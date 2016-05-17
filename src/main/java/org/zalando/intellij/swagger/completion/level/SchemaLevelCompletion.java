package org.zalando.intellij.swagger.completion.level;

import com.intellij.codeInsight.completion.CompletionResultSet;
import org.jetbrains.annotations.NotNull;
import org.zalando.intellij.swagger.completion.traversal.PositionResolver;

import static org.zalando.intellij.swagger.completion.level.LookupElementBuilderFactory.create;
import static org.zalando.intellij.swagger.completion.style.CompletionStyleFactory.optional;

public class SchemaLevelCompletion extends LevelCompletion {

    protected SchemaLevelCompletion(final PositionResolver positionResolver) {
        super(positionResolver);
    }

    @Override
    public void fill(@NotNull final CompletionResultSet result) {
        result.addElement(create("$ref", optional(positionResolver)));
        result.addElement(create("format", optional(positionResolver)));
        result.addElement(create("title", optional(positionResolver)));
        result.addElement(create("description", optional(positionResolver)));
        result.addElement(create("default", optional(positionResolver)));
        result.addElement(create("multipleOf", optional(positionResolver)));
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
        result.addElement(create("maxProperties", optional(positionResolver)));
        result.addElement(create("minProperties", optional(positionResolver)));
        result.addElement(create("required", optional(positionResolver)));
        result.addElement(create("enum", optional(positionResolver)));
        result.addElement(create("type", optional(positionResolver)));
        result.addElement(create("items", optional(positionResolver)));
        result.addElement(create("allOf", optional(positionResolver)));
        result.addElement(create("properties", optional(positionResolver)));
        result.addElement(create("additionalProperties", optional(positionResolver)));
        result.addElement(create("discriminator", optional(positionResolver)));
        result.addElement(create("readOnly", optional(positionResolver)));
        result.addElement(create("xml", optional(positionResolver)));
        result.addElement(create("externalDocs", optional(positionResolver)));
        result.addElement(create("example", optional(positionResolver)));        
    }
}
