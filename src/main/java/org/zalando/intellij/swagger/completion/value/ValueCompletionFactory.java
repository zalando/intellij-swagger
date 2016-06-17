package org.zalando.intellij.swagger.completion.value;

import com.intellij.codeInsight.completion.CompletionResultSet;
import org.zalando.intellij.swagger.traversal.CompletionHelper;

import java.util.Optional;

public class ValueCompletionFactory {

    public static Optional<ValueCompletion> from(final CompletionHelper completionHelper,
                                                 final CompletionResultSet completionResultSet) {
        if (completionHelper.completeMimeValue()) {
            return Optional.of(new MimeValueCompletion(completionHelper, completionResultSet));
        } else if (completionHelper.completeSchemesValue()) {
            return Optional.of(new SchemesValueCompletion(completionHelper, completionResultSet));
        } else if (completionHelper.completeDefinitionRefValue()) {
            return Optional.of(new DefinitionRefValueCompletion(completionHelper, completionResultSet));
        } else if (completionHelper.completeParameterRefValue()) {
            return Optional.of(new ParameterRefValueCompletion(completionHelper, completionResultSet));
        } else if (completionHelper.completeBooleanValue()) {
            return Optional.of(new BooleanValueCompletion(completionHelper, completionResultSet));
        } else if (completionHelper.completeTypeValue()) {
            return Optional.of(new TypeValueCompletion(completionHelper, completionResultSet));
        } else if (completionHelper.completeInValue()) {
            return Optional.of(new InValueCompletion(completionHelper, completionResultSet));
        } else if (completionHelper.completeResponseRefValue()) {
            return Optional.of(new ResponseRefValueCompletion(completionHelper, completionResultSet));
        } else if (completionHelper.completeSecurityScopeNameValue()) {
            return Optional.of(new SecurityScopeNameValueCompletion(completionHelper, completionResultSet));
        } else {
            return Optional.empty();
        }
    }
}