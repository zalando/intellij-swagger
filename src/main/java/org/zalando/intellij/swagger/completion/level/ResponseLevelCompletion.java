package org.zalando.intellij.swagger.completion.level;

import com.intellij.codeInsight.completion.CompletionResultSet;
import org.zalando.intellij.swagger.completion.level.field.Fields;
import org.zalando.intellij.swagger.completion.traversal.PositionResolver;

class ResponseLevelCompletion extends LevelCompletion {

    ResponseLevelCompletion(final PositionResolver positionResolver, final CompletionResultSet completionResultSet) {
        super(positionResolver, completionResultSet);
    }

    public void fill() {
        Fields.response().forEach(this::addUnique);
    }

}
