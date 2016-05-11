package org.zalando.intellij.swagger.completion.level;

import com.intellij.codeInsight.completion.CompletionResultSet;
import org.jetbrains.annotations.NotNull;
import org.zalando.intellij.swagger.completion.traversal.PositionResolver;

class ParameterDefinitionLevelCompletion extends LevelCompletion {

    ParameterDefinitionLevelCompletion(final PositionResolver positionResolver) {
        super(positionResolver);
    }

    public void fill(@NotNull final CompletionResultSet result) {
        new ParametersLevelCompletion(positionResolver).fill(result);
    }

}
