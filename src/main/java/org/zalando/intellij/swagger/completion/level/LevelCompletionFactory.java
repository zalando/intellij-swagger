package org.zalando.intellij.swagger.completion.level;

import org.zalando.intellij.swagger.completion.traversal.PositionResolver;

import java.util.Optional;

public class LevelCompletionFactory {

    public static Optional<LevelCompletion> from(final PositionResolver positionResolver) {

        if (positionResolver.completeRootKey()) {
            return Optional.of(new RootLevelCompletion(positionResolver));
        } else if (positionResolver.completeInfoKey()) {
            return Optional.of(new InfoLevelCompletion(positionResolver));
        } else if (positionResolver.completeContactKey()) {
            return Optional.of(new ContactLevelCompletion(positionResolver));
        } else if (positionResolver.completeLicenseKey()) {
            return Optional.of(new LicenseLevelCompletion(positionResolver));
        } else if (positionResolver.completePathKey()) {
            return Optional.of(new PathLevelCompletion(positionResolver));
        } else if (positionResolver.completeOperationKey()) {
            return Optional.of(new OperationLevelCompletion(positionResolver));
        } else if (positionResolver.completeExternalDocsKey()) {
            return Optional.of(new ExternalDocsLevelCompletion(positionResolver));
        } else if (positionResolver.completeParametersKey()) {
            return Optional.of(new ParametersLevelCompletion(positionResolver));
        } else if (positionResolver.completeItemsKey()) {
            return Optional.of(new ItemsLevelCompletion(positionResolver));
        } else if (positionResolver.completeResponsesKey()) {
            return Optional.of(new ResponsesLevelCompletion(positionResolver));
        } else if (positionResolver.completeResponseKey()) {
            return Optional.of(new ResponseLevelCompletion(positionResolver));
        } else if (positionResolver.completeHeaderKey()) {
            return Optional.of(new HeaderLevelCompletion(positionResolver));
        } else if (positionResolver.completeTagKey()) {
            return Optional.of(new TagsLevelCompletion(positionResolver));
        } else if (positionResolver.completeSecurityDefinitionKey()) {
            return Optional.of(new SecurityDefinitionLevelCompletion(positionResolver));
        } else if (positionResolver.completeSchemaKey()) {
            return Optional.of(new SchemaLevelCompletion(positionResolver));
        } else if (positionResolver.completeXmlKey()) {
            return Optional.of(new XmlLevelCompletion(positionResolver));
        } else if (positionResolver.completeDefinitionsKey()) {
            return Optional.of(new DefinitionsLevelCompletion(positionResolver));
        } else if (positionResolver.completeParameterDefinitionKey()) {
            return Optional.of(new ParameterDefinitionLevelCompletion(positionResolver));
        } else {
            return Optional.empty();
        }
    }
}