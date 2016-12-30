package org.zalando.intellij.swagger.reference.contributor;

import com.intellij.json.JsonLanguage;
import com.intellij.json.psi.JsonLiteral;
import com.intellij.json.psi.JsonProperty;
import com.intellij.json.psi.JsonValue;
import com.intellij.patterns.PsiElementPattern;
import com.intellij.patterns.StandardPatterns;
import com.intellij.patterns.StringPattern;
import com.intellij.psi.*;
import com.intellij.util.ProcessingContext;
import org.jetbrains.annotations.NotNull;
import org.zalando.intellij.swagger.completion.StringUtils;
import org.zalando.intellij.swagger.file.FileConstants;
import org.zalando.intellij.swagger.reference.*;
import org.zalando.intellij.swagger.traversal.JsonTraversal;

import java.util.Optional;

import static com.intellij.patterns.PlatformPatterns.psiElement;

public class SwaggerJsonReferenceContributor extends PsiReferenceContributor {

    private final JsonTraversal jsonTraversal;

    public SwaggerJsonReferenceContributor() {
        this(new JsonTraversal());
    }

    private SwaggerJsonReferenceContributor(final JsonTraversal jsonTraversal) {
        this.jsonTraversal = jsonTraversal;
    }

    @Override
    public void registerReferenceProviders(@NotNull final PsiReferenceRegistrar registrar) {
        registrar.registerReferenceProvider(localDefinitionsPattern(), getLocalDefinitionReferenceProvider());
        registrar.registerReferenceProvider(externalDefinitionsInRootPattern(), getExternalDefinitionsInRootProvider());
        registrar.registerReferenceProvider(externalDefinitionsNotInRootPattern(), getExternalDefinitionsNotInRootProvider());
        registrar.registerReferenceProvider(localParametersPattern(), getLocalParameterReferenceProvider());
        registrar.registerReferenceProvider(externalParameterDefinitionsInRootPattern(), getExternalDefinitionsInRootProvider());
        registrar.registerReferenceProvider(externalParameterDefinitionsNotInRootPattern(), getExternalDefinitionsNotInRootProvider());
        registrar.registerReferenceProvider(localResponsesPattern(), getLocalResponseReferenceProvider());
        registrar.registerReferenceProvider(filePattern(), getFileReferenceProvider());
        registrar.registerReferenceProvider(tagsPattern(), getTagsReferenceProvider());
    }

    @NotNull
    private PsiReferenceProvider getLocalResponseReferenceProvider() {
        return createLocalReferenceProviderFromKey(SwaggerConstants.RESPONSES_KEY);
    }

    @NotNull
    private PsiReferenceProvider getLocalParameterReferenceProvider() {
        return createLocalReferenceProviderFromKey(SwaggerConstants.PARAMETERS_KEY);
    }

    @NotNull
    private PsiReferenceProvider getLocalDefinitionReferenceProvider() {
        return createLocalReferenceProviderFromKey(SwaggerConstants.DEFINITIONS_KEY);
    }

    @NotNull
    private PsiReferenceProvider createLocalReferenceProviderFromKey(final String key) {
        return new PsiReferenceProvider() {
            @NotNull
            @Override
            public PsiReference[] getReferencesByElement(@NotNull PsiElement element,
                                                         @NotNull ProcessingContext context) {
                return Optional.ofNullable(element.getText())
                        .map(text -> new PsiReference[]{new LocalReference(
                                key,
                                element,
                                StringUtils.removeAllQuotes(text),
                                jsonTraversal)
                        }).orElse(LocalReference.EMPTY_ARRAY);
            }
        };
    }

    @NotNull
    private PsiReferenceProvider getExternalDefinitionsInRootProvider() {
        return new PsiReferenceProvider() {
            @NotNull
            @Override
            public PsiReference[] getReferencesByElement(@NotNull PsiElement element,
                                                         @NotNull ProcessingContext context) {
                return Optional.ofNullable(element.getText())
                        .map(text -> new PsiReference[]{
                                new DefinitionsInRootReference(
                                        element,
                                        StringUtils.removeAllQuotes(text),
                                        jsonTraversal)
                        }).orElse(DefinitionsInRootReference.EMPTY_ARRAY);
            }
        };
    }

