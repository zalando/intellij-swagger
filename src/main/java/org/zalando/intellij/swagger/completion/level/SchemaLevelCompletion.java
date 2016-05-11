package org.zalando.intellij.swagger.completion.level;

import com.intellij.codeInsight.completion.CompletionResultSet;
import org.jetbrains.annotations.NotNull;

import static org.zalando.intellij.swagger.completion.style.CompletionStyleFactory.optional;
import static org.zalando.intellij.swagger.completion.level.LookupElementBuilderFactory.create;

public class SchemaLevelCompletion implements LevelCompletion {

    public void fill(@NotNull final CompletionResultSet result, final boolean shouldQuote) {
        result.addElement(create("$ref", optional(shouldQuote)));
        result.addElement(create("format", optional(shouldQuote)));
        result.addElement(create("title", optional(shouldQuote)));
        result.addElement(create("description", optional(shouldQuote)));
        result.addElement(create("default", optional(shouldQuote)));
        result.addElement(create("multipleOf", optional(shouldQuote)));
        result.addElement(create("maximum", optional(shouldQuote)));
        result.addElement(create("exclusiveMaximum", optional(shouldQuote)));
        result.addElement(create("minimum", optional(shouldQuote)));
        result.addElement(create("exclusiveMinimum", optional(shouldQuote)));
        result.addElement(create("maxLength", optional(shouldQuote)));
        result.addElement(create("minLength", optional(shouldQuote)));
        result.addElement(create("pattern", optional(shouldQuote)));
        result.addElement(create("maxItems", optional(shouldQuote)));
        result.addElement(create("minItems", optional(shouldQuote)));
        result.addElement(create("uniqueItems", optional(shouldQuote)));
        result.addElement(create("maxProperties", optional(shouldQuote)));
        result.addElement(create("minProperties", optional(shouldQuote)));
        result.addElement(create("required", optional(shouldQuote)));
        result.addElement(create("enum", optional(shouldQuote)));
        result.addElement(create("type", optional(shouldQuote)));
        result.addElement(create("items", optional(shouldQuote)));
        result.addElement(create("allOf", optional(shouldQuote)));
        result.addElement(create("properties", optional(shouldQuote)));
        result.addElement(create("additionalProperties", optional(shouldQuote)));
        result.addElement(create("discriminator", optional(shouldQuote)));
        result.addElement(create("readOnly", optional(shouldQuote)));
        result.addElement(create("xml", optional(shouldQuote)));
        result.addElement(create("externalDocs", optional(shouldQuote)));
        result.addElement(create("example", optional(shouldQuote)));


    }

}
