package org.zalando.intellij.swagger.examples.extensions.zalando.value.completion.swagger;

import com.intellij.codeInsight.completion.CompletionResultSet;
import java.util.Optional;
import org.zalando.intellij.swagger.completion.SwaggerCompletionHelper;
import org.zalando.intellij.swagger.completion.value.ValueCompletion;
import org.zalando.intellij.swagger.extensions.completion.swagger.SwaggerCustomValueCompletionFactory;

public class SwaggerValueFactory implements SwaggerCustomValueCompletionFactory {

  @Override
  public Optional<ValueCompletion> from(
      SwaggerCompletionHelper swaggerCompletionHelper, CompletionResultSet completionResultSet) {
    if (swaggerCompletionHelper.hasPath("$.info.x-audience")) {
      return Optional.of(new AudienceValueCompletion(swaggerCompletionHelper, completionResultSet));
    } else {
      return Optional.empty();
    }
  }
}
