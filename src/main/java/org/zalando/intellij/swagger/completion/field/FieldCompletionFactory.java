package org.zalando.intellij.swagger.completion.field;

import com.intellij.codeInsight.completion.CompletionResultSet;
import org.zalando.intellij.swagger.completion.CompletionHelper;

import java.util.Optional;

public class FieldCompletionFactory {

    public static Optional<FieldCompletion> from(final CompletionHelper completionHelper,
                                                 final CompletionResultSet completionResultSet) {
        if (completionHelper.completeRootKey()) {
            return Optional.of(new RootCompletion(completionHelper, completionResultSet));
        } else if (completionHelper.completeInfoKey()) {
            return Optional.of(new InfoCompletion(completionHelper, completionResultSet));
        } else if (completionHelper.completeContactKey()) {
            return Optional.of(new ContactCompletion(completionHelper, completionResultSet));
        } else if (completionHelper.completeLicenseKey()) {
            return Optional.of(new LicenseCompletion(completionHelper, completionResultSet));
        } else if (completionHelper.completePathKey()) {
            return Optional.of(new PathCompletion(completionHelper, completionResultSet));
        } else if (completionHelper.completeOperationKey()) {
            return Optional.of(new OperationCompletion(completionHelper, completionResultSet));
        } else if (completionHelper.completeExternalDocsKey()) {
            return Optional.of(new ExternalDocsCompletion(completionHelper, completionResultSet));
        } else if (completionHelper.completeParametersKey()) {
            return Optional.of(new ParametersCompletion(completionHelper, completionResultSet));
        } else if (completionHelper.completeItemsKey()) {
            return Optional.of(new ItemsCompletion(completionHelper, completionResultSet));
        } else if (completionHelper.completeResponsesKey()) {
            return Optional.of(new ResponsesCompletion(completionHelper, completionResultSet));
        } else if (completionHelper.completeResponseKey()) {
            return Optional.of(new ResponseCompletion(completionHelper, completionResultSet));
        } else if (completionHelper.completeHeaderKey()) {
            return Optional.of(new HeaderCompletion(completionHelper, completionResultSet));
        } else if (completionHelper.completeTagKey()) {
            return Optional.of(new TagsCompletion(completionHelper, completionResultSet));
        } else if (completionHelper.completeSecurityDefinitionKey()) {
            return Optional.of(new SecurityDefinitionCompletion(completionHelper, completionResultSet));
        } else if (completionHelper.completeSchemaKey()) {
            return Optional.of(new SchemaCompletion(completionHelper, completionResultSet));
        } else if (completionHelper.completeXmlKey()) {
            return Optional.of(new XmlCompletion(completionHelper, completionResultSet));
        } else if (completionHelper.completeDefinitionsKey()) {
            return Optional.of(new DefinitionsCompletion(completionHelper, completionResultSet));
        } else if (completionHelper.completeParameterDefinitionKey()) {
            return Optional.of(new ParameterDefinitionCompletion(completionHelper, completionResultSet));
        } else if (completionHelper.completeHeadersKey()) {
            return Optional.of(new HeadersCompletion(completionHelper, completionResultSet));
        } else if (completionHelper.completeResponseDefinition()) {
            return Optional.of(new ResponseDefinitionCompletion(completionHelper, completionResultSet));
        } else if (completionHelper.completeRootSecurityKey()) {
            return Optional.of(new RootSecurityCompletion(completionHelper, completionResultSet));
        } else if (completionHelper.completeOperationSecurityKey()) {
            return Optional.of(new OperationSecurityCompletion(completionHelper, completionResultSet));
        } else {
            return Optional.empty();
        }
    }
}