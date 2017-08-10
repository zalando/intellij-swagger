package org.zalando.intellij.swagger.completion.value.swagger;

import com.intellij.codeInsight.completion.CompletionResultSet;
import org.zalando.intellij.swagger.completion.CompletionHelper;
import org.zalando.intellij.swagger.completion.value.ValueCompletion;
import org.zalando.intellij.swagger.completion.value.model.StringValue;
import org.zalando.intellij.swagger.completion.value.model.Value;
import org.zalando.intellij.swagger.completion.SwaggerCompletionHelper;

import java.util.List;
import java.util.stream.Collectors;

class TagsValueCompletion extends ValueCompletion {

    TagsValueCompletion(final CompletionHelper completionHelper, final CompletionResultSet completionResultSet) {
        super(completionHelper, completionResultSet);
    }

    @Override
    public void fill() {
        getTags().forEach(this::addValue);
    }

    private List<Value> getTags() {
        return completionHelper.getTagNames().stream()
                .map(StringValue::new)
                .collect(Collectors.toList());
    }
}
