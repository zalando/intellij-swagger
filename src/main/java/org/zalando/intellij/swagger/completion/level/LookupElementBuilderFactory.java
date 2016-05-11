package org.zalando.intellij.swagger.completion.level;

import com.intellij.codeInsight.lookup.LookupElementBuilder;
import org.zalando.intellij.swagger.completion.style.CompletionStyle;

public class LookupElementBuilderFactory {

    public static LookupElementBuilder create(final String lookup,
                                              final CompletionStyle completionStyle) {
        final String value = completionStyle.isShouldQuote() ? "\"" + lookup + "\"" : lookup;
        LookupElementBuilder lookupElementBuilder = LookupElementBuilder.create(value);

        if (completionStyle.getFontWeight() == CompletionStyle.FontWeight.BOLD) {
            lookupElementBuilder = lookupElementBuilder.bold();
        }

        return lookupElementBuilder;
    }

}
