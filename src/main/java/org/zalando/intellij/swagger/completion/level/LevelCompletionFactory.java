package org.zalando.intellij.swagger.completion.level;

import org.zalando.intellij.swagger.completion.traversal.PositionResolver;

import java.util.Optional;

public class LevelCompletionFactory {

    public static Optional<LevelCompletion> from(final PositionResolver positionResolver) {

        if (positionResolver.completeRootKey()) {
            return Optional.of(new RootLevelCompletion());
        } else if (positionResolver.completeInfoKey()) {
            return Optional.of(new InfoLevelCompletion());
        } else if (positionResolver.completeContactKey()) {
            return Optional.of(new ContactLevelCompletion());
        } else if (positionResolver.completeLicenseKey()) {
            return Optional.of(new LicenseLevelCompletion());
        } else if (positionResolver.completePathKey()) {
            return Optional.of(new PathLevelCompletion());
        } else if (positionResolver.completeOperationKey()) {
            return Optional.of(new OperationLevelCompletion());
        } else if (positionResolver.completeExternalDocsKey()) {
            return Optional.of(new ExternalDocsLevelCompletion());
        } else if (positionResolver.completeParametersKey()) {
            return Optional.of(new ParametersLevelCompletion());
        } else if (positionResolver.completeItemsKey()) {
            return Optional.of(new ItemsLevelCompletion());
        } else if (positionResolver.completeResponsesKey()) {
            return Optional.of(new ResponsesLevelCompletion());
        } else if (positionResolver.completeResponseKey()) {
            return Optional.of(new ResponseLevelCompletion());
        } else if (positionResolver.completeHeaderKey()) {
            return Optional.of(new HeaderLevelCompletion());
        } else if (positionResolver.completeTagKey()) {
            return Optional.of(new TagsLevelCompletion());
        } else if (positionResolver.completeSecurityDefinitionKey()) {
            return Optional.of(new SecurityDefinitionLevelCompletion());
        } else if (positionResolver.completeSchemaKey()) {
            return Optional.of(new SchemaLevelCompletion());
        } else if (positionResolver.completeXmlKey()) {
            return Optional.of(new XmlLevelCompletion());
        } else if (positionResolver.completeDefinitionsKey()) {
            return Optional.of(new DefinitionsLevelCompletion());
        } else if (positionResolver.completeParameterDefinitionKey()) {
            return Optional.of(new ParameterDefinitionLevelCompletion());
        } else {
            return Optional.empty();
        }
    }
}