package org.zalando.intellij.swagger.reference.contributor.openapi;

import com.intellij.json.JsonLanguage;
import com.intellij.json.psi.JsonLiteral;
import com.intellij.json.psi.JsonProperty;
import com.intellij.patterns.PsiElementPattern;
import com.intellij.patterns.StandardPatterns;
import com.intellij.patterns.StringPattern;
import com.intellij.psi.PsiReferenceRegistrar;
import org.jetbrains.annotations.NotNull;
import org.zalando.intellij.swagger.file.FileConstants;
import org.zalando.intellij.swagger.reference.swagger.OpenApiConstants;

import static com.intellij.patterns.PlatformPatterns.psiElement;

public class OpenApiJsonReferenceContributor extends ReferenceContributor {

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

    private PsiElementPattern.Capture<JsonLiteral> localReferencePattern(final String refTypePrefix) {
        return psiElement(JsonLiteral.class)
                .withParent(psiElement(JsonProperty.class).withName(OpenApiConstants.REF_KEY))
                .withText(StandardPatterns.string().contains(refTypePrefix))
                .andNot(StandardPatterns.string().contains(FileConstants.JSON_FILE_NAME_SUFFIX))
                .withLanguage(JsonLanguage.INSTANCE);
    }

    private PsiElementPattern.Capture<JsonLiteral> filePattern() {
        return psiElement(JsonLiteral.class)
                .withText(endsWithJson())
                .withLanguage(JsonLanguage.INSTANCE);
    }

    private PsiElementPattern.Capture<JsonLiteral> componentFileReferencePattern() {
        return psiElement(JsonLiteral.class)
                .withParent(psiElement(JsonProperty.class).withName(OpenApiConstants.REF_KEY))
                .withText(StandardPatterns.string().matches("(.*).json#/([^/])*$"))
                .withLanguage(JsonLanguage.INSTANCE);
    }

    private StringPattern endsWithJson() {
        return StandardPatterns.string().endsWith(FileConstants.JSON_FILE_NAME_SUFFIX + "\"");
    }

}
