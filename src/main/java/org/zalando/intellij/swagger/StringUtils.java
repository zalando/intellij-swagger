package org.zalando.intellij.swagger;

public class StringUtils {

    public static boolean nextCharAfterSpacesAndQuotesIsColon(final String string) {
        for (int i = 0; i < string.length(); i++) {
            final char c = string.charAt(i);
            if (c != ' ' && c != '"') {
                return c == ':';
            }
        }
        return false;
    }

    public static boolean nextCharAfterSpacesAndLineBreaksIsCurlyBraces(final String string) {
        for (int i = 0; i < string.length(); i++) {
            final char c = string.charAt(i);
            if (c != ' ' && c != '\n') {
                return c == '}';
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

    public static boolean hasSingleQuoteBeforeColonStartingFromEnd(final String string) {
        final int lastIndexOfColon = string.lastIndexOf(":");
        final int lastIndexOfSingleQuote = string.lastIndexOf("'");

        return lastIndexOfSingleQuote > lastIndexOfColon;
    }

    public static boolean nextCharAfterSpacesAndLineBreaksIsCommaStartingFromEnd(final String string) {
        for (int i = string.length() - 1; i >= 0; i--) {
            final char c = string.charAt(i);
            if (c != ' ' && c != '\n') {
                return c == ',';
            }
        }
        return false;
    }
}
