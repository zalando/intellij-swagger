package org.zalando.intellij.swagger.completion.contributor.openapi;

import com.intellij.codeInsight.completion.CompletionContributor;
import com.intellij.codeInsight.completion.CompletionParameters;
import com.intellij.codeInsight.completion.CompletionResultSet;
import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;
import org.zalando.intellij.swagger.completion.OpenApiCompletionHelper;
import org.zalando.intellij.swagger.completion.field.FieldCompletion;
import org.zalando.intellij.swagger.completion.field.openapi.OpenApiFieldCompletionFactory;
import org.zalando.intellij.swagger.completion.value.openapi.OpenApiValueCompletionFactory;
import org.zalando.intellij.swagger.completion.value.ValueCompletion;
import org.zalando.intellij.swagger.file.OpenApiFileType;
import org.zalando.intellij.swagger.index.openapi.OpenApiIndexService;
import org.zalando.intellij.swagger.completion.contributor.ReferencePrefixExtractor;
import org.zalando.intellij.swagger.traversal.YamlTraversal;
import org.zalando.intellij.swagger.traversal.path.openapi.PathResolver;
import org.zalando.intellij.swagger.traversal.path.openapi.PathResolverFactory;

public class OpenApiYamlCompletionContributor extends CompletionContributor {

    private final YamlTraversal yamlTraversal;
    private final ReferencePrefixExtractor referencePrefixExtractor;
    private final OpenApiIndexService openApiIndexService;

    public OpenApiYamlCompletionContributor() {
        this(new YamlTraversal(), new ReferencePrefixExtractor(), new OpenApiIndexService());
    }

    private OpenApiYamlCompletionContributor(final YamlTraversal yamlTraversal,
                                             final ReferencePrefixExtractor referencePrefixExtractor,
                                             final OpenApiIndexService openApiIndexService) {
        this.yamlTraversal = yamlTraversal;
        this.referencePrefixExtractor = referencePrefixExtractor;
        this.openApiIndexService = openApiIndexService;
    }

    @Override
    public void fillCompletionVariants(@NotNull CompletionParameters parameters, @NotNull CompletionResultSet result) {
        final boolean isMainOpenApiFile = openApiIndexService.isMainOpenApiFile(
                parameters.getOriginalFile().getVirtualFile(),
                parameters.getOriginalFile().getProject());

        final boolean isPartialOpenApiFile = openApiIndexService.isPartialOpenApiFile(
                parameters.getOriginalFile().getVirtualFile(),
                parameters.getOriginalFile().getProject());

        if (isMainOpenApiFile || isPartialOpenApiFile) {
            final PsiElement psiElement = parameters.getPosition();
            final OpenApiFileType openApiFileType = isMainOpenApiFile
                    ? OpenApiFileType.MAIN
                    : openApiIndexService.getOpenApiFileType(
                    parameters.getOriginalFile().getVirtualFile(),
                    parameters.getOriginalFile().getProject());

            final PathResolver pathResolver = PathResolverFactory.fromOpenApiFileType(openApiFileType);

            final OpenApiCompletionHelper completionHelper = new OpenApiCompletionHelper(psiElement, yamlTraversal, pathResolver);

            OpenApiFieldCompletionFactory.from(completionHelper, result)
                    .ifPresent(FieldCompletion::fill);

            OpenApiValueCompletionFactory.from(completionHelper, getResultSetWithPrefixMatcher(parameters, result))
                    .ifPresent(ValueCompletion::fill);

            result.stopHere();
        }
    }

    private CompletionResultSet getResultSetWithPrefixMatcher(final @NotNull CompletionParameters parameters,
                                                              final @NotNull CompletionResultSet result) {
        return referencePrefixExtractor.getPrefix(parameters.getOffset() - 1, parameters.getOriginalFile().getText())
                .map(result::withPrefixMatcher)
                .orElse(result);
    }

}
