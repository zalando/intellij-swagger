package org.zalando.intellij.swagger.completion.value;

import com.intellij.codeInsight.completion.CompletionResultSet;
import org.zalando.intellij.swagger.completion.value.model.StringValue;
import org.zalando.intellij.swagger.completion.value.model.Value;
import org.zalando.intellij.swagger.traversal.PositionResolver;

import java.util.List;
import java.util.stream.Collectors;

class DefinitionRefValueCompletion extends ValueCompletion {

    protected DefinitionRefValueCompletion(final PositionResolver positionResolver, final CompletionResultSet completionResultSet) {
        super(positionResolver, completionResultSet);
    }

    @Override
    public void fill() {
        getDefinitionKeys().stream().forEach(this::addValue);
    }

    private List<Value> getDefinitionKeys() {
        return positionResolver.getKeyNamesOf("definitions").stream()
                .map(keyName -> "#/definitions/" + keyName)
                .map(StringValue::new)
                .collect(Collectors.toList());
    }
}
