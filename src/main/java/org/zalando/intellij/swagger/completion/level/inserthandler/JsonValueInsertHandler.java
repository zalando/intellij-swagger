package org.zalando.intellij.swagger.completion.level.inserthandler;

import com.intellij.codeInsight.completion.InsertHandler;
import com.intellij.codeInsight.completion.InsertionContext;
import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.openapi.editor.EditorModificationUtil;
import com.intellij.util.text.CharArrayUtil;

public class JsonValueInsertHandler implements InsertHandler<LookupElement> {

    @Override
    public void handleInsert(final InsertionContext insertionContext, final LookupElement lookupElement) {
        handleCommentCharacters(insertionContext, lookupElement);
    }

    private void handleCommentCharacters(final InsertionContext insertionContext, final LookupElement lookupElement) {
        final int caretOffset = insertionContext.getEditor().getCaretModel().getOffset();
        if (quoted(lookupElement)) {
            final int startOfLookupStringOffset = caretOffset - lookupElement.getLookupString().length();
            final boolean hasCommentPrefix =
                    CharArrayUtil.regionMatches(insertionContext.getDocument().getCharsSequence(), startOfLookupStringOffset - 1, "#\"#");
            if (hasCommentPrefix) {
                insertionContext.getDocument().deleteString(startOfLookupStringOffset - 1, startOfLookupStringOffset);
                EditorModificationUtil.moveCaretRelatively(insertionContext.getEditor(), -1);
            }
        } else {
            final int startOfLookupStringOffset = caretOffset - lookupElement.getLookupString().length() - 1;
            final boolean hasCommentPrefix = insertionContext.getDocument().getText().charAt(startOfLookupStringOffset - 1) == '#';
            if (hasCommentPrefix) {
                insertionContext.getDocument().deleteString(startOfLookupStringOffset - 1, startOfLookupStringOffset);
            }
        }
    }

    private boolean quoted(final LookupElement lookupElement) {
        return lookupElement.getLookupString().startsWith("\"");
    }
}
