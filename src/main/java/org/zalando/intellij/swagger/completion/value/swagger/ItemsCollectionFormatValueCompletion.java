package org.zalando.intellij.swagger.completion.value.swagger;

import com.intellij.codeInsight.completion.CompletionResultSet;
import org.zalando.intellij.swagger.completion.CompletionHelper;
import org.zalando.intellij.swagger.completion.value.ValueCompletion;
import org.zalando.intellij.swagger.completion.value.model.Values;
import org.zalando.intellij.swagger.completion.SwaggerCompletionHelper;

class ItemsCollectionFormatValueCompletion extends ValueCompletion {

    ItemsCollectionFormatValueCompletion(final CompletionHelper completionHelper, final CompletionResultSet completionResultSet) {
        super(completionHelper, completionResultSet);
    }

    @Override
    public void fill() {
        Values.itemsCollectionFormat().forEach(this::addValue);
    }
}
