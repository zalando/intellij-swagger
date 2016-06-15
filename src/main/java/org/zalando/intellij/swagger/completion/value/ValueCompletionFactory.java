package org.zalando.intellij.swagger.completion.value;

import com.intellij.codeInsight.completion.CompletionResultSet;
import org.zalando.intellij.swagger.traversal.PositionResolver;

import java.util.Optional;

public class ValueCompletionFactory {

    public static Optional<ValueCompletion> from(final PositionResolver positionResolver,
                                                 final CompletionResultSet completionResultSet) {
        if (positionResolver.completeMimeValue()) {
            return Optional.of(new MimeValueCompletion(positionResolver, completionResultSet));
        } else if (positionResolver.completeSchemesValue()) {
            return Optional.of(new SchemesValueCompletion(positionResolver, completionResultSet));
        } else if (positionResolver.completeDefinitionRefValue()) {
            return Optional.of(new DefinitionRefValueCompletion(positionResolver, completionResultSet));
        } else if (positionResolver.completeParameterRefValue()) {
            return Optional.of(new ParameterRefValueCompletion(positionResolver, completionResultSet));
        } else if (positionResolver.completeBooleanValue()) {
            return Optional.of(new BooleanValueCompletion(positionResolver, completionResultSet));
        } else if (positionResolver.completeTypeValue()) {
            return Optional.of(new TypeValueCompletion(positionResolver, completionResultSet));
        } else if (positionResolver.completeInValue()) {
            return Optional.of(new InValueCompletion(positionResolver, completionResultSet));
        } else if (positionResolver.completeResponseRefValue()) {
            return Optional.of(new ResponseRefValueCompletion(positionResolver, completionResultSet));
        } else {
            return Optional.empty();
        }
    }
}