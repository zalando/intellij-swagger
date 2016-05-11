package org.zalando.intellij.swagger.completion.level;

import com.intellij.codeInsight.completion.CompletionResultSet;
import org.jetbrains.annotations.NotNull;

import static org.zalando.intellij.swagger.completion.style.CompletionStyleFactory.optional;
import static org.zalando.intellij.swagger.completion.style.CompletionStyleFactory.required;
import static org.zalando.intellij.swagger.completion.level.LookupElementBuilderFactory.create;

public class RootLevelCompletion implements LevelCompletion {

    public void fill(@NotNull final CompletionResultSet result, final boolean shouldQuote) {
        result.addElement(create("swagger", required(shouldQuote)));
        result.addElement(create("info", required(shouldQuote)));
        result.addElement(create("host", optional(shouldQuote)));
        result.addElement(create("basePath", optional(shouldQuote)));
        result.addElement(create("schemes", optional(shouldQuote)));
        result.addElement(create("consumes", optional(shouldQuote)));
        result.addElement(create("produces", optional(shouldQuote)));
        result.addElement(create("paths", required(shouldQuote)));
        result.addElement(create("definitions", optional(shouldQuote)));
        result.addElement(create("parameters", optional(shouldQuote)));
        result.addElement(create("responses", optional(shouldQuote)));
        result.addElement(create("securityDefinitions", optional(shouldQuote)));
        result.addElement(create("security", optional(shouldQuote)));
        result.addElement(create("tags", optional(shouldQuote)));
        result.addElement(create("externalDocs", optional(shouldQuote)));
    }

}
