package org.zalando.intellij.swagger.completion.field.completion.swagger;

import com.intellij.codeInsight.completion.CompletionResultSet;
import org.zalando.intellij.swagger.completion.CompletionHelper;
import org.zalando.intellij.swagger.completion.field.FieldCompletion;
import org.zalando.intellij.swagger.completion.field.model.swagger.SwaggerFields;

class ResponseDefinitionCompletion extends FieldCompletion {

  ResponseDefinitionCompletion(
      final CompletionHelper completionHelper, final CompletionResultSet completionResultSet) {
    super(completionHelper, completionResultSet);
  }

  public void fill() {
    SwaggerFields.responseDefinition().forEach(this::addUnique);
  }
}
