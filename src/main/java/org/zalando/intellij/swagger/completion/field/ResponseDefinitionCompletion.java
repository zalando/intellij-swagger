package org.zalando.intellij.swagger.completion.field;

import com.intellij.codeInsight.completion.CompletionResultSet;
import org.zalando.intellij.swagger.completion.field.model.Fields;
import org.zalando.intellij.swagger.completion.CompletionHelper;

class ResponseDefinitionCompletion extends FieldCompletion {

    ResponseDefinitionCompletion(final CompletionHelper completionHelper, final CompletionResultSet completionResultSet) {
        super(completionHelper, completionResultSet);
    }

    public void fill() {
        Fields.responseDefinition().forEach(this::addUnique);
    }

}
