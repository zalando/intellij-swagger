package org.zalando.intellij.swagger.completion.contributor;

import com.intellij.codeInsight.completion.CompletionContributor;
import com.intellij.codeInsight.completion.CompletionParameters;
import com.intellij.codeInsight.completion.CompletionResultSet;
import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.yaml.psi.YAMLKeyValue;
import org.zalando.intellij.swagger.completion.field.FieldCompletion;
import org.zalando.intellij.swagger.completion.field.FieldCompletionFactory;
import org.zalando.intellij.swagger.completion.value.ValueCompletion;
import org.zalando.intellij.swagger.completion.value.ValueCompletionFactory;
import org.zalando.intellij.swagger.file.FileDetector;
import org.zalando.intellij.swagger.completion.CompletionHelper;
import org.zalando.intellij.swagger.traversal.PathResolver;
import org.zalando.intellij.swagger.traversal.ReferencePrefixExtractor;
import org.zalando.intellij.swagger.traversal.YamlTraversal;

public class SwaggerYamlCompletionContributor extends CompletionContributor {

    private final FileDetector fileDetector;
    private final YamlTraversal yamlTraversal;
    private final ReferencePrefixExtractor referencePrefixExtractor;
    private final PathResolver pathResolver;

    public SwaggerYamlCompletionContributor() {
        this(new FileDetector(), new YamlTraversal(),
                new PathResolver(), new ReferencePrefixExtractor());
    }

    private SwaggerYamlCompletionContributor(final FileDetector fileDetector,
                                             final YamlTraversal yamlTraversal,
                                             final PathResolver pathResolver,
                                             final ReferencePrefixExtractor referencePrefixExtractor) {
        this.fileDetector = fileDetector;
        this.yamlTraversal = yamlTraversal;
        this.referencePrefixExtractor = referencePrefixExtractor;
        this.pathResolver = pathResolver;
    }

    @Override
    public void fillCompletionVariants(@NotNull CompletionParameters parameters, @NotNull CompletionResultSet result) {
        if (!fileDetector.isSwaggerYamlFile(parameters.getOriginalFile())) {
            return;
        }

        PsiElement psiElement = parameters.getPosition();
        if (psiElement.getParent() instanceof YAMLKeyValue) {
            psiElement = psiElement.getParent().getParent();
        }

        final CompletionHelper completionHelper = new CompletionHelper(psiElement, yamlTraversal, pathResolver);

        FieldCompletionFactory.from(completionHelper, result)
                .ifPresent(FieldCompletion::fill);

        ValueCompletionFactory.from(completionHelper, getResultSetWithPrefixMatcher(parameters, result))
                .ifPresent(ValueCompletion::fill);

        result.stopHere();
    }

    private CompletionResultSet getResultSetWithPrefixMatcher(final @NotNull CompletionParameters parameters,
                                                              final @NotNull CompletionResultSet result) {
        return referencePrefixExtractor.getPrefix(parameters.getOffset() - 1, parameters.getOriginalFile().getText())
                .map(result::withPrefixMatcher)
                .orElse(result);
    }

}
