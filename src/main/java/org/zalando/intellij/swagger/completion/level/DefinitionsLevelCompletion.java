package org.zalando.intellij.swagger.completion.level;

import com.intellij.codeInsight.completion.CompletionResultSet;
import org.jetbrains.annotations.NotNull;
import org.zalando.intellij.swagger.completion.traversal.PositionResolver;

class DefinitionsLevelCompletion extends LevelCompletion {

    DefinitionsLevelCompletion(final PositionResolver positionResolver) {
        super(positionResolver);
    }

    @Override
    public void fill(@NotNull final CompletionResultSet result) {
        new SchemaLevelCompletion(positionResolver).fill(result);
    }

}
