package org.zalando.intellij.swagger.extensions.completion.openapi;

import com.intellij.codeInsight.completion.CompletionResultSet;
import com.intellij.openapi.extensions.ExtensionPointName;
import java.util.Optional;
import org.zalando.intellij.swagger.completion.OpenApiCompletionHelper;
import org.zalando.intellij.swagger.completion.value.ValueCompletion;

public interface OpenApiCustomValueCompletionFactory {
  ExtensionPointName<OpenApiCustomValueCompletionFactory> EP_NAME =
      ExtensionPointName.create("org.zalando.intellij.openapi.customValueFactory");

  Optional<ValueCompletion> from(
      final OpenApiCompletionHelper completionHelper,
      final CompletionResultSet completionResultSet);
}
