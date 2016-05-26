package org.zalando.intellij.swagger.completion.level;

import com.intellij.codeInsight.completion.CompletionResultSet;
import com.intellij.codeInsight.completion.InsertHandler;
import com.intellij.codeInsight.lookup.LookupElement;
import org.jetbrains.annotations.NotNull;
import org.zalando.intellij.swagger.completion.traversal.PositionResolver;

class DefinitionsLevelCompletion extends LevelCompletion {

    DefinitionsLevelCompletion(final PositionResolver positionResolver, final CompletionResultSet completionResultSet) {
        super(positionResolver, completionResultSet);
    }

    @Override
    public void fill(@NotNull final InsertHandler<LookupElement> insertHandler) {
        new SchemaLevelCompletion(positionResolver, completionResultSet).fill(insertHandler);
    }

}
