package org.zalando.intellij.swagger.completion.field.completion.swagger;

import com.intellij.codeInsight.completion.CompletionResultSet;
import org.zalando.intellij.swagger.completion.CompletionHelper;
import org.zalando.intellij.swagger.completion.field.FieldCompletion;
import org.zalando.intellij.swagger.completion.field.model.swagger.SwaggerFields;

class RootCompletion extends FieldCompletion {

  RootCompletion(
      final CompletionHelper completionHelper, final CompletionResultSet completionResultSet) {
    super(completionHelper, completionResultSet);
  }

  public void fill() {
    SwaggerFields.root().forEach(this::addUnique);
  }
}
