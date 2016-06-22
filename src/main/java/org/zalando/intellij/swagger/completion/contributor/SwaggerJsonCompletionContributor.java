package org.zalando.intellij.swagger.completion.contributor;

import com.intellij.codeInsight.completion.CompletionContributor;
import com.intellij.codeInsight.completion.CompletionParameters;
import com.intellij.codeInsight.completion.CompletionResultSet;
import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;
import org.zalando.intellij.swagger.completion.field.FieldCompletion;
import org.zalando.intellij.swagger.completion.field.FieldCompletionFactory;
import org.zalando.intellij.swagger.completion.value.ValueCompletion;
import org.zalando.intellij.swagger.completion.value.ValueCompletionFactory;
import org.zalando.intellij.swagger.file.FileDetector;
import org.zalando.intellij.swagger.traversal.CompletionHelper;
import org.zalando.intellij.swagger.traversal.CustomCompletionPrefixExtractor;
import org.zalando.intellij.swagger.traversal.JsonTraversal;

public class SwaggerJsonCompletionContributor extends CompletionContributor {

    private final FileDetector fileDetector;
    private final JsonTraversal jsonTraversal;
    private final CustomCompletionPrefixExtractor customCompletionPrefixExtractor;

    public SwaggerJsonCompletionContributor() {
        this(new FileDetector(), new JsonTraversal());
    }

    private SwaggerJsonCompletionContributor(final FileDetector fileDetector,
                                             final JsonTraversal jsonTraversal) {
        this.fileDetector = fileDetector;
        this.jsonTraversal = jsonTraversal;
        this.customCompletionPrefixExtractor = new CustomCompletionPrefixExtractor(jsonTraversal);
    }

    @Override
    public void fillCompletionVariants(@NotNull CompletionParameters parameters, @NotNull CompletionResultSet result) {
        if (!fileDetector.isSwaggerJsonFile(parameters.getOriginalFile())) {
            return;
        }

        final PsiElement psiElement = parameters.getPosition();
        final CompletionHelper completionHelper = new CompletionHelper(psiElement, jsonTraversal);

        if (jsonTraversal.isKey(psiElement)) {
            FieldCompletionFactory.from(completionHelper, result)
                    .ifPresent(FieldCompletion::fill);
        } else {
            ValueCompletionFactory.from(completionHelper, getResultSetWithPrefixMatcher(parameters, result))
                    .ifPresent(ValueCompletion::fill);
        }

        result.stopHere();
    }

    private CompletionResultSet getResultSetWithPrefixMatcher(final @NotNull CompletionParameters parameters,
                                                              final @NotNull CompletionResultSet result) {
        return customCompletionPrefixExtractor.getPrefix(parameters.getPosition(), parameters.getOffset())
                .map(result::withPrefixMatcher)
                .orElse(result);
    }

}
