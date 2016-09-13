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
import org.zalando.intellij.swagger.completion.StringUtils;
import org.zalando.intellij.swagger.reference.JsonDefinitionReference;
import org.zalando.intellij.swagger.reference.JsonFileReference;
import org.zalando.intellij.swagger.reference.JsonParameterReference;
import org.zalando.intellij.swagger.reference.JsonResponseReference;
import org.zalando.intellij.swagger.reference.extractor.ReferenceValueExtractor;
import org.zalando.intellij.swagger.traversal.JsonTraversal;

import java.util.Optional;

public class SwaggerJsonReferenceContributor extends PsiReferenceContributor {

    private final ReferenceValueExtractor referenceValueExtractor;
    private final JsonTraversal jsonTraversal;

    public SwaggerJsonReferenceContributor() {
        this(new ReferenceValueExtractor(), new JsonTraversal());
    }

    private SwaggerJsonReferenceContributor(final ReferenceValueExtractor referenceValueExtractor,
                                            final JsonTraversal jsonTraversal) {
        this.referenceValueExtractor = referenceValueExtractor;
        this.jsonTraversal = jsonTraversal;
    }

    @Override
    public void registerReferenceProviders(@NotNull final PsiReferenceRegistrar registrar) {
        registrar.registerReferenceProvider(definitionsPattern(), getDefinitionReferenceProvider());
        registrar.registerReferenceProvider(parametersPattern(), getParameterReferenceProvider());
        registrar.registerReferenceProvider(responsesPattern(), getResponseReferenceProvider());
        registrar.registerReferenceProvider(filePattern(), getFileReferenceProvider());
    }

    @NotNull
    private PsiReferenceProvider getResponseReferenceProvider() {
        return new PsiReferenceProvider() {
            @NotNull
            @Override
            public PsiReference[] getReferencesByElement(@NotNull PsiElement element,
                                                         @NotNull ProcessingContext context) {
                return Optional.ofNullable(element.getText())
                        .map(text -> new PsiReference[]{new JsonResponseReference(
                                (JsonLiteral) element,
                                referenceValueExtractor.getValue(text),
                                jsonTraversal)})
                        .orElse(JsonResponseReference.EMPTY_ARRAY);
            }
        };
    }

    @NotNull
    private PsiReferenceProvider getParameterReferenceProvider() {
        return new PsiReferenceProvider() {
            @NotNull
            @Override
            public PsiReference[] getReferencesByElement(@NotNull PsiElement element,
                                                         @NotNull ProcessingContext context) {
                return Optional.ofNullable(element.getText())
                        .map(text -> new PsiReference[]{new JsonParameterReference(
                                (JsonLiteral) element,
                                referenceValueExtractor.getValue(text),
                                jsonTraversal)})
                        .orElse(JsonParameterReference.EMPTY_ARRAY);
            }
        };
    }

    @NotNull
    private PsiReferenceProvider getDefinitionReferenceProvider() {
        return new PsiReferenceProvider() {
            @NotNull
            @Override
            public PsiReference[] getReferencesByElement(@NotNull PsiElement element,
                                                         @NotNull ProcessingContext context) {
                return Optional.ofNullable(element.getText())
                        .map(text -> new PsiReference[]{
                                new JsonDefinitionReference(
                                        (JsonLiteral) element,
                                        referenceValueExtractor.getValue(text),
                                        jsonTraversal)
                        }).orElse(JsonDefinitionReference.EMPTY_ARRAY);
            }
        };
    }

    @NotNull
    private PsiReferenceProvider getFileReferenceProvider() {
        return new PsiReferenceProvider() {
            @NotNull
            @Override
            public PsiReference[] getReferencesByElement(@NotNull PsiElement element,
                                                         @NotNull ProcessingContext context) {
                return Optional.ofNullable(element.getText())
                        .map(text -> new PsiReference[]{
                                new JsonFileReference(
                                        (JsonLiteral) element,
                                        StringUtils.removeAllQuotes(text))
                        }).orElse(JsonFileReference.EMPTY_ARRAY);
            }
        };
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

    private PsiElementPattern.Capture<JsonLiteral> filePattern() {
        return psiElement(JsonLiteral.class).withText(StandardPatterns.string().endsWith(".json\""))
                .withLanguage(JsonLanguage.INSTANCE);
    }
}
