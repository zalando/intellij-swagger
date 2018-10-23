package org.zalando.intellij.swagger.extensions.completion.swagger;

import com.intellij.codeInsight.completion.CompletionResultSet;
import com.intellij.openapi.extensions.ExtensionPointName;
import java.util.Optional;
import org.zalando.intellij.swagger.completion.SwaggerCompletionHelper;
import org.zalando.intellij.swagger.completion.field.FieldCompletion;

public interface SwaggerCustomFieldCompletionFactory {
  ExtensionPointName<SwaggerCustomFieldCompletionFactory> EP_NAME =
      ExtensionPointName.create("org.zalando.intellij.swagger.customFieldFactory");

  Optional<FieldCompletion> from(
      final SwaggerCompletionHelper completionHelper,
      final CompletionResultSet completionResultSet);
}
