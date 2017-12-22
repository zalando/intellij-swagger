package org.zalando.intellij.swagger.reference.contributor.swagger;

import com.intellij.json.JsonLanguage;
import com.intellij.json.psi.JsonLiteral;
import com.intellij.json.psi.JsonObject;
import com.intellij.json.psi.JsonProperty;
import com.intellij.json.psi.JsonValue;
import com.intellij.patterns.PsiElementPattern;
import com.intellij.patterns.StandardPatterns;
import com.intellij.patterns.StringPattern;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReferenceRegistrar;
import org.jetbrains.annotations.NotNull;
import org.zalando.intellij.swagger.file.FileConstants;
import org.zalando.intellij.swagger.reference.swagger.SwaggerConstants;
import org.zalando.intellij.swagger.traversal.JsonTraversal;

import static com.intellij.patterns.PlatformPatterns.psiElement;

public class SwaggerJsonReferenceContributor extends ReferenceContributor {

    public SwaggerJsonReferenceContributor() {
        super(new JsonTraversal());
    }

    @Override
    public void registerReferenceProviders(@NotNull final PsiReferenceRegistrar registrar) {
        registrar.registerReferenceProvider(localDefinitionsPattern(), createLocalReferenceProvider());
        registrar.registerReferenceProvider(externalDefinitionsInRootPattern(), createExternalDefinitionsInRootReferenceProvider());
        registrar.registerReferenceProvider(externalDefinitionsNotInRootPattern(), createExternalDefinitionsNotInRootReferenceProvider());

        registrar.registerReferenceProvider(localParametersPattern(), createLocalReferenceProvider());
        registrar.registerReferenceProvider(localResponsesPattern(), createLocalReferenceProvider());

        registrar.registerReferenceProvider(filePattern(), createFileReferenceProvider());
        registrar.registerReferenceProvider(tagsPattern(), createTagsReferenceProvider());
    }

    private PsiElementPattern.Capture<JsonLiteral> localDefinitionsPattern() {
        return swagger(psiElement(JsonLiteral.class)
                .withParent(psiElement(JsonProperty.class).withName(SwaggerConstants.REF_KEY))
                .withText(StandardPatterns.string().contains(SwaggerConstants.LOCAL_DEFINITIONS_PREFIX))
                .andNot(StandardPatterns.string().contains(FileConstants.JSON_FILE_NAME_SUFFIX))
                .withLanguage(JsonLanguage.INSTANCE));
    }

    private PsiElementPattern.Capture<JsonLiteral> externalDefinitionsInRootPattern() {
        return swagger(psiElement(JsonLiteral.class)
                .withParent(psiElement(JsonProperty.class).withName(SwaggerConstants.REF_KEY))
                .withText(StandardPatterns.string().matches("(.*).json#/([^/])*$"))
                .withLanguage(JsonLanguage.INSTANCE));
    }

    private PsiElementPattern.Capture<JsonLiteral> externalDefinitionsNotInRootPattern() {
        return swagger(psiElement(JsonLiteral.class)
                .withParent(psiElement(JsonProperty.class).withName(SwaggerConstants.REF_KEY))
                .withText(StandardPatterns.string().matches("(.*).json#/(.*)/(.*)"))
                .withLanguage(JsonLanguage.INSTANCE));
    }

    private PsiElementPattern.Capture<JsonLiteral> localParametersPattern() {
        return swagger(psiElement(JsonLiteral.class).withText(StandardPatterns.string().contains(SwaggerConstants.LOCAL_PARAMETERS_PREFIX))
                .withLanguage(JsonLanguage.INSTANCE));
    }

    private PsiElementPattern.Capture<JsonLiteral> localResponsesPattern() {
        return swagger(psiElement(JsonLiteral.class).withText(StandardPatterns.string().contains(SwaggerConstants.LOCAL_RESPONSES_PREFIX))
                .withLanguage(JsonLanguage.INSTANCE));
    }

    private PsiElementPattern.Capture<JsonLiteral> filePattern() {
        return swagger(psiElement(JsonLiteral.class)
                .withText(endsWithJson())
                .withLanguage(JsonLanguage.INSTANCE));
    }

    private StringPattern endsWithJson() {
        return StandardPatterns.string().endsWith(FileConstants.JSON_FILE_NAME_SUFFIX + "\"");
    }

    private PsiElementPattern.Capture<JsonValue> tagsPattern() {
        return psiElement(JsonValue.class).inside(psiElement(JsonProperty.class).withName(SwaggerConstants.TAGS_KEY))
                .withLanguage(JsonLanguage.INSTANCE);
    }

    private <T extends PsiElement> PsiElementPattern.Capture<T> swagger(final PsiElementPattern.Capture<T> pattern) {
        return pattern.
                inside(psiElement(JsonObject.class).withChild(psiElement().withName(SwaggerConstants.SWAGGER)));
    }
}
