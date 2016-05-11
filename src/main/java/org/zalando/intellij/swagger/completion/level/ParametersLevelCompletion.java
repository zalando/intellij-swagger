package org.zalando.intellij.swagger.completion.level;

import com.intellij.codeInsight.completion.CompletionResultSet;
import org.jetbrains.annotations.NotNull;
import org.zalando.intellij.swagger.completion.traversal.PositionResolver;

import static org.zalando.intellij.swagger.completion.style.CompletionStyleFactory.optional;
import static org.zalando.intellij.swagger.completion.style.CompletionStyleFactory.required;
import static org.zalando.intellij.swagger.completion.level.LookupElementBuilderFactory.create;

class ParametersLevelCompletion extends LevelCompletion {

    ParametersLevelCompletion(final PositionResolver positionResolver) {
        super(positionResolver);
    }

    public void fill(@NotNull final CompletionResultSet result) {
        result.addElement(create("name", required(positionResolver)));
        result.addElement(create("in", required(positionResolver)));
        result.addElement(create("description", optional(positionResolver)));
        result.addElement(create("required", optional(positionResolver)));
        result.addElement(create("schema", optional(positionResolver)));
        result.addElement(create("type", optional(positionResolver)));
        result.addElement(create("format", optional(positionResolver)));
        result.addElement(create("allowEmptyValue", optional(positionResolver)));
        result.addElement(create("items", optional(positionResolver)));
        result.addElement(create("collectionFormat", optional(positionResolver)));
        result.addElement(create("default", optional(positionResolver)));
        result.addElement(create("maximum", optional(positionResolver)));
        result.addElement(create("exclusiveMaximum", optional(positionResolver)));
        result.addElement(create("minimum", optional(positionResolver)));
        result.addElement(create("exclusiveMinimum", optional(positionResolver)));
        result.addElement(create("maxLength", optional(positionResolver)));
        result.addElement(create("minLength", optional(positionResolver)));
        result.addElement(create("pattern", optional(positionResolver)));
        result.addElement(create("maxItems", optional(positionResolver)));
        result.addElement(create("minItems", optional(positionResolver)));
        result.addElement(create("uniqueItems", optional(positionResolver)));
        result.addElement(create("enum", optional(positionResolver)));
        result.addElement(create("multipleOf", optional(positionResolver)));
    }

}
