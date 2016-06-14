package org.zalando.intellij.swagger.completion.field;

import com.intellij.codeInsight.completion.CompletionResultSet;
import org.zalando.intellij.swagger.completion.field.model.Fields;
import org.zalando.intellij.swagger.traversal.PositionResolver;

class PathCompletion extends FieldCompletion {

    PathCompletion(final PositionResolver positionResolver, final CompletionResultSet completionResultSet) {
        super(positionResolver, completionResultSet);
    }

    public void fill() {
        Fields.path().forEach(this::addUnique);
    }

}
