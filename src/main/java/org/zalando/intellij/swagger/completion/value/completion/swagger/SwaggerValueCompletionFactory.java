package org.zalando.intellij.swagger.completion.value.completion.swagger;

import com.intellij.codeInsight.completion.CompletionResultSet;
import java.util.Optional;
import org.zalando.intellij.swagger.completion.SwaggerCompletionHelper;
import org.zalando.intellij.swagger.completion.value.ValueCompletion;
import org.zalando.intellij.swagger.completion.value.completion.common.BooleanValueCompletion;
import org.zalando.intellij.swagger.completion.value.completion.common.RefValueCompletion;
import org.zalando.intellij.swagger.file.SwaggerFileType;

public class SwaggerValueCompletionFactory {

  public static Optional<ValueCompletion> from(
      final SwaggerCompletionHelper completionHelper,
      final CompletionResultSet completionResultSet) {
    if (completionHelper.completeMimeValue()) {
      return Optional.of(new MimeValueCompletion(completionHelper, completionResultSet));
    } else if (completionHelper.completeSchemesValue()) {
      return Optional.of(new SchemesValueCompletion(completionHelper, completionResultSet));
    } else if (completionHelper.completeDefinitionRefValue()) {
      return Optional.of(
          new RefValueCompletion(
              completionHelper, completionResultSet, SwaggerFileType.DEFINITIONS));
    } else if (completionHelper.completeParameterRefValue()) {
      return Optional.of(
          new RefValueCompletion(
              completionHelper, completionResultSet, SwaggerFileType.PARAMETERS));
    } else if (completionHelper.completeBooleanValue()) {
      return Optional.of(new BooleanValueCompletion(completionHelper, completionResultSet));
    } else if (completionHelper.completeTypeValue()) {
      return Optional.of(new TypeValueCompletion(completionHelper, completionResultSet));
    } else if (completionHelper.completeInValue()) {
      return Optional.of(new InValueCompletion(completionHelper, completionResultSet));
    } else if (completionHelper.completeResponseRefValue()) {
      return Optional.of(
          new RefValueCompletion(completionHelper, completionResultSet, SwaggerFileType.RESPONSES));
    } else if (completionHelper.completeSecurityScopeNameValue()) {
      return Optional.of(
          new SecurityScopeNameValueCompletion(completionHelper, completionResultSet));
    } else if (completionHelper.completeFormatValue()) {
      return Optional.of(new FormatValueCompletion(completionHelper, completionResultSet));
    } else if (completionHelper.completeItemsCollectionFormat()) {
      return Optional.of(
          new ItemsCollectionFormatValueCompletion(completionHelper, completionResultSet));
    } else if (completionHelper.completeParametersCollectionFormat()) {
      return Optional.of(
          new ParametersCollectionFormatValueCompletion(completionHelper, completionResultSet));
    } else if (completionHelper.completeHeadersCollectionFormat()) {
      return Optional.of(
          new HeadersCollectionFormatValueCompletion(completionHelper, completionResultSet));
    } else if (completionHelper.completeTagsValue()) {
      return Optional.of(new TagsValueCompletion(completionHelper, completionResultSet));
    } else {
      return Optional.empty();
    }
  }
}
