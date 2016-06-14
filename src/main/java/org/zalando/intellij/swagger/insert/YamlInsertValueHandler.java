package org.zalando.intellij.swagger.insert;

import com.intellij.codeInsight.completion.InsertHandler;
import com.intellij.codeInsight.completion.InsertionContext;
import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.util.text.CharArrayUtil;
import org.apache.commons.lang.StringUtils;

public class YamlInsertValueHandler implements InsertHandler<LookupElement> {

    private static final char[] RESERVED_YAML_CHARS = {
            ':', '{', '}', '[', ']', ',', '&', '*', '#', '?', '|', '-', '<', '>', '=', '!', '%', '@', '`'
    };

    @Override
    public void handleInsert(final InsertionContext insertionContext, final LookupElement lookupElement) {
        if (StringUtils.containsAny(lookupElement.getLookupString(), RESERVED_YAML_CHARS)) {
            handleEndingQuote(insertionContext);
            handleStartingQuote(insertionContext, lookupElement);
        }
    }

    private void handleStartingQuote(final InsertionContext insertionContext, final LookupElement lookupElement) {
        final int caretOffset = insertionContext.getEditor().getCaretModel().getOffset();
        final int startOfLookupStringOffset = caretOffset - lookupElement.getLookupString().length();
        final boolean hasStartingQuote = insertionContext.getDocument().getText().charAt(startOfLookupStringOffset - 1) == '\'';
        if (!hasStartingQuote) {
            insertionContext.getDocument().insertString(startOfLookupStringOffset, "'");
        }
    }

    private void handleEndingQuote(final InsertionContext insertionContext) {
        final int caretOffset = insertionContext.getEditor().getCaretModel().getOffset();
        final CharSequence chars = insertionContext.getDocument().getCharsSequence();

        final boolean hasEndingQuote = CharArrayUtil.regionMatches(chars, caretOffset, "'");
        if (!hasEndingQuote) {
            insertionContext.getDocument().insertString(caretOffset, "'");
        }
    }
}
