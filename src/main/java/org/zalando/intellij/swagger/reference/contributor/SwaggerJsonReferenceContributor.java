package org.zalando.intellij.swagger.reference.contributor;

import static com.intellij.patterns.PlatformPatterns.psiElement;
import com.intellij.json.JsonLanguage;
import com.intellij.json.psi.JsonLiteral;
import com.intellij.patterns.PsiElementPattern;
import com.intellij.patterns.StandardPatterns;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReference;
import com.intellij.psi.PsiReferenceContributor;
import com.intellij.psi.PsiReferenceProvider;
import com.intellij.psi.PsiReferenceRegistrar;
import com.intellij.util.ProcessingContext;
import org.jetbrains.annotations.NotNull;
import org.zalando.intellij.swagger.reference.JsonDefinitionReference;
import org.zalando.intellij.swagger.reference.JsonParameterReference;
import org.zalando.intellij.swagger.reference.JsonResponseReference;
import org.zalando.intellij.swagger.reference.YamlDefinitionReference;
import org.zalando.intellij.swagger.reference.extractor.ValueExtractor;
import org.zalando.intellij.swagger.traversal.JsonTraversal;

import java.util.Optional;

public class SwaggerJsonReferenceContributor extends PsiReferenceContributor {

    private final ValueExtractor valueExtractor;
    private final JsonTraversal jsonTraversal;

    public SwaggerJsonReferenceContributor() {
        this(new ValueExtractor(), new JsonTraversal());
    }

    private SwaggerJsonReferenceContributor(final ValueExtractor valueExtractor,
                                            final JsonTraversal jsonTraversal) {
        this.valueExtractor = valueExtractor;
        this.jsonTraversal = jsonTraversal;
    }

    @Override
    public void registerReferenceProviders(@NotNull final PsiReferenceRegistrar registrar) {
        registrar.registerReferenceProvider(definitionsPattern(),
                new PsiReferenceProvider() {
                    @NotNull
                    @Override
                    public PsiReference[] getReferencesByElement(@NotNull PsiElement element,
                                                                 @NotNull ProcessingContext context) {
                        return Optional.ofNullable(element.getText())
                                .map(text -> new PsiReference[]{
                                        new JsonDefinitionReference(
                                                (JsonLiteral) element,
                                                valueExtractor.getDefinitionValue(text),
                                                jsonTraversal)
                                }).orElse(YamlDefinitionReference.EMPTY_ARRAY);
                    }
                });
        registrar.registerReferenceProvider(parametersPattern(),
                new PsiReferenceProvider() {
                    @NotNull
                    @Override
                    public PsiReference[] getReferencesByElement(@NotNull PsiElement element,
                                                                 @NotNull ProcessingContext context) {
                        return Optional.ofNullable(element.getText())
                                .map(text -> new PsiReference[]{new JsonParameterReference(
                                        (JsonLiteral) element,
                                        valueExtractor.getParameterValue(text),
                                        jsonTraversal)})
                                .orElse(JsonParameterReference.EMPTY_ARRAY);
                    }
                });
        registrar.registerReferenceProvider(responsesPattern(),
                new PsiReferenceProvider() {
                    @NotNull
                    @Override
                    public PsiReference[] getReferencesByElement(@NotNull PsiElement element,
                                                                 @NotNull ProcessingContext context) {
                        return Optional.ofNullable(element.getText())
                                .map(text -> new PsiReference[]{new JsonResponseReference(
                                        (JsonLiteral) element,
                                        valueExtractor.getResponseValue(text),
                                        jsonTraversal)})
                                .orElse(JsonResponseReference.EMPTY_ARRAY);
                    }
                });
    }

    private PsiElementPattern.Capture<JsonLiteral> definitionsPattern() {
        return psiElement(JsonLiteral.class).withText(StandardPatterns.string().contains("#/definitions/"))
                .withLanguage(JsonLanguage.INSTANCE);
    }

    private PsiElementPattern.Capture<JsonLiteral> parametersPattern() {
        return psiElement(JsonLiteral.class).withText(StandardPatterns.string().contains("#/parameters/"))
                .withLanguage(JsonLanguage.INSTANCE);
    }

    private PsiElementPattern.Capture<JsonLiteral> responsesPattern() {
        return psiElement(JsonLiteral.class).withText(StandardPatterns.string().contains("#/responses/"))
                .withLanguage(JsonLanguage.INSTANCE);
    }

}
