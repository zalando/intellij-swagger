package org.zalando.intellij.swagger.completion.contributor;

import com.intellij.codeInsight.completion.CompletionContributor;
import com.intellij.codeInsight.completion.CompletionParameters;
import com.intellij.codeInsight.completion.CompletionResultSet;
import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.yaml.psi.YAMLKeyValue;
import org.zalando.intellij.swagger.completion.level.LevelCompletionFactory;
import org.zalando.intellij.swagger.completion.level.inserthandler.YamlInsertHandler;
import org.zalando.intellij.swagger.completion.level.value.ValueCompletionFactory;
import org.zalando.intellij.swagger.completion.traversal.PositionResolver;
import org.zalando.intellij.swagger.completion.traversal.YamlTraversal;
import org.zalando.intellij.swagger.file.FileDetector;

public class SwaggerYamlCompletionContributor extends CompletionContributor {

    private final FileDetector fileDetector;
    private final YamlTraversal yamlTraversal;
    private final YamlInsertHandler yamlInsertHandler;

    /* Constructor for IntelliJ IDEA bootstrap */
    public SwaggerYamlCompletionContributor() {
        this(new FileDetector(), new YamlTraversal(), new YamlInsertHandler());
    }

    private SwaggerYamlCompletionContributor(final FileDetector fileDetector,
                                             final YamlTraversal yamlTraversal,
                                             final YamlInsertHandler yamlInsertHandler) {
        this.fileDetector = fileDetector;
        this.yamlTraversal = yamlTraversal;
        this.yamlInsertHandler = yamlInsertHandler;
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
        LevelCompletionFactory.from(positionResolver)
                .ifPresent(levelCompletion -> levelCompletion.fill(result, yamlInsertHandler));

        ValueCompletionFactory.from(positionResolver)
                .ifPresent(valueCompletion -> valueCompletion.fill(result));
    }
}
