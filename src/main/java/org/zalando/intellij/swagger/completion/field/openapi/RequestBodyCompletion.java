package org.zalando.intellij.swagger.completion.field.openapi;

import com.intellij.codeInsight.completion.CompletionResultSet;
import org.zalando.intellij.swagger.completion.CompletionHelper;
import org.zalando.intellij.swagger.completion.field.FieldCompletion;
import org.zalando.intellij.swagger.completion.field.model.common.CommonFields;
import org.zalando.intellij.swagger.completion.field.model.openapi.OpenApiFields;

class RequestBodyCompletion extends FieldCompletion {

    RequestBodyCompletion(final CompletionHelper completionHelper, final CompletionResultSet completionResultSet) {
        super(completionHelper, completionResultSet);
    }

    public void fill() {
        OpenApiFields.requestBody().forEach(this::addUnique);
    }

}
