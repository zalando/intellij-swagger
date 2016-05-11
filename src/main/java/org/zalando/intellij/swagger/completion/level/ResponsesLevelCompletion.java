package org.zalando.intellij.swagger.completion.level;

import com.intellij.codeInsight.completion.CompletionResultSet;
import org.jetbrains.annotations.NotNull;

import static org.zalando.intellij.swagger.completion.style.CompletionStyleFactory.optional;
import static org.zalando.intellij.swagger.completion.level.LookupElementBuilderFactory.create;

public class ResponsesLevelCompletion implements LevelCompletion {

    public void fill(@NotNull final CompletionResultSet result, final boolean shouldQuote) {
        result.addElement(create("default", optional(shouldQuote)));
        result.addElement(create("200", optional(shouldQuote)));
        result.addElement(create("201", optional(shouldQuote)));
        result.addElement(create("202", optional(shouldQuote)));
        result.addElement(create("203", optional(shouldQuote)));
        result.addElement(create("204", optional(shouldQuote)));
        result.addElement(create("205", optional(shouldQuote)));
        result.addElement(create("206", optional(shouldQuote)));
        result.addElement(create("207", optional(shouldQuote)));
        result.addElement(create("208", optional(shouldQuote)));
        result.addElement(create("226", optional(shouldQuote)));

        result.addElement(create("300", optional(shouldQuote)));
        result.addElement(create("301", optional(shouldQuote)));
        result.addElement(create("302", optional(shouldQuote)));
        result.addElement(create("303", optional(shouldQuote)));
        result.addElement(create("304", optional(shouldQuote)));
        result.addElement(create("305", optional(shouldQuote)));
        result.addElement(create("306", optional(shouldQuote)));
        result.addElement(create("307", optional(shouldQuote)));
        result.addElement(create("308", optional(shouldQuote)));

        result.addElement(create("400", optional(shouldQuote)));
        result.addElement(create("401", optional(shouldQuote)));
        result.addElement(create("402", optional(shouldQuote)));
        result.addElement(create("403", optional(shouldQuote)));
        result.addElement(create("404", optional(shouldQuote)));
        result.addElement(create("405", optional(shouldQuote)));
        result.addElement(create("406", optional(shouldQuote)));
        result.addElement(create("407", optional(shouldQuote)));
        result.addElement(create("408", optional(shouldQuote)));
        result.addElement(create("409", optional(shouldQuote)));
        result.addElement(create("410", optional(shouldQuote)));
        result.addElement(create("411", optional(shouldQuote)));
        result.addElement(create("412", optional(shouldQuote)));
        result.addElement(create("413", optional(shouldQuote)));
        result.addElement(create("414", optional(shouldQuote)));
        result.addElement(create("415", optional(shouldQuote)));
        result.addElement(create("416", optional(shouldQuote)));
        result.addElement(create("417", optional(shouldQuote)));
        result.addElement(create("418", optional(shouldQuote)));
        result.addElement(create("421", optional(shouldQuote)));
        result.addElement(create("422", optional(shouldQuote)));
        result.addElement(create("423", optional(shouldQuote)));
        result.addElement(create("424", optional(shouldQuote)));
        result.addElement(create("426", optional(shouldQuote)));
        result.addElement(create("428", optional(shouldQuote)));
        result.addElement(create("429", optional(shouldQuote)));
        result.addElement(create("431", optional(shouldQuote)));
        result.addElement(create("451", optional(shouldQuote)));

        result.addElement(create("500", optional(shouldQuote)));
        result.addElement(create("501", optional(shouldQuote)));
        result.addElement(create("502", optional(shouldQuote)));
        result.addElement(create("503", optional(shouldQuote)));
        result.addElement(create("504", optional(shouldQuote)));
        result.addElement(create("505", optional(shouldQuote)));
        result.addElement(create("506", optional(shouldQuote)));
        result.addElement(create("507", optional(shouldQuote)));
        result.addElement(create("508", optional(shouldQuote)));
        result.addElement(create("510", optional(shouldQuote)));
        result.addElement(create("511", optional(shouldQuote)));
    }

}
