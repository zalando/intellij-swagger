package org.zalando.intellij.swagger.completion.value.swagger;

import com.intellij.codeInsight.completion.CompletionResultSet;
import org.zalando.intellij.swagger.completion.CompletionHelper;
import org.zalando.intellij.swagger.completion.value.ValueCompletion;
import org.zalando.intellij.swagger.completion.value.model.StringValue;
import org.zalando.intellij.swagger.completion.value.model.Value;
import org.zalando.intellij.swagger.completion.SwaggerCompletionHelper;

import java.util.List;
import java.util.stream.Collectors;

class DefinitionRefValueCompletion extends ValueCompletion {

    DefinitionRefValueCompletion(final CompletionHelper completionHelper, final CompletionResultSet completionResultSet) {
        super(completionHelper, completionResultSet);
    }

    @Override
    public void fill() {
        getDefinitionKeys().forEach(this::addValue);
    }

    private List<Value> getDefinitionKeys() {
        return completionHelper.getKeyNamesOfDefinition("definitions").stream()
                .map(keyName -> "#/definitions/" + keyName)
                .map(StringValue::new)
                .collect(Collectors.toList());
    }
}
