package org.zalando.intellij.swagger.completion.level.string;

public class StringUtils {

    public static boolean nextCharAfterSpacesIsColonOrQuote(final String string) {
        for (int i = 0; i < string.length(); i++) {
            final char c = string.charAt(i);
            if (c != ' ') {
                return c == ':' || c == '\"';
            }
        }
        return false;
    }

    public static int getNumberOfSpacesInRowStartingFromEnd(final String string) {
        int count = 0;
        for (int i = string.length() - 1; i >= 0; i--) {
            final char c = string.charAt(i);
            if (c != ' ') {
                return count;
            }
            count++;
        }
        return count;
    }

    public static String removeAllQuotes(final String string) {
        return string.replace("'", "").replace("\"", "");
    }
}