    @NotNull
    private PsiReferenceProvider getExternalDefinitionsNotInRootProvider() {
        return new PsiReferenceProvider() {
            @NotNull
            @Override
            public PsiReference[] getReferencesByElement(@NotNull PsiElement element,
                                                         @NotNull ProcessingContext context) {
                return Optional.ofNullable(element.getText())
                        .map(text -> new PsiReference[]{
                                new DefinitionsNotInRootReference(
                                        element,
                                        StringUtils.removeAllQuotes(text),
                                        jsonTraversal)
                        }).orElse(DefinitionsNotInRootReference.EMPTY_ARRAY);
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
                                new FileReference(
                                        element,
                                        StringUtils.removeAllQuotes(text))
                        }).orElse(FileReference.EMPTY_ARRAY);
            }
        };
    }

    @NotNull
    private PsiReferenceProvider getTagsReferenceProvider() {
        return new PsiReferenceProvider() {
            @NotNull
            @Override
            public PsiReference[] getReferencesByElement(@NotNull PsiElement element,
                                                         @NotNull ProcessingContext context) {
                return Optional.ofNullable(element.getText())
                        .map(text -> new PsiReference[]{new TagReference(
                                element,
                                element.getText(),
                                jsonTraversal)})
                        .orElse(TagReference.EMPTY_ARRAY);
            }
        };
    }

    private PsiElementPattern.Capture<JsonLiteral> localDefinitionsPattern() {
        return psiElement(JsonLiteral.class)
                .withParent(psiElement(JsonProperty.class).withName(SwaggerConstants.REF_KEY))
                .inside(psiElement(JsonProperty.class).withName(SwaggerConstants.SCHEMA_KEY))
                .withText(StandardPatterns.string().contains(SwaggerConstants.LOCAL_DEFINITIONS_PREFIX))
                .andNot(StandardPatterns.string().contains(FileConstants.JSON_FILE_NAME_SUFFIX))
                .withLanguage(JsonLanguage.INSTANCE);
    }

    private PsiElementPattern.Capture<JsonLiteral> externalDefinitionsInRootPattern() {
        return psiElement(JsonLiteral.class)
                .withParent(psiElement(JsonProperty.class).withName(SwaggerConstants.REF_KEY))
                .inside(psiElement(JsonProperty.class).withName(SwaggerConstants.SCHEMA_KEY))
                .withText(StandardPatterns.string().matches("(.*).json#/([^/])*$"))
                .withLanguage(JsonLanguage.INSTANCE);
    }

    private PsiElementPattern.Capture<JsonLiteral> externalParameterDefinitionsInRootPattern() {
        return psiElement(JsonLiteral.class)
                .withParent(psiElement(JsonProperty.class).withName(SwaggerConstants.REF_KEY))
                .inside(psiElement(JsonProperty.class).withName(SwaggerConstants.PARAMETERS_KEY))
                .withText(StandardPatterns.string().matches("(.*).json#/([^/])*$"))
                .withLanguage(JsonLanguage.INSTANCE);
    }

    private PsiElementPattern.Capture<JsonLiteral> externalDefinitionsNotInRootPattern() {
        return psiElement(JsonLiteral.class)
                .withParent(psiElement(JsonProperty.class).withName(SwaggerConstants.REF_KEY))
                .inside(psiElement(JsonProperty.class).withName(SwaggerConstants.SCHEMA_KEY))
                .withText(StandardPatterns.string().matches("(.*).json#/(.*)[/]+(.*)"))
                .withLanguage(JsonLanguage.INSTANCE);
    }

    private PsiElementPattern.Capture<JsonLiteral> externalParameterDefinitionsNotInRootPattern() {
        return psiElement(JsonLiteral.class)
                .withParent(psiElement(JsonProperty.class).withName(SwaggerConstants.REF_KEY))
                .inside(psiElement(JsonProperty.class).withName(SwaggerConstants.PARAMETERS_KEY))
                .withText(StandardPatterns.string().matches("(.*).json#/(.*)[/]+(.*)"))
                .withLanguage(JsonLanguage.INSTANCE);
    }

    private PsiElementPattern.Capture<JsonLiteral> localParametersPattern() {
        return psiElement(JsonLiteral.class).withText(StandardPatterns.string().contains(SwaggerConstants.LOCAL_PARAMETERS_PREFIX))
                .withLanguage(JsonLanguage.INSTANCE);
    }

    private PsiElementPattern.Capture<JsonLiteral> localResponsesPattern() {
        return psiElement(JsonLiteral.class).withText(StandardPatterns.string().contains(SwaggerConstants.LOCAL_RESPONSES_PREFIX))
                .withLanguage(JsonLanguage.INSTANCE);
    }

    private PsiElementPattern.Capture<JsonLiteral> filePattern() {
        return psiElement(JsonLiteral.class)
                .withText(endsWithJson())
                .withLanguage(JsonLanguage.INSTANCE);
    }

    private StringPattern endsWithJson() {
        return StandardPatterns.string().endsWith(FileConstants.JSON_FILE_NAME_SUFFIX + "\"");
    }

    private PsiElementPattern.Capture<JsonValue> tagsPattern() {
        return psiElement(JsonValue.class).inside(psiElement(JsonProperty.class).withName(SwaggerConstants.TAGS_KEY))
                .withLanguage(JsonLanguage.INSTANCE);
    }
}
