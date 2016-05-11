package org.zalando.intellij.swagger.completion.level;

import com.intellij.codeInsight.completion.CompletionResultSet;
import org.jetbrains.annotations.NotNull;
import org.zalando.intellij.swagger.completion.CompletionStyleFactory;
import org.zalando.intellij.swagger.completion.LevelCompletion;
import org.zalando.intellij.swagger.completion.LookupElementBuilderFactory;

public class ExternalDocsLevelCompletion implements LevelCompletion {

    public void fill(@NotNull final CompletionResultSet result, final boolean shouldQuote) {
        result.addElement(LookupElementBuilderFactory.create("description", CompletionStyleFactory.optional(shouldQuote)));
        result.addElement(LookupElementBuilderFactory.create("url", CompletionStyleFactory.optional(shouldQuote)));
    }

}
