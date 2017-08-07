package org.zalando.intellij.swagger.completion.contributor.swagger;

import com.intellij.codeInsight.completion.CompletionContributor;
import com.intellij.codeInsight.completion.CompletionParameters;
import com.intellij.codeInsight.completion.CompletionResultSet;
import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;
import org.zalando.intellij.swagger.completion.SwaggerCompletionHelper;
import org.zalando.intellij.swagger.completion.field.FieldCompletion;
import org.zalando.intellij.swagger.completion.field.swagger.SwaggerFieldCompletionFactory;
import org.zalando.intellij.swagger.completion.value.ValueCompletion;
import org.zalando.intellij.swagger.completion.value.swagger.SwaggerValueCompletionFactory;
import org.zalando.intellij.swagger.file.SwaggerFileType;
import org.zalando.intellij.swagger.index.swagger.SwaggerIndexService;
import org.zalando.intellij.swagger.traversal.JsonTraversal;
import org.zalando.intellij.swagger.traversal.path.swagger.PathResolver;
import org.zalando.intellij.swagger.traversal.path.swagger.PathResolverFactory;
import org.zalando.intellij.swagger.completion.contributor.ReferencePrefixExtractor;

public class SwaggerJsonCompletionContributor extends CompletionContributor {

    private final JsonTraversal jsonTraversal;
    private final ReferencePrefixExtractor referencePrefixExtractor;
    private final SwaggerIndexService swaggerIndexService;

    public SwaggerJsonCompletionContributor() {
        this(new JsonTraversal(), new ReferencePrefixExtractor(), new SwaggerIndexService());
    }

    private SwaggerJsonCompletionContributor(final JsonTraversal jsonTraversal,
                                             final ReferencePrefixExtractor referencePrefixExtractor,
                                             final SwaggerIndexService swaggerIndexService) {
        this.jsonTraversal = jsonTraversal;
        this.referencePrefixExtractor = referencePrefixExtractor;
        this.swaggerIndexService = swaggerIndexService;
    }

    @Override
    public void fillCompletionVariants(@NotNull CompletionParameters parameters, @NotNull CompletionResultSet result) {
        final boolean isMainSwaggerFile = swaggerIndexService.isMainSwaggerFile(
                parameters.getOriginalFile().getVirtualFile(),
                parameters.getOriginalFile().getProject());

        final boolean isPartialSwaggerFile = swaggerIndexService.isPartialSwaggerFile(
                parameters.getOriginalFile().getVirtualFile(),
                parameters.getOriginalFile().getProject());

        if (isMainSwaggerFile || isPartialSwaggerFile) {
            final PsiElement psiElement = parameters.getPosition();
            final SwaggerFileType swaggerFileType = isMainSwaggerFile
                    ? SwaggerFileType.MAIN
                    : swaggerIndexService.getSwaggerFileType(parameters.getOriginalFile().getVirtualFile(),
                    parameters.getOriginalFile().getProject());

            final PathResolver pathResolver = PathResolverFactory.fromSwaggerFileType(swaggerFileType);

            final SwaggerCompletionHelper completionHelper = new SwaggerCompletionHelper(psiElement, jsonTraversal, pathResolver);

            if (jsonTraversal.isKey(psiElement)) {
                SwaggerFieldCompletionFactory.from(completionHelper, result)
                        .ifPresent(FieldCompletion::fill);
            } else {
                SwaggerValueCompletionFactory.from(completionHelper, getResultSetWithPrefixMatcher(parameters, result))
                        .ifPresent(ValueCompletion::fill);
            }

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
