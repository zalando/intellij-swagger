package org.zalando.intellij.swagger.completion.field.openapi;

import com.intellij.codeInsight.completion.CompletionResultSet;
import org.zalando.intellij.swagger.completion.OpenApiCompletionHelper;
import org.zalando.intellij.swagger.completion.field.FieldCompletion;
import org.zalando.intellij.swagger.completion.field.model.Fields;

class RootCompletion extends FieldCompletion {

    RootCompletion(final OpenApiCompletionHelper completionHelper, final CompletionResultSet completionResultSet) {
        super(completionHelper, completionResultSet);
    }

    public void fill() {
        Fields.root().forEach(this::addUnique);
    }

}
