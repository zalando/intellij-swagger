package org.zalando.intellij.swagger.completion.style;

public class CompletionStyle {

    public enum FontWeight {
        NORMAL, BOLD
    }

    public enum QuoteStyle {
        SINGLE, DOUBLE
    }

    private final FontWeight fontWeight;
    private final QuoteStyle quoteStyle;

    CompletionStyle(final FontWeight fontWeight) {
        this(fontWeight, null);
    }

    CompletionStyle(final FontWeight fontWeight, final QuoteStyle quoteStyle) {
        this.fontWeight = fontWeight;
        this.quoteStyle = quoteStyle;
    }

    public FontWeight getFontWeight() {
        return fontWeight;
    }

    public boolean isShouldQuote() {
        return quoteStyle != null;
    }

}
