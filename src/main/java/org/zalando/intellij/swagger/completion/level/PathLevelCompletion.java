package org.zalando.intellij.swagger.completion.level;

import com.intellij.codeInsight.completion.CompletionResultSet;
import org.jetbrains.annotations.NotNull;
import org.zalando.intellij.swagger.completion.style.CompletionStyleFactory;

public class PathLevelCompletion implements LevelCompletion {

    public void fill(@NotNull final CompletionResultSet result, final boolean shouldQuote) {
        result.addElement(LookupElementBuilderFactory.create("$ref", CompletionStyleFactory.optional(shouldQuote)));
        result.addElement(LookupElementBuilderFactory.create("get", CompletionStyleFactory.optional(shouldQuote)));
        result.addElement(LookupElementBuilderFactory.create("put", CompletionStyleFactory.optional(shouldQuote)));
        result.addElement(LookupElementBuilderFactory.create("post", CompletionStyleFactory.optional(shouldQuote)));
        result.addElement(LookupElementBuilderFactory.create("delete", CompletionStyleFactory.optional(shouldQuote)));
        result.addElement(LookupElementBuilderFactory.create("options", CompletionStyleFactory.optional(shouldQuote)));
        result.addElement(LookupElementBuilderFactory.create("head", CompletionStyleFactory.optional(shouldQuote)));
        result.addElement(LookupElementBuilderFactory.create("patch", CompletionStyleFactory.optional(shouldQuote)));
        result.addElement(LookupElementBuilderFactory.create("parameters", CompletionStyleFactory.optional(shouldQuote)));
    }

}
