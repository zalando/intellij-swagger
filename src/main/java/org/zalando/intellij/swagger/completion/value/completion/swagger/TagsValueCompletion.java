package org.zalando.intellij.swagger.completion.value.completion.swagger;

import com.intellij.codeInsight.completion.CompletionResultSet;
import java.util.List;
import java.util.stream.Collectors;
import org.zalando.intellij.swagger.completion.CompletionHelper;
import org.zalando.intellij.swagger.completion.value.ValueCompletion;
import org.zalando.intellij.swagger.completion.value.model.common.StringValue;
import org.zalando.intellij.swagger.completion.value.model.common.Value;

class TagsValueCompletion extends ValueCompletion {

  TagsValueCompletion(
      final CompletionHelper completionHelper, final CompletionResultSet completionResultSet) {
    super(completionHelper, completionResultSet);
  }

  @Override
  public void fill() {
    getTags().forEach(this::addValue);
  }

  private List<Value> getTags() {
    return completionHelper
        .getTagNames()
        .stream()
        .map(StringValue::new)
        .collect(Collectors.toList());
  }
}
