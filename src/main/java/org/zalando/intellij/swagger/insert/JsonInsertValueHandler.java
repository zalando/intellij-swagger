package org.zalando.intellij.swagger.insert;

import com.intellij.codeInsight.completion.InsertHandler;
import com.intellij.codeInsight.completion.InsertionContext;
import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.openapi.editor.EditorModificationUtil;
import com.intellij.util.text.CharArrayUtil;
import org.zalando.intellij.swagger.completion.value.model.Value;

public class JsonInsertValueHandler implements InsertHandler<LookupElement> {

    private final Value value;

    public JsonInsertValueHandler(final Value value) {
        this.value = value;
    }

    @Override
    public void handleInsert(final InsertionContext insertionContext, final LookupElement lookupElement) {
        if (value.isQuotable()) {
            handleStartingQuote(insertionContext, lookupElement);
            handleEndingQuote(insertionContext);
        }
    }

    private void handleStartingQuote(final InsertionContext insertionContext, final LookupElement lookupElement) {
        final int caretOffset = insertionContext.getEditor().getCaretModel().getOffset();
        final int startOfLookupStringOffset = caretOffset - lookupElement.getLookupString().length();
        final boolean hasStartingQuote = insertionContext.getDocument().getText().charAt(startOfLookupStringOffset - 1) == '\"';
        if (!hasStartingQuote) {
            insertionContext.getDocument().insertString(startOfLookupStringOffset, "\"");
        }
    }

    private void handleEndingQuote(final InsertionContext insertionContext) {
        final int caretOffset = insertionContext.getEditor().getCaretModel().getOffset();
        final CharSequence chars = insertionContext.getDocument().getCharsSequence();

        final boolean hasEndingQuote = CharArrayUtil.regionMatches(chars, caretOffset, "\"");
        if (!hasEndingQuote) {
            insertionContext.getDocument().insertString(caretOffset, "\"");
            EditorModificationUtil.moveCaretRelatively(insertionContext.getEditor(), 1);
        }
    }
}
