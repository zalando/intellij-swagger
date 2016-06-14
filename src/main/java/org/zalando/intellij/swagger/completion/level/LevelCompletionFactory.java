package org.zalando.intellij.swagger.completion.level;

import com.intellij.codeInsight.completion.CompletionResultSet;
import org.zalando.intellij.swagger.completion.traversal.PositionResolver;

import java.util.Optional;

public class LevelCompletionFactory {

    public static Optional<LevelCompletion> from(final PositionResolver positionResolver, final CompletionResultSet completionResultSet) {

        if (positionResolver.completeRootKey()) {
            return Optional.of(new RootLevelCompletion(positionResolver, completionResultSet));
        } else if (positionResolver.completeInfoKey()) {
            return Optional.of(new InfoLevelCompletion(positionResolver, completionResultSet));
        } else if (positionResolver.completeContactKey()) {
            return Optional.of(new ContactLevelCompletion(positionResolver, completionResultSet));
        } else if (positionResolver.completeLicenseKey()) {
            return Optional.of(new LicenseLevelCompletion(positionResolver, completionResultSet));
        } else if (positionResolver.completePathKey()) {
            return Optional.of(new PathLevelCompletion(positionResolver, completionResultSet));
        } else if (positionResolver.completeOperationKey()) {
            return Optional.of(new OperationLevelCompletion(positionResolver, completionResultSet));
        } else if (positionResolver.completeExternalDocsKey()) {
            return Optional.of(new ExternalDocsLevelCompletion(positionResolver, completionResultSet));
        } else if (positionResolver.completeParametersKey()) {
            return Optional.of(new ParametersLevelCompletion(positionResolver, completionResultSet));
        } else if (positionResolver.completeItemsKey()) {
            return Optional.of(new ItemsLevelCompletion(positionResolver, completionResultSet));
        } else if (positionResolver.completeResponsesKey()) {
            return Optional.of(new ResponsesLevelCompletion(positionResolver, completionResultSet));
        } else if (positionResolver.completeResponseKey()) {
            return Optional.of(new ResponseLevelCompletion(positionResolver, completionResultSet));
        } else if (positionResolver.completeHeaderKey()) {
            return Optional.of(new HeaderLevelCompletion(positionResolver, completionResultSet));
        } else if (positionResolver.completeTagKey()) {
            return Optional.of(new TagsLevelCompletion(positionResolver, completionResultSet));
        } else if (positionResolver.completeSecurityDefinitionKey()) {
            return Optional.of(new SecurityDefinitionLevelCompletion(positionResolver, completionResultSet));
        } else if (positionResolver.completeSchemaKey()) {
            return Optional.of(new SchemaLevelCompletion(positionResolver, completionResultSet));
        } else if (positionResolver.completeXmlKey()) {
            return Optional.of(new XmlLevelCompletion(positionResolver, completionResultSet));
        } else if (positionResolver.completeDefinitionsKey()) {
            return Optional.of(new DefinitionsLevelCompletion(positionResolver, completionResultSet));
        } else if (positionResolver.completeParameterDefinitionKey()) {
            return Optional.of(new ParameterDefinitionLevelCompletion(positionResolver, completionResultSet));
        } else if (positionResolver.completeHeadersKey()) {
            return Optional.of(new HeadersLevelCompletion(positionResolver, completionResultSet));
        } else {
            return Optional.empty();
        }
    }
}