package org.zalando.intellij.swagger.completion.value;

import com.intellij.codeInsight.completion.CompletionResultSet;
import org.zalando.intellij.swagger.completion.value.model.StringValue;
import org.zalando.intellij.swagger.completion.value.model.Value;
import org.zalando.intellij.swagger.completion.CompletionHelper;

import java.util.List;
import java.util.stream.Collectors;

class TagsValueCompletion extends ValueCompletion {

    TagsValueCompletion(final CompletionHelper completionHelper, final CompletionResultSet completionResultSet) {
        super(completionHelper, completionResultSet);
    }

    @Override
    public void fill() {
        getTags().stream().forEach(this::addValue);
    }

    private List<Value> getTags() {
        return completionHelper.getTagNames().stream()
                .map(StringValue::new)
                .collect(Collectors.toList());
    }
}
