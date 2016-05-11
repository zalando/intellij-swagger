package org.zalando.intellij.swagger.completion.style;

public class CompletionStyle {

    public enum FontWeight {
        NORMAL, BOLD
    }

    private final FontWeight fontWeight;
    private final boolean shouldQuote;

    CompletionStyle(final FontWeight fontWeight, final boolean shouldQuote) {
        this.fontWeight = fontWeight;
        this.shouldQuote = shouldQuote;
    }

    public FontWeight getFontWeight() {
        return fontWeight;
    }

    public boolean isShouldQuote() {
        return shouldQuote;
    }

}
