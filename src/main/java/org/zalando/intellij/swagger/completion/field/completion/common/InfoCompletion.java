package org.zalando.intellij.swagger.completion.field.completion.common;

import com.intellij.codeInsight.completion.CompletionResultSet;
import org.zalando.intellij.swagger.completion.CompletionHelper;
import org.zalando.intellij.swagger.completion.field.FieldCompletion;
import org.zalando.intellij.swagger.completion.field.model.common.CommonFields;

public class InfoCompletion extends FieldCompletion {

  public InfoCompletion(
      final CompletionHelper completionHelper, final CompletionResultSet completionResultSet) {
    super(completionHelper, completionResultSet);
  }

  public void fill() {
    CommonFields.info().forEach(this::addUnique);
  }
}
