package org.zalando.intellij.swagger.completion.field.swagger;

import com.intellij.codeInsight.completion.CompletionResultSet;
import org.zalando.intellij.swagger.completion.CompletionHelper;
import org.zalando.intellij.swagger.completion.field.FieldCompletion;
import org.zalando.intellij.swagger.completion.field.model.swagger.SwaggerFields;
import org.zalando.intellij.swagger.completion.SwaggerCompletionHelper;

class OperationCompletion extends FieldCompletion {

    OperationCompletion(final CompletionHelper completionHelper, final CompletionResultSet completionResultSet) {
        super(completionHelper, completionResultSet);
    }

    public void fill() {
        SwaggerFields.operation().forEach(this::addUnique);
    }

}
