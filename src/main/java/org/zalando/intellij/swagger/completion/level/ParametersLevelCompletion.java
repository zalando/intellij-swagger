package org.zalando.intellij.swagger.completion.level;

import com.intellij.codeInsight.completion.CompletionResultSet;
import com.intellij.codeInsight.completion.InsertHandler;
import com.intellij.codeInsight.lookup.LookupElement;
import org.jetbrains.annotations.NotNull;
import org.zalando.intellij.swagger.completion.traversal.PositionResolver;

import static org.zalando.intellij.swagger.completion.level.LookupElementBuilderFactory.create;
import static org.zalando.intellij.swagger.completion.style.CompletionStyleFactory.optional;
import static org.zalando.intellij.swagger.completion.style.CompletionStyleFactory.required;

class ParametersLevelCompletion extends LevelCompletion {

    ParametersLevelCompletion(final PositionResolver positionResolver) {
        super(positionResolver);
    }

    public void fill(@NotNull final CompletionResultSet result,
                     @NotNull final InsertHandler<LookupElement> insertHandler) {
        result.addElement(create("name", required(positionResolver), insertHandler));
        result.addElement(create("in", required(positionResolver), insertHandler));
        result.addElement(create("description", optional(positionResolver), insertHandler));
        result.addElement(create("required", optional(positionResolver), insertHandler));
        result.addElement(create("schema", optional(positionResolver), insertHandler));
        result.addElement(create("type", optional(positionResolver), insertHandler));
        result.addElement(create("format", optional(positionResolver), insertHandler));
        result.addElement(create("allowEmptyValue", optional(positionResolver), insertHandler));
        result.addElement(create("items", optional(positionResolver), insertHandler));
        result.addElement(create("collectionFormat", optional(positionResolver), insertHandler));
        result.addElement(create("default", optional(positionResolver), insertHandler));
        result.addElement(create("maximum", optional(positionResolver), insertHandler));
        result.addElement(create("exclusiveMaximum", optional(positionResolver), insertHandler));
        result.addElement(create("minimum", optional(positionResolver), insertHandler));
        result.addElement(create("exclusiveMinimum", optional(positionResolver), insertHandler));
        result.addElement(create("maxLength", optional(positionResolver), insertHandler));
        result.addElement(create("minLength", optional(positionResolver), insertHandler));
        result.addElement(create("pattern", optional(positionResolver), insertHandler));
        result.addElement(create("maxItems", optional(positionResolver), insertHandler));
        result.addElement(create("minItems", optional(positionResolver), insertHandler));
        result.addElement(create("uniqueItems", optional(positionResolver), insertHandler));
        result.addElement(create("enum", optional(positionResolver), insertHandler));
        result.addElement(create("multipleOf", optional(positionResolver), insertHandler));
    }

}
