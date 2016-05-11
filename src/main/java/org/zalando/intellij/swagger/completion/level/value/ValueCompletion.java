package org.zalando.intellij.swagger.completion.level.value;

import com.intellij.codeInsight.completion.CompletionResultSet;
import org.jetbrains.annotations.NotNull;
import org.zalando.intellij.swagger.completion.traversal.PositionResolver;

public abstract class ValueCompletion {

    final PositionResolver positionResolver;

    ValueCompletion(final PositionResolver positionResolver) {
        this.positionResolver = positionResolver;
    }

    public abstract void fill(@NotNull final CompletionResultSet result);
}
