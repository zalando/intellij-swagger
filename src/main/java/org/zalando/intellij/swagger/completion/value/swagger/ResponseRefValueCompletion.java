package org.zalando.intellij.swagger.completion.value.swagger;

import com.intellij.codeInsight.completion.CompletionResultSet;
import org.zalando.intellij.swagger.completion.value.ValueCompletion;
import org.zalando.intellij.swagger.completion.value.model.StringValue;
import org.zalando.intellij.swagger.completion.value.model.Value;
import org.zalando.intellij.swagger.completion.SwaggerCompletionHelper;

import java.util.List;
import java.util.stream.Collectors;

class ResponseRefValueCompletion extends ValueCompletion {

    ResponseRefValueCompletion(final SwaggerCompletionHelper completionHelper, final CompletionResultSet completionResultSet) {
        super(completionHelper, completionResultSet);
    }

    @Override
    public void fill() {
        getResponseKeys().stream().forEach(this::addValue);
    }

    private List<Value> getResponseKeys() {
        return completionHelper.getKeyNamesOfDefinition("responses").stream()
                .map(keyName -> "#/responses/" + keyName)
                .map(StringValue::new)
                .collect(Collectors.toList());
    }
}
