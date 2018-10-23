package org.zalando.intellij.swagger.examples.extensions.zalando.field.completion.swagger;

import com.intellij.codeInsight.completion.CompletionResultSet;
import java.util.Optional;
import org.zalando.intellij.swagger.completion.SwaggerCompletionHelper;
import org.zalando.intellij.swagger.completion.field.FieldCompletion;
import org.zalando.intellij.swagger.extensions.completion.swagger.SwaggerCustomFieldCompletionFactory;

public class SwaggerFieldFactory implements SwaggerCustomFieldCompletionFactory {

  @Override
  public Optional<FieldCompletion> from(
      SwaggerCompletionHelper swaggerCompletionHelper, CompletionResultSet completionResultSet) {
    if (swaggerCompletionHelper.hasPath("$.info")) {
      return Optional.of(new InfoCompletion(swaggerCompletionHelper, completionResultSet));
    } else {
      return Optional.empty();
    }
  }
}
