package org.zalando.intellij.swagger.completion.field;

import com.intellij.codeInsight.completion.CompletionResultSet;
import org.zalando.intellij.swagger.traversal.PositionResolver;

class DefinitionsCompletion extends FieldCompletion {

    DefinitionsCompletion(final PositionResolver positionResolver, final CompletionResultSet completionResultSet) {
        super(positionResolver, completionResultSet);
    }

    @Override
    public void fill() {
        new SchemaCompletion(positionResolver, completionResultSet).fill();
    }

}
