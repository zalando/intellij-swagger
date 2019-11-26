package org.zalando.intellij.swagger.extensions.completion.openapi;

import com.intellij.codeInsight.completion.CompletionResultSet;
import com.intellij.openapi.extensions.ExtensionPointName;
import java.util.Optional;
import org.zalando.intellij.swagger.completion.OpenApiCompletionHelper;
import org.zalando.intellij.swagger.completion.field.FieldCompletion;

public interface OpenApiCustomFieldCompletionFactory {
  ExtensionPointName<OpenApiCustomFieldCompletionFactory> EP_NAME =
      ExtensionPointName.create("org.zalando.intellij.openapi.customFieldFactory");

  Optional<FieldCompletion> from(
      final OpenApiCompletionHelper completionHelper,
      final CompletionResultSet completionResultSet);
}
