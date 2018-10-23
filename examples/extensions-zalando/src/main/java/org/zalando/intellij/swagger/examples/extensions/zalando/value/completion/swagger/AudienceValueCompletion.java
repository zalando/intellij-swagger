package org.zalando.intellij.swagger.examples.extensions.zalando.value.completion.swagger;

import com.intellij.codeInsight.completion.CompletionResultSet;
import org.zalando.intellij.swagger.completion.CompletionHelper;
import org.zalando.intellij.swagger.completion.value.ValueCompletion;

class AudienceValueCompletion extends ValueCompletion {

  AudienceValueCompletion(
      final CompletionHelper completionHelper, final CompletionResultSet completionResultSet) {
    super(completionHelper, completionResultSet);
  }

  @Override
  public void fill() {
    SwaggerValues.audience().forEach(this::addValue);
  }
}
