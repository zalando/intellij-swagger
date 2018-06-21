package org.zalando.intellij.swagger.extensions.completion.swagger;

import com.intellij.codeInsight.completion.CompletionResultSet;
import com.intellij.openapi.extensions.ExtensionPointName;
import org.zalando.intellij.swagger.completion.SwaggerCompletionHelper;
import org.zalando.intellij.swagger.completion.value.ValueCompletion;

import java.util.Optional;

public interface SwaggerCustomValueCompletionFactory {
    ExtensionPointName<SwaggerCustomValueCompletionFactory> EP_NAME = ExtensionPointName.create("org.zalando.intellij.swagger.customValueFactory");

    Optional<ValueCompletion> from(final SwaggerCompletionHelper completionHelper,
                                   final CompletionResultSet completionResultSet);
}
