package org.zalando.intellij.swagger.completion.field;

import com.intellij.codeInsight.completion.CompletionResultSet;
import org.zalando.intellij.swagger.completion.field.model.Fields;
import org.zalando.intellij.swagger.traversal.PositionResolver;

class ExternalDocsCompletion extends FieldCompletion {

    ExternalDocsCompletion(final PositionResolver positionResolver, final CompletionResultSet completionResultSet) {
        super(positionResolver, completionResultSet);
    }

    @Override
    public void fill() {
        Fields.externalDocs().forEach(this::addUnique);
    }
}
