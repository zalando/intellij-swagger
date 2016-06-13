package org.zalando.intellij.swagger.completion.contributor;

import com.intellij.codeInsight.completion.CompletionContributor;
import com.intellij.codeInsight.completion.CompletionParameters;
import com.intellij.codeInsight.completion.CompletionResultSet;
import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.yaml.psi.YAMLKeyValue;
import org.zalando.intellij.swagger.completion.level.LevelCompletion;
import org.zalando.intellij.swagger.completion.level.LevelCompletionFactory;
import org.zalando.intellij.swagger.completion.level.inserthandler.YamlValueInsertHandler;
import org.zalando.intellij.swagger.completion.level.value.ValueCompletionFactory;
import org.zalando.intellij.swagger.completion.traversal.PositionResolver;
import org.zalando.intellij.swagger.completion.traversal.keydepth.YamlCompletionKeyDepth;
import org.zalando.intellij.swagger.completion.traversal.YamlTraversal;
import org.zalando.intellij.swagger.file.FileDetector;

public class SwaggerYamlCompletionContributor extends CompletionContributor {

    private final FileDetector fileDetector;
    private final YamlTraversal yamlTraversal;
    private final YamlValueInsertHandler yamlValueInsertHandler;

    /* Constructor for IntelliJ IDEA bootstrap */
    public SwaggerYamlCompletionContributor() {
        this(new FileDetector(), new YamlTraversal(new YamlCompletionKeyDepth()), new YamlValueInsertHandler());
    }

    private SwaggerYamlCompletionContributor(final FileDetector fileDetector,
                                             final YamlTraversal yamlTraversal,
                                             final YamlValueInsertHandler yamlValueInsertHandler) {
        this.fileDetector = fileDetector;
        this.yamlTraversal = yamlTraversal;
        this.yamlValueInsertHandler = yamlValueInsertHandler;
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

        LevelCompletionFactory.from(positionResolver, result)
                .ifPresent(LevelCompletion::fill);

        CompletionResultSet withFixedPrefix =
                yamlTraversal.getCustomCompletionPrefix(parameters.getPosition(), parameters.getOffset())
                        .map(result::withPrefixMatcher)
                        .orElse(result);

        ValueCompletionFactory.from(positionResolver)
                .ifPresent(valueCompletion -> valueCompletion.fill(withFixedPrefix, yamlValueInsertHandler));

        result.stopHere();
    }

}
