package org.zalando.intellij.swagger.completion.level;

import com.intellij.codeInsight.completion.CompletionResultSet;
import org.jetbrains.annotations.NotNull;
import org.zalando.intellij.swagger.completion.style.CompletionStyleFactory;

public class OperationLevelCompletion implements LevelCompletion {

    public void fill(@NotNull final CompletionResultSet result, final boolean shouldQuote) {
        result.addElement(LookupElementBuilderFactory.create("tags", CompletionStyleFactory.optional(shouldQuote)));
        result.addElement(LookupElementBuilderFactory.create("summary", CompletionStyleFactory.optional(shouldQuote)));
        result.addElement(LookupElementBuilderFactory.create("description", CompletionStyleFactory.optional(shouldQuote)));
        result.addElement(LookupElementBuilderFactory.create("externalDocs", CompletionStyleFactory.optional(shouldQuote)));
        result.addElement(LookupElementBuilderFactory.create("operationId", CompletionStyleFactory.optional(shouldQuote)));
        result.addElement(LookupElementBuilderFactory.create("consumes", CompletionStyleFactory.optional(shouldQuote)));
        result.addElement(LookupElementBuilderFactory.create("produces", CompletionStyleFactory.optional(shouldQuote)));
        result.addElement(LookupElementBuilderFactory.create("parameters", CompletionStyleFactory.optional(shouldQuote)));
        result.addElement(LookupElementBuilderFactory.create("responses", CompletionStyleFactory.required(shouldQuote)));
        result.addElement(LookupElementBuilderFactory.create("schemes", CompletionStyleFactory.optional(shouldQuote)));
        result.addElement(LookupElementBuilderFactory.create("deprecated", CompletionStyleFactory.optional(shouldQuote)));
        result.addElement(LookupElementBuilderFactory.create("security", CompletionStyleFactory.optional(shouldQuote)));
    }

}
