package org.zalando.intellij.swagger.examples.extensions.zalando.field.completion.openapi;

import com.intellij.codeInsight.completion.CompletionResultSet;
import com.intellij.openapi.diagnostic.Logger;

import java.util.Optional;

import org.zalando.intellij.swagger.completion.OpenApiCompletionHelper;
import org.zalando.intellij.swagger.completion.field.FieldCompletion;
import org.zalando.intellij.swagger.extensions.completion.openapi.OpenApiCustomFieldCompletionFactory;

public class OpenApiFieldFactory implements OpenApiCustomFieldCompletionFactory {

    @Override
    public Optional<FieldCompletion> from(
            OpenApiCompletionHelper openApiCompletionHelper, CompletionResultSet completionResultSet) {
        if (openApiCompletionHelper.hasPath("$.info")) {
            return Optional.of(new InfoCompletion(openApiCompletionHelper, completionResultSet));
        } else {
            return Optional.empty();
        }
    }
}