package org.zalando.intellij.swagger.completion;

public class CompletionStyleFactory {

    public static CompletionStyle required(final boolean shouldQuote) {
        return new CompletionStyle(CompletionStyle.FontWeight.BOLD, shouldQuote);
    }

    public static CompletionStyle optional(final boolean shouldQuote) {
        return new CompletionStyle(CompletionStyle.FontWeight.NORMAL, shouldQuote);
    }

}
