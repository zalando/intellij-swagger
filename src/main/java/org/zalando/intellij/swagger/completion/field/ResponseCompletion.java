package org.zalando.intellij.swagger.completion.field;

import com.intellij.codeInsight.completion.CompletionResultSet;
import org.zalando.intellij.swagger.completion.field.model.Fields;
import org.zalando.intellij.swagger.completion.CompletionHelper;

class ResponseCompletion extends FieldCompletion {

    ResponseCompletion(final CompletionHelper completionHelper, final CompletionResultSet completionResultSet) {
        super(completionHelper, completionResultSet);
    }

    public void fill() {
        Fields.response().forEach(this::addUnique);
    }

}
