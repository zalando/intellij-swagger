package org.zalando.intellij.swagger.completion.field.openapi;

import com.intellij.codeInsight.completion.CompletionResultSet;
import org.zalando.intellij.swagger.completion.OpenApiCompletionHelper;
import org.zalando.intellij.swagger.completion.field.FieldCompletion;

import java.util.Optional;

public class OpenApiFieldCompletionFactory {

    public static Optional<FieldCompletion> from(final OpenApiCompletionHelper completionHelper,
                                                 final CompletionResultSet completionResultSet) {
        return Optional.empty();
    }
}
