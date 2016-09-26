package org.zalando.intellij.swagger.completion.contributor;

import com.intellij.codeInsight.completion.CompletionContributor;
import com.intellij.codeInsight.completion.CompletionParameters;
import com.intellij.codeInsight.completion.CompletionResultSet;
import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;
import org.zalando.intellij.swagger.completion.CompletionHelper;
import org.zalando.intellij.swagger.completion.field.FieldCompletion;
import org.zalando.intellij.swagger.completion.field.FieldCompletionFactory;
import org.zalando.intellij.swagger.completion.value.ValueCompletion;
import org.zalando.intellij.swagger.completion.value.ValueCompletionFactory;
import org.zalando.intellij.swagger.file.SwaggerFileType;
import org.zalando.intellij.swagger.index.SwaggerIndexService;
import org.zalando.intellij.swagger.traversal.PathResolver;
import org.zalando.intellij.swagger.traversal.PathResolverFactory;
import org.zalando.intellij.swagger.traversal.ReferencePrefixExtractor;
import org.zalando.intellij.swagger.traversal.YamlTraversal;

public class SwaggerYamlCompletionContributor extends CompletionContributor {

    private final YamlTraversal yamlTraversal;
    private final ReferencePrefixExtractor referencePrefixExtractor;
    private final SwaggerIndexService swaggerIndexService;

    public SwaggerYamlCompletionContributor() {
        this(new YamlTraversal(), new ReferencePrefixExtractor(), new SwaggerIndexService());
    }

    private SwaggerYamlCompletionContributor(final YamlTraversal yamlTraversal,
                                             final ReferencePrefixExtractor referencePrefixExtractor,
                                             final SwaggerIndexService swaggerIndexService) {
        this.yamlTraversal = yamlTraversal;
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
                    : swaggerIndexService.getSwaggerFileType(
                    parameters.getOriginalFile().getVirtualFile(),
                    parameters.getOriginalFile().getProject());

            final PathResolver pathResolver = PathResolverFactory.fromSwaggerFileType(swaggerFileType);

            final CompletionHelper completionHelper = new CompletionHelper(psiElement, yamlTraversal, pathResolver);

            FieldCompletionFactory.from(completionHelper, result)
                    .ifPresent(FieldCompletion::fill);

            ValueCompletionFactory.from(completionHelper, getResultSetWithPrefixMatcher(parameters, result))
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
