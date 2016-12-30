package org.zalando.intellij.swagger.reference.contributor;

import com.intellij.patterns.PsiElementPattern;
import com.intellij.patterns.StandardPatterns;
import com.intellij.psi.*;
import com.intellij.util.ProcessingContext;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.yaml.YAMLLanguage;
import org.jetbrains.yaml.psi.YAMLKeyValue;
import org.jetbrains.yaml.psi.YAMLQuotedText;
import org.jetbrains.yaml.psi.YAMLValue;
import org.zalando.intellij.swagger.completion.StringUtils;
import org.zalando.intellij.swagger.reference.*;
import org.zalando.intellij.swagger.traversal.YamlTraversal;

import java.util.Optional;

import static com.intellij.patterns.PlatformPatterns.psiElement;

public class SwaggerYamlReferenceContributor extends PsiReferenceContributor {

    private final YamlTraversal yamlTraversal;

    public SwaggerYamlReferenceContributor() {
        this(new YamlTraversal());
    }

    private SwaggerYamlReferenceContributor(final YamlTraversal yamlTraversal) {
        this.yamlTraversal = yamlTraversal;
    }

    @Override
    public void registerReferenceProviders(@NotNull final PsiReferenceRegistrar registrar) {
        registrar.registerReferenceProvider(localDefinitionsPattern(), getLocalDefinitionReferenceProvider());
        registrar.registerReferenceProvider(externalDefinitionsInRootPattern(), getExternalDefinitionsInRootProvider());
        registrar.registerReferenceProvider(externalDefinitionsNotInRootPattern(), getExternalDefinitionsNotInRootProvider());
        registrar.registerReferenceProvider(localParametersPattern(), getLocalParameterReferenceProvider());
        registrar.registerReferenceProvider(localResponsesPattern(), getLocalResponseReferenceProvider());
        registrar.registerReferenceProvider(filePattern(), getFileReferenceProvider());
        registrar.registerReferenceProvider(tagsPattern(), getTagsReferenceProvider());
    }

    @NotNull
    private PsiReferenceProvider getLocalResponseReferenceProvider() {
        return createLocalReferenceProviderFromReferenceType(SwaggerConstants.RESPONSES_KEY);
    }

    @NotNull
    private PsiReferenceProvider getLocalParameterReferenceProvider() {
        return createLocalReferenceProviderFromReferenceType(SwaggerConstants.PARAMETERS_KEY);
    }

    @NotNull
    private PsiReferenceProvider getLocalDefinitionReferenceProvider() {
        return createLocalReferenceProviderFromReferenceType(SwaggerConstants.DEFINITIONS_KEY);
    }

    @NotNull
    private PsiReferenceProvider createLocalReferenceProviderFromReferenceType(final String referenceType) {
        return new PsiReferenceProvider() {
            @NotNull
            @Override
            public PsiReference[] getReferencesByElement(@NotNull PsiElement element,
                                                         @NotNull ProcessingContext context) {
                return Optional.ofNullable(element.getText())
                        .map(text -> new PsiReference[]{new LocalReference(
                                referenceType,
                                element,
                                StringUtils.removeAllQuotes(text),
                                yamlTraversal)
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
                                        yamlTraversal)
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
                                        yamlTraversal)
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
                        .map(text -> new PsiReference[]{
                                new TagReference(
                                        element,
                                        element.getText(),
                                        yamlTraversal)})
                        .orElse(TagReference.EMPTY_ARRAY);
            }
        };
    }

    private PsiElementPattern.Capture<YAMLQuotedText> localDefinitionsPattern() {
        return psiElement(YAMLQuotedText.class)
                .withParent(psiElement(YAMLKeyValue.class).withName(SwaggerConstants.REF_KEY))
                .inside(psiElement(YAMLKeyValue.class).withName(SwaggerConstants.SCHEMA_KEY))
                .withText(StandardPatterns.string().contains(SwaggerConstants.LOCAL_DEFINITIONS_PREFIX))
                .andNot(StandardPatterns.string().matches(".ya?ml"))
                .withLanguage(YAMLLanguage.INSTANCE);
    }

    private PsiElementPattern.Capture<YAMLQuotedText> externalDefinitionsInRootPattern() {
        return psiElement(YAMLQuotedText.class)
                .withParent(psiElement(YAMLKeyValue.class).withName(SwaggerConstants.REF_KEY))
                .inside(psiElement(YAMLKeyValue.class).withName(SwaggerConstants.SCHEMA_KEY))
                .withText(StandardPatterns.string().matches("(.*).ya?ml#/([^/])*$"))
                .withLanguage(YAMLLanguage.INSTANCE);
    }

    private PsiElementPattern.Capture<YAMLQuotedText> externalDefinitionsNotInRootPattern() {
        return psiElement(YAMLQuotedText.class)
                .withParent(psiElement(YAMLKeyValue.class).withName(SwaggerConstants.REF_KEY))
                .inside(psiElement(YAMLKeyValue.class).withName(SwaggerConstants.SCHEMA_KEY))
                .withText(StandardPatterns.string().matches("(.*).ya?ml#/(.*)[/]+(.*)"))
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
