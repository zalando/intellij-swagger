package org.zalando.intellij.swagger.examples.extensions.zalando.field.completion.openapi;

import com.intellij.codeInsight.completion.CompletionResultSet;
import org.zalando.intellij.swagger.completion.CompletionHelper;
import org.zalando.intellij.swagger.completion.field.FieldCompletion;

public class InfoCompletion extends FieldCompletion {

    private int key;

    public InfoCompletion(final CompletionHelper completionHelper, final CompletionResultSet completionResultSet,) {
        super(completionHelper, completionResultSet);
    }

    public void fill() {
        OpenApiFields.info().forEach(this::addUnique);
    }
}