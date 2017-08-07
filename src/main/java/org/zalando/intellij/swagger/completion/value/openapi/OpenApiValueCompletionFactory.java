package org.zalando.intellij.swagger.completion.value.openapi;

import com.intellij.codeInsight.completion.CompletionResultSet;
import org.zalando.intellij.swagger.completion.OpenApiCompletionHelper;
import org.zalando.intellij.swagger.completion.value.ValueCompletion;

import java.util.Optional;

public class OpenApiValueCompletionFactory {

    public static Optional<ValueCompletion> from(final OpenApiCompletionHelper completionHelper,
                                                 final CompletionResultSet completionResultSet) {
        return Optional.empty();
    }
}
