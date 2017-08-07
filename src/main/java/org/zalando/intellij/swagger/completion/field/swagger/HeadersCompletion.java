package org.zalando.intellij.swagger.completion.field.swagger;

import com.intellij.codeInsight.completion.CompletionResultSet;
import org.zalando.intellij.swagger.completion.field.FieldCompletion;
import org.zalando.intellij.swagger.completion.field.model.Fields;
import org.zalando.intellij.swagger.completion.SwaggerCompletionHelper;

class HeadersCompletion extends FieldCompletion {

    HeadersCompletion(final SwaggerCompletionHelper completionHelper, final CompletionResultSet completionResultSet) {
        super(completionHelper, completionResultSet);
    }

    @Override
    public void fill() {
        Fields.headers().forEach(this::addUnique);
    }
}
