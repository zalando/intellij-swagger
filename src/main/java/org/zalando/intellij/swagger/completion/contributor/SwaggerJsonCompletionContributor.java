package org.zalando.intellij.swagger.completion.contributor;

import com.intellij.codeInsight.completion.CompletionContributor;
import com.intellij.codeInsight.completion.CompletionParameters;
import com.intellij.codeInsight.completion.CompletionResultSet;
import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;
import org.zalando.intellij.swagger.completion.level.LevelCompletionFactory;
import org.zalando.intellij.swagger.completion.level.inserthandler.JsonInsertHandler;
import org.zalando.intellij.swagger.completion.level.value.ValueCompletionFactory;
import org.zalando.intellij.swagger.completion.traversal.JsonTraversal;
import org.zalando.intellij.swagger.completion.traversal.PositionResolver;
import org.zalando.intellij.swagger.file.FileDetector;

public class SwaggerJsonCompletionContributor extends CompletionContributor {

    private FileDetector fileDetector;
    private JsonTraversal jsonTraversal;
    private JsonInsertHandler jsonInsertHandler;

    /* Constructor for IntelliJ IDEA bootstrap */
    public SwaggerJsonCompletionContributor() {
        this(new FileDetector(), new JsonTraversal(), new JsonInsertHandler());
    }

    private SwaggerJsonCompletionContributor(final FileDetector fileDetector,
                                             final JsonTraversal jsonTraversal,
                                             final JsonInsertHandler jsonInsertHandler) {
        this.fileDetector = fileDetector;
        this.jsonTraversal = jsonTraversal;
        this.jsonInsertHandler = jsonInsertHandler;
    }

    @Override
    public void fillCompletionVariants(@NotNull CompletionParameters parameters, @NotNull CompletionResultSet result) {
        if (!fileDetector.isSwaggerJsonFile(parameters.getOriginalFile())) {
            return;
        }

        final PsiElement psiElement = parameters.getPosition();
        final PositionResolver positionResolver = new PositionResolver(psiElement, jsonTraversal);
        if (jsonTraversal.isKey(psiElement)) {
            LevelCompletionFactory.from(positionResolver)
                    .ifPresent(levelCompletion -> levelCompletion.fill(result, jsonInsertHandler));
        } else {
            ValueCompletionFactory.from(positionResolver)
                    .ifPresent(valueCompletion -> valueCompletion.fill(result));
        }
    }
}
