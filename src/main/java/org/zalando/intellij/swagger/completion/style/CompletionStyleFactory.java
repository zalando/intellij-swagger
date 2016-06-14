package org.zalando.intellij.swagger.completion.style;

import org.zalando.intellij.swagger.traversal.PositionResolver;

public class CompletionStyleFactory {

    public static CompletionStyle required(final PositionResolver positionResolver) {
        return positionResolver.shouldQuote()
                ? new CompletionStyle(CompletionStyle.FontWeight.BOLD, positionResolver.getQuoteStyle())
                : new CompletionStyle(CompletionStyle.FontWeight.BOLD);
    }

    public static CompletionStyle optional(final PositionResolver positionResolver) {
        return positionResolver.shouldQuote()
                ? new CompletionStyle(CompletionStyle.FontWeight.NORMAL, positionResolver.getQuoteStyle())
                : new CompletionStyle(CompletionStyle.FontWeight.NORMAL);
    }

}
