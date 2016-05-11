package org.zalando.intellij.swagger.completion.level;

import com.intellij.codeInsight.completion.CompletionResultSet;
import org.jetbrains.annotations.NotNull;
import org.zalando.intellij.swagger.completion.style.CompletionStyleFactory;

public class InfoLevelCompletion implements LevelCompletion {

    public void fill(@NotNull final CompletionResultSet result, final boolean shouldQuote) {
        result.addElement(LookupElementBuilderFactory.create("title", CompletionStyleFactory.required(shouldQuote)));
        result.addElement(LookupElementBuilderFactory.create("description", CompletionStyleFactory.optional(shouldQuote)));
        result.addElement(LookupElementBuilderFactory.create("termsOfService", CompletionStyleFactory.optional(shouldQuote)));
        result.addElement(LookupElementBuilderFactory.create("contact", CompletionStyleFactory.optional(shouldQuote)));
        result.addElement(LookupElementBuilderFactory.create("license", CompletionStyleFactory.optional(shouldQuote)));
        result.addElement(LookupElementBuilderFactory.create("version", CompletionStyleFactory.required(shouldQuote)));
    }

}
