package org.zalando.intellij.swagger.reference.contributor.openapi;

import com.intellij.patterns.PsiElementPattern;
import com.intellij.patterns.StandardPatterns;
import com.intellij.psi.PsiReferenceRegistrar;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.yaml.YAMLLanguage;
import org.jetbrains.yaml.psi.YAMLKeyValue;
import org.jetbrains.yaml.psi.YAMLQuotedText;
import org.jetbrains.yaml.psi.YAMLValue;
import org.zalando.intellij.swagger.reference.swagger.OpenApiConstants;

import static com.intellij.patterns.PlatformPatterns.psiElement;

public class OpenApiYamlReferenceContributor extends ReferenceContributor {

    @Override
    public void registerReferenceProviders(@NotNull final PsiReferenceRegistrar registrar) {
        registrar.registerReferenceProvider(localReferencePattern(OpenApiConstants.LOCAL_SCHEMAS_PREFIX), createLocalReferenceProvider());
        registrar.registerReferenceProvider(localReferencePattern(OpenApiConstants.LOCAL_RESPONSES_PREFIX), createLocalReferenceProvider());
        registrar.registerReferenceProvider(localReferencePattern(OpenApiConstants.LOCAL_PARAMETERS_PREFIX), createLocalReferenceProvider());
        registrar.registerReferenceProvider(localReferencePattern(OpenApiConstants.LOCAL_EXAMPLES_PREFIX), createLocalReferenceProvider());
        registrar.registerReferenceProvider(localReferencePattern(OpenApiConstants.LOCAL_REQUEST_BODIES_PREFIX), createLocalReferenceProvider());
        registrar.registerReferenceProvider(localReferencePattern(OpenApiConstants.LOCAL_HEADERS_PREFIX), createLocalReferenceProvider());
        registrar.registerReferenceProvider(localReferencePattern(OpenApiConstants.LOCAL_LINKS_PREFIX), createLocalReferenceProvider());
        registrar.registerReferenceProvider(localReferencePattern(OpenApiConstants.LOCAL_CALLBACKS_PREFIX), createLocalReferenceProvider());

        registrar.registerReferenceProvider(filePattern(), createFileReferenceProvider());
        registrar.registerReferenceProvider(componentFileReferencePattern(), createComponentFileReferenceProvider());
    }

    private PsiElementPattern.Capture<YAMLQuotedText> localReferencePattern(final String refTypePrefix) {
        return psiElement(YAMLQuotedText.class)
                .withParent(psiElement(YAMLKeyValue.class).withName(OpenApiConstants.REF_KEY))
                .withText(StandardPatterns.string().contains(refTypePrefix))
                .andNot(StandardPatterns.string().matches(".ya?ml"))
                .withLanguage(YAMLLanguage.INSTANCE);
    }

    private PsiElementPattern.Capture<YAMLValue> filePattern() {
        return psiElement(YAMLValue.class)
                .withText(StandardPatterns.string().matches("(.)*.ya?ml$"))
                .withLanguage(YAMLLanguage.INSTANCE);
    }

    private PsiElementPattern.Capture<YAMLQuotedText> componentFileReferencePattern() {
        return psiElement(YAMLQuotedText.class)
                .withParent(psiElement(YAMLKeyValue.class).withName(OpenApiConstants.REF_KEY))
                .withText(StandardPatterns.string().matches("(.*).ya?ml#/([^/])*$"))
                .withLanguage(YAMLLanguage.INSTANCE);
    }

}
