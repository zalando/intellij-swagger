package org.zalando.intellij.swagger.completion.level.inserthandler;

import com.intellij.codeInsight.completion.InsertHandler;
import com.intellij.codeInsight.completion.InsertionContext;
import com.intellij.codeInsight.completion.OffsetKey;
import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.util.text.CharArrayUtil;
import org.apache.commons.lang.StringUtils;

public class YamlValueInsertHandler implements InsertHandler<LookupElement> {

    private static final char[] RESERVED_YAML_CHARS = {
            ':', '{', '}', '[', ']', ',', '&', '*', '#', '?', '|', '-', '<', '>', '=', '!', '%', '@', '`'
    };

    @Override
    public void handleInsert(final InsertionContext insertionContext, final LookupElement lookupElement) {
        if (StringUtils.containsAny(lookupElement.getLookupString(), RESERVED_YAML_CHARS)) {
            final int caretOffset = insertionContext.getEditor().getCaretModel().getOffset();
            final CharSequence chars = insertionContext.getDocument().getCharsSequence();

            final boolean hasEndingQuote = CharArrayUtil.regionMatches(chars, caretOffset, "'");
            if (!hasEndingQuote) {
                insertionContext.getDocument().insertString(caretOffset, "'");
            }

            final int startOfLookupStringOffset = caretOffset - lookupElement.getLookupString().length();
            final boolean hasStartingQuote = hasStartingQuote(insertionContext);
            if (!hasStartingQuote) {
                insertionContext.getDocument().insertString(startOfLookupStringOffset, "'");
            }
        }
    }

    private boolean hasStartingQuote(final InsertionContext insertionContext) {
        final int caretOffset = insertionContext.getEditor().getCaretModel().getOffset();
        final String documentText = insertionContext.getDocument().getText();
        return org.zalando.intellij.swagger.completion.level.string.StringUtils
                .hasSingleQuoteBeforeColonStartingFromEnd(documentText.substring(0, caretOffset));
    }
}
