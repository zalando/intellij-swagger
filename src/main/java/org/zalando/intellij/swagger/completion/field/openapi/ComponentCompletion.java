package org.zalando.intellij.swagger.completion.field.openapi;

import com.intellij.codeInsight.completion.CompletionResultSet;
import org.zalando.intellij.swagger.completion.CompletionHelper;
import org.zalando.intellij.swagger.completion.field.FieldCompletion;
import org.zalando.intellij.swagger.completion.field.model.openapi.OpenApiFields;

class ComponentCompletion extends FieldCompletion {

    ComponentCompletion(final CompletionHelper completionHelper, final CompletionResultSet completionResultSet) {
        super(completionHelper, completionResultSet);
    }

    public void fill() {
        OpenApiFields.component().forEach(this::addUnique);
    }

}
