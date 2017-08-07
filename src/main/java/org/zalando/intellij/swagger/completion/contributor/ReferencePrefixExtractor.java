package org.zalando.intellij.swagger.completion.contributor;

import java.util.Optional;

public class ReferencePrefixExtractor {

    public Optional<String> getPrefix(int offset, final String text) {
        final StringBuilder prefixBuilder = new StringBuilder();
        while (offset > 0 && (Character.isLetterOrDigit(text.charAt(offset))
                || text.charAt(offset) == '#' || text.charAt(offset) == '/')) {
            prefixBuilder.insert(0, text.charAt(offset));
            offset--;
        }
        return prefixBuilder.length() > 0 ? Optional.of(prefixBuilder.toString()) : Optional.empty();
    }
}
