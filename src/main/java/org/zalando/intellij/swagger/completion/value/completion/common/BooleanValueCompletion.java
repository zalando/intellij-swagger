package org.zalando.intellij.swagger.completion.value.completion.common;

import com.intellij.codeInsight.completion.CompletionResultSet;
import org.zalando.intellij.swagger.completion.CompletionHelper;
import org.zalando.intellij.swagger.completion.value.ValueCompletion;
import org.zalando.intellij.swagger.completion.value.model.common.CommonValues;

public class BooleanValueCompletion extends ValueCompletion {

  public BooleanValueCompletion(
      final CompletionHelper completionHelper, final CompletionResultSet completionResultSet) {
    super(completionHelper, completionResultSet);
  }

  @Override
  public void fill() {
    CommonValues.booleans().forEach(this::addValue);
  }
}
