package org.zalando.intellij.swagger.completion.level;

import com.intellij.codeInsight.completion.CompletionResultSet;
import org.jetbrains.annotations.NotNull;
import org.zalando.intellij.swagger.completion.LevelCompletion;

import static org.zalando.intellij.swagger.completion.CompletionStyleFactory.optional;
import static org.zalando.intellij.swagger.completion.CompletionStyleFactory.required;
import static org.zalando.intellij.swagger.completion.LookupElementBuilderFactory.create;

public class ResponseLevelCompletion implements LevelCompletion {

    public void fill(@NotNull final CompletionResultSet result, final boolean shouldQuote) {
        result.addElement(create("description", required(shouldQuote)));
        result.addElement(create("schema", optional(shouldQuote)));
        result.addElement(create("headers", optional(shouldQuote)));
        result.addElement(create("examples", optional(shouldQuote)));
    }

}
