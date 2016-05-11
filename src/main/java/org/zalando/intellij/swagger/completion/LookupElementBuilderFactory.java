package org.zalando.intellij.swagger.completion;

import com.intellij.codeInsight.lookup.LookupElementBuilder;

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
