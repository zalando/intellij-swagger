package org.zalando.intellij.swagger.completion.level.value;

import org.zalando.intellij.swagger.completion.traversal.PositionResolver;

import java.util.Optional;

public class ValueCompletionFactory {

    public static Optional<ValueCompletion> from(final PositionResolver positionResolver) {

        if (positionResolver.completeMimeValue()) {
            return Optional.of(new MimeValueCompletion(positionResolver));
        } else if (positionResolver.completeSchemesValue()) {
            return Optional.of(new SchemesValueCompletion(positionResolver));
        } else if (positionResolver.completeDefinitionRefValue()) {
            return Optional.of(new DefinitionRefValueCompletion(positionResolver));
        } else if (positionResolver.completeParameterRefValue()) {
            return Optional.of(new ParameterRefValueCompletion(positionResolver));
        } else if (positionResolver.completeBooleanValue()) {
            return Optional.of(new BooleanValueCompletion(positionResolver));
        } else {
            return Optional.empty();
        }
    }
}