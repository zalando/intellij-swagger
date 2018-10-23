package org.zalando.intellij.swagger.completion.value.completion.openapi;

import com.intellij.codeInsight.completion.CompletionResultSet;
import java.util.Optional;
import org.zalando.intellij.swagger.completion.OpenApiCompletionHelper;
import org.zalando.intellij.swagger.completion.value.ValueCompletion;
import org.zalando.intellij.swagger.completion.value.completion.common.BooleanValueCompletion;
import org.zalando.intellij.swagger.completion.value.completion.common.TypeValueCompletion;

public class OpenApiValueCompletionFactory {

  public static Optional<ValueCompletion> from(
      final OpenApiCompletionHelper completionHelper,
      final CompletionResultSet completionResultSet) {
    if (completionHelper.completeSchemaRefValue()) {
      return Optional.of(
          new ComponentRefValueCompletion(completionHelper, completionResultSet, "schemas"));
    } else if (completionHelper.completeResponseRefValue()) {
      return Optional.of(
          new ComponentRefValueCompletion(completionHelper, completionResultSet, "responses"));
    } else if (completionHelper.completeParameterRefValue()) {
      return Optional.of(
          new ComponentRefValueCompletion(completionHelper, completionResultSet, "parameters"));
    } else if (completionHelper.completeExampleRefValue()) {
      return Optional.of(
          new ComponentRefValueCompletion(completionHelper, completionResultSet, "examples"));
    } else if (completionHelper.completeRequestBodyRefValue()) {
      return Optional.of(
          new ComponentRefValueCompletion(completionHelper, completionResultSet, "requestBodies"));
    } else if (completionHelper.completeHeaderRefValue()) {
      return Optional.of(
          new ComponentRefValueCompletion(completionHelper, completionResultSet, "headers"));
    } else if (completionHelper.completeLinkRefValue()) {
      return Optional.of(
          new ComponentRefValueCompletion(completionHelper, completionResultSet, "links"));
    } else if (completionHelper.completeCallbackRefValue()) {
      return Optional.of(
          new ComponentRefValueCompletion(completionHelper, completionResultSet, "callbacks"));
    } else if (completionHelper.completeBooleanValue()) {
      return Optional.of(new BooleanValueCompletion(completionHelper, completionResultSet));
    } else if (completionHelper.completeTypeValue()) {
      return Optional.of(new TypeValueCompletion(completionHelper, completionResultSet));
    } else if (completionHelper.completeInValue()) {
      return Optional.of(new InValueCompletion(completionHelper, completionResultSet));
    } else if (completionHelper.completeFormatValue()) {
      return Optional.of(new FormatValueCompletion(completionHelper, completionResultSet));
    } else if (completionHelper.completeStyleValue()) {
      return Optional.of(new StyleValueCompletion(completionHelper, completionResultSet));
    } else {
      return Optional.empty();
    }
  }
}
