package org.zalando.intellij.swagger.completion.value.completion.common;

import com.intellij.codeInsight.completion.CompletionResultSet;
import org.zalando.intellij.swagger.completion.CompletionHelper;
import org.zalando.intellij.swagger.completion.value.ValueCompletion;
import org.zalando.intellij.swagger.completion.value.model.common.CommonValues;

public class TypeValueCompletion extends ValueCompletion {

  public TypeValueCompletion(
      final CompletionHelper completionHelper, final CompletionResultSet completionResultSet) {
    super(completionHelper, completionResultSet);
  }

  @Override
  public void fill() {
    CommonValues.types().forEach(this::addValue);
  }
}
