package org.zalando.intellij.swagger.reference.contributor;

import com.intellij.patterns.PsiElementPattern;
import com.intellij.patterns.StandardPatterns;
import com.intellij.psi.PsiReferenceRegistrar;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.yaml.YAMLLanguage;
import org.jetbrains.yaml.psi.YAMLKeyValue;
import org.jetbrains.yaml.psi.YAMLQuotedText;
import org.jetbrains.yaml.psi.YAMLValue;
import org.zalando.intellij.swagger.reference.SwaggerConstants;
import org.zalando.intellij.swagger.traversal.YamlTraversal;

import static com.intellij.patterns.PlatformPatterns.psiElement;

public class SwaggerYamlReferenceContributor extends ReferenceContributor {

    public SwaggerYamlReferenceContributor() {
        super(new YamlTraversal());
    }

    @Override
    public void registerReferenceProviders(@NotNull final PsiReferenceRegistrar registrar) {
        registrar.registerReferenceProvider(localDefinitionsPattern(), createLocalReferenceProvider());
        registrar.registerReferenceProvider(externalDefinitionsInRootPattern(), createExternalDefinitionsInRootReferenceProvider());

        registrar.registerReferenceProvider(localParametersPattern(), createLocalReferenceProvider());

        registrar.registerReferenceProvider(localResponsesPattern(), createLocalReferenceProvider());

        registrar.registerReferenceProvider(filePattern(), createFileReferenceProvider());
        registrar.registerReferenceProvider(tagsPattern(), createTagsReferenceProvider());
    }

    private PsiElementPattern.Capture<YAMLQuotedText> localDefinitionsPattern() {
        return psiElement(YAMLQuotedText.class)
                .withParent(psiElement(YAMLKeyValue.class).withName(SwaggerConstants.REF_KEY))
                .andNot(StandardPatterns.string().matches(".ya?ml"))
                .withLanguage(YAMLLanguage.INSTANCE);
    }

    private PsiElementPattern.Capture<YAMLQuotedText> externalDefinitionsInRootPattern() {
        return psiElement(YAMLQuotedText.class)
                .withParent(psiElement(YAMLKeyValue.class).withName(SwaggerConstants.REF_KEY))
                .withText(StandardPatterns.string().matches("(.*).ya?ml#/([^/])*$"))
                .withLanguage(YAMLLanguage.INSTANCE);
    }

    private PsiElementPattern.Capture<YAMLQuotedText> localParametersPattern() {
        return psiElement(YAMLQuotedText.class)
                .withText(StandardPatterns.string().contains(SwaggerConstants.LOCAL_PARAMETERS_PREFIX))
                .withLanguage(YAMLLanguage.INSTANCE);
    }

    private PsiElementPattern.Capture<YAMLQuotedText> localResponsesPattern() {
        return psiElement(YAMLQuotedText.class)
                .withText(StandardPatterns.string().contains(SwaggerConstants.LOCAL_RESPONSES_PREFIX))
                .withLanguage(YAMLLanguage.INSTANCE);
    }

    private PsiElementPattern.Capture<YAMLValue> filePattern() {
        return psiElement(YAMLValue.class)
                .withText(StandardPatterns.string().matches("(.)*.ya?ml(.)*"))
                .withLanguage(YAMLLanguage.INSTANCE);
    }

    private PsiElementPattern.Capture<YAMLValue> tagsPattern() {
        return psiElement(YAMLValue.class).inside(psiElement(YAMLKeyValue.class).withName(SwaggerConstants.TAGS_KEY))
                .withLanguage(YAMLLanguage.INSTANCE);
    }
}
