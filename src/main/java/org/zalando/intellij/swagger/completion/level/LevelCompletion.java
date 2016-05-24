package org.zalando.intellij.swagger.completion.level;

import com.intellij.codeInsight.completion.CompletionResultSet;
import com.intellij.codeInsight.completion.InsertHandler;
import com.intellij.codeInsight.lookup.LookupElement;
import org.jetbrains.annotations.NotNull;
import org.zalando.intellij.swagger.completion.traversal.PositionResolver;

public abstract class LevelCompletion {

    final PositionResolver positionResolver;

    protected LevelCompletion(final PositionResolver positionResolver) {
        this.positionResolver = positionResolver;
    }

    public abstract void fill(@NotNull final CompletionResultSet result,
                              @NotNull final InsertHandler<LookupElement> insertHandler);
}
