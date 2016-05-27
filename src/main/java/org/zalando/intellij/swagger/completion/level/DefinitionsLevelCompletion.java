package org.zalando.intellij.swagger.completion.level;

import com.intellij.codeInsight.completion.CompletionResultSet;
import org.zalando.intellij.swagger.completion.traversal.PositionResolver;

class DefinitionsLevelCompletion extends LevelCompletion {

    DefinitionsLevelCompletion(final PositionResolver positionResolver, final CompletionResultSet completionResultSet) {
        super(positionResolver, completionResultSet);
    }

    @Override
    public void fill() {
        new SchemaLevelCompletion(positionResolver, completionResultSet).fill();
    }

}
