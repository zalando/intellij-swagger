package org.zalando.intellij.swagger.completion.contributor;

import com.intellij.codeInsight.completion.CompletionContributor;
import com.intellij.codeInsight.completion.CompletionParameters;
import com.intellij.codeInsight.completion.CompletionResultSet;
import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;
import org.zalando.intellij.swagger.completion.level.LevelCompletionFactory;
import org.zalando.intellij.swagger.completion.traversal.JsonTraversal;
import org.zalando.intellij.swagger.completion.traversal.PositionResolver;
import org.zalando.intellij.swagger.file.FileDetector;

public class SwaggerJsonCompletionContributor extends CompletionContributor {

    private FileDetector fileDetector;
    private JsonTraversal jsonTraversal;

    /* Constructor for IntelliJ IDEA bootstrap */
    public SwaggerJsonCompletionContributor() {
        this(new FileDetector(), new JsonTraversal());
    }

    private SwaggerJsonCompletionContributor(FileDetector fileDetector,
                                             JsonTraversal jsonTraversal) {
        this.fileDetector = fileDetector;
        this.jsonTraversal = jsonTraversal;
    }

    @Override
    public void fillCompletionVariants(@NotNull CompletionParameters parameters, @NotNull CompletionResultSet result) {
        if (!fileDetector.isSwaggerJsonFile(parameters.getOriginalFile())) {
            return;
        }

        final PsiElement psiElement = parameters.getPosition();
        if (jsonTraversal.isKey(psiElement)) {
            final PositionResolver positionResolver = new PositionResolver(psiElement, jsonTraversal);
            LevelCompletionFactory.from(positionResolver)
                    .ifPresent(levelCompletion -> levelCompletion.fill(result, positionResolver.shouldQuote()));
        }
    }
}
