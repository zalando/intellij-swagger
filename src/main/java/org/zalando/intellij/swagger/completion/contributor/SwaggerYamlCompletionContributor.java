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
import org.zalando.intellij.swagger.insert.YamlInsertValueHandler;
import org.zalando.intellij.swagger.traversal.PositionResolver;
import org.zalando.intellij.swagger.traversal.YamlTraversal;
import org.zalando.intellij.swagger.traversal.keydepth.YamlCompletionKeyDepth;

public class SwaggerYamlCompletionContributor extends CompletionContributor {

    private final FileDetector fileDetector;
    private final YamlTraversal yamlTraversal;
    private final YamlInsertValueHandler yamlInsertValueHandler;

    /* Constructor for IntelliJ IDEA bootstrap */
    public SwaggerYamlCompletionContributor() {
        this(new FileDetector(), new YamlTraversal(new YamlCompletionKeyDepth()), new YamlInsertValueHandler());
    }

    private SwaggerYamlCompletionContributor(final FileDetector fileDetector,
                                             final YamlTraversal yamlTraversal,
                                             final YamlInsertValueHandler yamlInsertValueHandler) {
        this.fileDetector = fileDetector;
        this.yamlTraversal = yamlTraversal;
        this.yamlInsertValueHandler = yamlInsertValueHandler;
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

        final PositionResolver positionResolver = new PositionResolver(psiElement, yamlTraversal);

        FieldCompletionFactory.from(positionResolver, result)
                .ifPresent(FieldCompletion::fill);

        ValueCompletionFactory.from(positionResolver, getResultSetWithPrefixMatcher(parameters, result))
                .ifPresent(ValueCompletion::fill);

        result.stopHere();
    }

    private CompletionResultSet getResultSetWithPrefixMatcher(final @NotNull CompletionParameters parameters,
                                                              final @NotNull CompletionResultSet result) {
        return yamlTraversal.getCustomCompletionPrefix(parameters.getPosition(), parameters.getOffset())
                .map(result::withPrefixMatcher)
                .orElse(result);
    }

}
