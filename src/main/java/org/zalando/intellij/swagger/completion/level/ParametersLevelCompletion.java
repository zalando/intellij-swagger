package org.zalando.intellij.swagger.completion.level;

import com.intellij.codeInsight.completion.CompletionResultSet;
import com.intellij.codeInsight.completion.InsertHandler;
import com.intellij.codeInsight.lookup.LookupElement;
import org.jetbrains.annotations.NotNull;
import org.zalando.intellij.swagger.completion.traversal.PositionResolver;

import static org.zalando.intellij.swagger.completion.style.CompletionStyleFactory.optional;
import static org.zalando.intellij.swagger.completion.style.CompletionStyleFactory.required;

class ParametersLevelCompletion extends LevelCompletion {

    ParametersLevelCompletion(final PositionResolver positionResolver, final CompletionResultSet completionResultSet) {
        super(positionResolver, completionResultSet);
    }

    public void fill(@NotNull final InsertHandler<LookupElement> insertHandler) {
        addUnique("name", required(positionResolver), insertHandler);
        addUnique("in", required(positionResolver), insertHandler);
        addUnique("description", optional(positionResolver), insertHandler);
        addUnique("required", optional(positionResolver), insertHandler);
        addUnique("schema", optional(positionResolver), insertHandler);
        addUnique("type", optional(positionResolver), insertHandler);
        addUnique("format", optional(positionResolver), insertHandler);
        addUnique("allowEmptyValue", optional(positionResolver), insertHandler);
        addUnique("items", optional(positionResolver), insertHandler);
        addUnique("collectionFormat", optional(positionResolver), insertHandler);
        addUnique("default", optional(positionResolver), insertHandler);
        addUnique("maximum", optional(positionResolver), insertHandler);
        addUnique("exclusiveMaximum", optional(positionResolver), insertHandler);
        addUnique("minimum", optional(positionResolver), insertHandler);
        addUnique("exclusiveMinimum", optional(positionResolver), insertHandler);
        addUnique("maxLength", optional(positionResolver), insertHandler);
        addUnique("minLength", optional(positionResolver), insertHandler);
        addUnique("pattern", optional(positionResolver), insertHandler);
        addUnique("maxItems", optional(positionResolver), insertHandler);
        addUnique("minItems", optional(positionResolver), insertHandler);
        addUnique("uniqueItems", optional(positionResolver), insertHandler);
        addUnique("enum", optional(positionResolver), insertHandler);
        addUnique("multipleOf", optional(positionResolver), insertHandler);
    }

}
