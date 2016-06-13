package org.zalando.intellij.swagger.reference.contributor;

import static com.intellij.patterns.PlatformPatterns.psiElement;
import com.intellij.patterns.PsiElementPattern;
import com.intellij.patterns.StandardPatterns;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReference;
import com.intellij.psi.PsiReferenceContributor;
import com.intellij.psi.PsiReferenceProvider;
import com.intellij.psi.PsiReferenceRegistrar;
import com.intellij.util.ProcessingContext;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.yaml.YAMLLanguage;
import org.jetbrains.yaml.psi.YAMLQuotedText;
import org.zalando.intellij.swagger.completion.traversal.keydepth.YamlCompletionKeyDepth;
import org.zalando.intellij.swagger.completion.traversal.YamlTraversal;
import org.zalando.intellij.swagger.reference.YamlDefinitionReference;
import org.zalando.intellij.swagger.reference.YamlParameterReference;
import org.zalando.intellij.swagger.reference.extractor.ValueExtractor;

import java.util.Optional;

public class SwaggerYamlReferenceContributor extends PsiReferenceContributor {

    private final ValueExtractor valueExtractor;
    private final YamlTraversal yamlTraversal;

    public SwaggerYamlReferenceContributor() {
        this(new ValueExtractor(), new YamlTraversal(new YamlCompletionKeyDepth()));
    }

    private SwaggerYamlReferenceContributor(final ValueExtractor valueExtractor,
                                            final YamlTraversal yamlTraversal) {
        this.valueExtractor = valueExtractor;
        this.yamlTraversal = yamlTraversal;
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
                                .map(text -> new PsiReference[]{new YamlDefinitionReference(
                                        (YAMLQuotedText) element,
                                        valueExtractor.getDefinitionValue(text),
                                        yamlTraversal)
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
                                .map(text -> new PsiReference[]{new YamlParameterReference(
                                        (YAMLQuotedText) element,
                                        valueExtractor.getParameterValue(text),
                                        yamlTraversal)
                                }).orElse(YamlParameterReference.EMPTY_ARRAY);
                    }
                });
    }


    private PsiElementPattern.Capture<YAMLQuotedText> definitionsPattern() {
        return psiElement(YAMLQuotedText.class)
                .withText(StandardPatterns.string().contains("#/definitions/"))
                .withLanguage(YAMLLanguage.INSTANCE);
    }

    private PsiElementPattern.Capture<YAMLQuotedText> parametersPattern() {
        return psiElement(YAMLQuotedText.class)
                .withText(StandardPatterns.string().contains("#/parameters/"))
                .withLanguage(YAMLLanguage.INSTANCE);
    }

}
