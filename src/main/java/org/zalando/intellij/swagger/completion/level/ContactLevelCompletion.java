package org.zalando.intellij.swagger.completion.level;

import com.intellij.codeInsight.completion.CompletionResultSet;
import org.jetbrains.annotations.NotNull;
import org.zalando.intellij.swagger.completion.LevelCompletion;

import static org.zalando.intellij.swagger.completion.CompletionStyleFactory.optional;
import static org.zalando.intellij.swagger.completion.LookupElementBuilderFactory.create;

public class ContactLevelCompletion implements LevelCompletion {

    public void fill(@NotNull final CompletionResultSet result, final boolean shouldQuote) {
        result.addElement(create("name", optional(shouldQuote)));
        result.addElement(create("url", optional(shouldQuote)));
        result.addElement(create("email", optional(shouldQuote)));
    }

}
