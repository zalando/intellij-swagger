package org.zalando.intellij.swagger.completion.field;

import com.intellij.codeInsight.completion.CompletionResultSet;
import org.zalando.intellij.swagger.traversal.PositionResolver;

import java.util.Optional;

public class FieldCompletionFactory {

    public static Optional<FieldCompletion> from(final PositionResolver positionResolver,
                                                 final CompletionResultSet completionResultSet) {
        if (positionResolver.completeRootKey()) {
            return Optional.of(new RootCompletion(positionResolver, completionResultSet));
        } else if (positionResolver.completeInfoKey()) {
            return Optional.of(new InfoCompletion(positionResolver, completionResultSet));
        } else if (positionResolver.completeContactKey()) {
            return Optional.of(new ContactCompletion(positionResolver, completionResultSet));
        } else if (positionResolver.completeLicenseKey()) {
            return Optional.of(new LicenseCompletion(positionResolver, completionResultSet));
        } else if (positionResolver.completePathKey()) {
            return Optional.of(new PathCompletion(positionResolver, completionResultSet));
        } else if (positionResolver.completeOperationKey()) {
            return Optional.of(new OperationCompletion(positionResolver, completionResultSet));
        } else if (positionResolver.completeExternalDocsKey()) {
            return Optional.of(new ExternalDocsCompletion(positionResolver, completionResultSet));
        } else if (positionResolver.completeParametersKey()) {
            return Optional.of(new ParametersCompletion(positionResolver, completionResultSet));
        } else if (positionResolver.completeItemsKey()) {
            return Optional.of(new ItemsCompletion(positionResolver, completionResultSet));
        } else if (positionResolver.completeResponsesKey()) {
            return Optional.of(new ResponsesCompletion(positionResolver, completionResultSet));
        } else if (positionResolver.completeResponseKey()) {
            return Optional.of(new ResponseCompletion(positionResolver, completionResultSet));
        } else if (positionResolver.completeHeaderKey()) {
            return Optional.of(new HeaderCompletion(positionResolver, completionResultSet));
        } else if (positionResolver.completeTagKey()) {
            return Optional.of(new TagsCompletion(positionResolver, completionResultSet));
        } else if (positionResolver.completeSecurityDefinitionKey()) {
            return Optional.of(new SecurityDefinitionCompletion(positionResolver, completionResultSet));
        } else if (positionResolver.completeSchemaKey()) {
            return Optional.of(new SchemaCompletion(positionResolver, completionResultSet));
        } else if (positionResolver.completeXmlKey()) {
            return Optional.of(new XmlCompletion(positionResolver, completionResultSet));
        } else if (positionResolver.completeDefinitionsKey()) {
            return Optional.of(new DefinitionsCompletion(positionResolver, completionResultSet));
        } else if (positionResolver.completeParameterDefinitionKey()) {
            return Optional.of(new ParameterDefinitionCompletion(positionResolver, completionResultSet));
        } else if (positionResolver.completeHeadersKey()) {
            return Optional.of(new HeadersCompletion(positionResolver, completionResultSet));
        } else if (positionResolver.completeResponseDefinition()) {
            return Optional.of(new ResponseDefinitionCompletion(positionResolver, completionResultSet));
        } else {
            return Optional.empty();
        }
    }
}