package org.zalando.intellij.swagger.completion.contributor.openapi;

import com.intellij.codeInsight.completion.CompletionContributor;
import com.intellij.codeInsight.completion.CompletionParameters;
import com.intellij.codeInsight.completion.CompletionResultSet;
import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;
import org.zalando.intellij.swagger.completion.OpenApiCompletionHelper;
import org.zalando.intellij.swagger.completion.contributor.CompletionResultSetFactory;
import org.zalando.intellij.swagger.completion.field.FieldCompletion;
import org.zalando.intellij.swagger.completion.field.completion.openapi.OpenApiFieldCompletionFactory;
import org.zalando.intellij.swagger.completion.value.ValueCompletion;
import org.zalando.intellij.swagger.completion.value.completion.openapi.OpenApiValueCompletionFactory;
import org.zalando.intellij.swagger.file.OpenApiFileType;
import org.zalando.intellij.swagger.index.openapi.OpenApiIndexService;
import org.zalando.intellij.swagger.traversal.JsonTraversal;
import org.zalando.intellij.swagger.traversal.path.openapi.PathResolver;
import org.zalando.intellij.swagger.traversal.path.openapi.PathResolverFactory;

public class OpenApiJsonCompletionContributor extends CompletionContributor {

    private final JsonTraversal jsonTraversal;
    private final OpenApiIndexService openApiIndexService;

    public OpenApiJsonCompletionContributor() {
        this(new JsonTraversal(), new OpenApiIndexService());
    }

    private OpenApiJsonCompletionContributor(final JsonTraversal jsonTraversal,
                                             final OpenApiIndexService openApiIndexService) {
        this.jsonTraversal = jsonTraversal;
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
                    : openApiIndexService.getOpenApiFileType(parameters.getOriginalFile().getVirtualFile(),
                    parameters.getOriginalFile().getProject());

            final PathResolver pathResolver = PathResolverFactory.fromOpenApiFileType(openApiFileType);

            final OpenApiCompletionHelper completionHelper = new OpenApiCompletionHelper(psiElement, jsonTraversal, pathResolver);

            if (jsonTraversal.isKey(psiElement)) {
                OpenApiFieldCompletionFactory.from(completionHelper, result)
                        .ifPresent(FieldCompletion::fill);
            } else {
                OpenApiValueCompletionFactory.from(completionHelper, CompletionResultSetFactory.forValue(parameters, result))
                        .ifPresent(ValueCompletion::fill);
            }

            result.stopHere();
        }
    }

}
