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
import org.zalando.intellij.swagger.reference.extractor.ReferenceValueExtractor;
import org.zalando.intellij.swagger.traversal.JsonTraversal;

import java.util.Optional;

import static com.intellij.patterns.PlatformPatterns.psiElement;

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
        registrar.registerReferenceProvider(localDefinitionsPattern(), getLocalDefinitionReferenceProvider());
        registrar.registerReferenceProvider(externalDefinitionsInRootPattern(), getExternalDefinitionsInRootProvider());
        registrar.registerReferenceProvider(externalDefinitionsNotInRootPattern(), getExternalDefinitionsNotInRootProvider());
        registrar.registerReferenceProvider(parametersPattern(), getParameterReferenceProvider());
        registrar.registerReferenceProvider(responsesPattern(), getResponseReferenceProvider());
        registrar.registerReferenceProvider(filePattern(), getFileReferenceProvider());
        registrar.registerReferenceProvider(tagsPattern(), getTagsReferenceProvider());
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
    private PsiReferenceProvider getLocalDefinitionReferenceProvider() {
        return new PsiReferenceProvider() {
            @NotNull
            @Override
            public PsiReference[] getReferencesByElement(@NotNull PsiElement element,
                                                         @NotNull ProcessingContext context) {
                return Optional.ofNullable(element.getText())
                        .map(text -> new PsiReference[]{
                                new JsonLocalDefinitionReference(
                                        (JsonLiteral) element,
                                        StringUtils.removeAllQuotes(text),
                                        jsonTraversal)
                        }).orElse(JsonLocalDefinitionReference.EMPTY_ARRAY);
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
                                new JsonDefinitionsInRootReference(
                                        (JsonLiteral) element,
                                        StringUtils.removeAllQuotes(text),
                                        jsonTraversal)
                        }).orElse(JsonDefinitionsInRootReference.EMPTY_ARRAY);
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
                                new JsonDefinitionsNotInRootReference(
                                        (JsonLiteral) element,
                                        StringUtils.removeAllQuotes(text),
                                        jsonTraversal)
                        }).orElse(JsonDefinitionsNotInRootReference.EMPTY_ARRAY);
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

    @NotNull
    private PsiReferenceProvider getTagsReferenceProvider() {
        return new PsiReferenceProvider() {
            @NotNull
            @Override
            public PsiReference[] getReferencesByElement(@NotNull PsiElement element,
                                                         @NotNull ProcessingContext context) {
                return Optional.ofNullable(element.getText())
                        .map(text -> new PsiReference[]{new JsonTagReference(
                                (JsonValue) element,
                                element.getText(),
                                jsonTraversal)})
                        .orElse(JsonTagReference.EMPTY_ARRAY);
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

    private PsiElementPattern.Capture<JsonLiteral> externalDefinitionsNotInRootPattern() {
        return psiElement(JsonLiteral.class)
                .withParent(psiElement(JsonProperty.class).withName(SwaggerConstants.REF_KEY))
                .inside(psiElement(JsonProperty.class).withName(SwaggerConstants.SCHEMA_KEY))
                .withText(StandardPatterns.string().matches("(.*).json#/(.*)[/]+(.*)"))
                .withLanguage(JsonLanguage.INSTANCE);
    }

    private PsiElementPattern.Capture<JsonLiteral> parametersPattern() {
        return psiElement(JsonLiteral.class).withText(StandardPatterns.string().contains(SwaggerConstants.LOCAL_PARAMETERS_PREFIX))
                .withLanguage(JsonLanguage.INSTANCE);
    }

    private PsiElementPattern.Capture<JsonLiteral> responsesPattern() {
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
