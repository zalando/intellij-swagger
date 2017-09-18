package org.zalando.intellij.swagger.completion.field.completion.openapi;

import com.intellij.codeInsight.completion.CompletionResultSet;
import org.zalando.intellij.swagger.completion.OpenApiCompletionHelper;
import org.zalando.intellij.swagger.completion.field.FieldCompletion;
import org.zalando.intellij.swagger.completion.field.completion.common.InfoCompletion;

import java.util.Optional;

public class OpenApiFieldCompletionFactory {

    public static Optional<FieldCompletion> from(final OpenApiCompletionHelper completionHelper,
                                                 final CompletionResultSet completionResultSet) {
        if (completionHelper.completeRootKey()) {
            return Optional.of(new RootCompletion(completionHelper, completionResultSet));
        } else if (completionHelper.completeInfoKey()) {
            return Optional.of(new InfoCompletion(completionHelper, completionResultSet));
        } else if (completionHelper.completeContactKey()) {
            return Optional.of(new ContactCompletion(completionHelper, completionResultSet));
        } else if (completionHelper.completeLicenseKey()) {
            return Optional.of(new LicenseCompletion(completionHelper, completionResultSet));
        } else if (completionHelper.completeServerKey()) {
            return Optional.of(new ServerCompletion(completionHelper, completionResultSet));
        } else if (completionHelper.completeServerVariableKey()) {
            return Optional.of(new ServerVariableCompletion(completionHelper, completionResultSet));
        } else if (completionHelper.completePathKey()) {
            return Optional.of(new PathCompletion(completionHelper, completionResultSet));
        } else if (completionHelper.completeOperationKey()) {
            return Optional.of(new OperationCompletion(completionHelper, completionResultSet));
        } else if (completionHelper.completeParametersKey()) {
            return Optional.of(new ParameterCompletion(completionHelper, completionResultSet));
        } else if (completionHelper.completeRequestBodyKey()) {
            return Optional.of(new RequestBodyCompletion(completionHelper, completionResultSet));
        } else if (completionHelper.completeMediaTypeKey()) {
            return Optional.of(new MediaTypeCompletion(completionHelper, completionResultSet));
        } else if (completionHelper.completeComponentKey()) {
            return Optional.of(new ComponentCompletion(completionHelper, completionResultSet));
        } else if (completionHelper.completeTagKey()) {
            return Optional.of(new TagCompletion(completionHelper, completionResultSet));
        } else if (completionHelper.completeSchemaKey()) {
            return Optional.of(new SchemaCompletion(completionHelper, completionResultSet));
        } else if (completionHelper.completeExampleKey()) {
            return Optional.of(new ExampleCompletion(completionHelper, completionResultSet));
        } else if (completionHelper.completeEncodingKey()) {
            return Optional.of(new EncodingCompletion(completionHelper, completionResultSet));
        } else if (completionHelper.completeResponseKey()) {
            return Optional.of(new ResponseCompletion(completionHelper, completionResultSet));
        } else if (completionHelper.completeResponsesKey()) {
            return Optional.of(new ResponsesCompletion(completionHelper, completionResultSet));
        } else if (completionHelper.completeHeaderKey()) {
            return Optional.of(new HeaderCompletion(completionHelper, completionResultSet));
        } else if (completionHelper.completeLinkKey()) {
            return Optional.of(new LinkCompletion(completionHelper, completionResultSet));
        } else if (completionHelper.completeCallbackKey()) {
            return Optional.of(new CallbackCompletion(completionHelper, completionResultSet));
        } else if (completionHelper.completeSecuritySchemeKey()) {
            return Optional.of(new SecuritySchemeCompletion(completionHelper, completionResultSet));
        } else if (completionHelper.completeExternalDocsKey()) {
            return Optional.of(new ExternalDocsCompletion(completionHelper, completionResultSet));
        } else if (completionHelper.completeContentKey()) {
            return Optional.of(new ContentCompletion(completionHelper, completionResultSet));
        } else {
            return Optional.empty();
        }
    }
}
