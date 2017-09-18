package org.zalando.intellij.swagger.completion.value.completion.swagger;

import com.intellij.codeInsight.completion.CompletionResultSet;
import org.zalando.intellij.swagger.completion.CompletionHelper;
import org.zalando.intellij.swagger.completion.value.ValueCompletion;
import org.zalando.intellij.swagger.completion.value.model.swagger.SwaggerValues;

class ParametersCollectionFormatValueCompletion extends ValueCompletion {

    ParametersCollectionFormatValueCompletion(final CompletionHelper completionHelper, final CompletionResultSet completionResultSet) {
        super(completionHelper, completionResultSet);
    }

    @Override
    public void fill() {
        SwaggerValues.parametersCollectionFormat().forEach(this::addValue);
    }
}
