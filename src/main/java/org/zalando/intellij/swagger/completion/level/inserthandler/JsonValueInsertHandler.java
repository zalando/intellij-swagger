package org.zalando.intellij.swagger.completion.level.inserthandler;

import com.intellij.codeInsight.completion.InsertHandler;
import com.intellij.codeInsight.completion.InsertionContext;
import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.EditorModificationUtil;
import com.intellij.util.text.CharArrayUtil;

public class JsonValueInsertHandler implements InsertHandler<LookupElement> {

    @Override
    public void handleInsert(final InsertionContext insertionContext, final LookupElement lookupElement) {
        handleCommentCharacters(insertionContext, lookupElement);
    }

    private void handleCommentCharacters(final InsertionContext insertionContext, final LookupElement lookupElement) {
        final int caretOffset = insertionContext.getEditor().getCaretModel().getOffset();
        final Document document = insertionContext.getDocument();

        if (quoted(lookupElement)) {
            final int startOfLookupStringOffset = caretOffset - lookupElement.getLookupString().length();
            final boolean hasCommentPrefix =
                    CharArrayUtil.regionMatches(document.getCharsSequence(), startOfLookupStringOffset - 1, "#\"#");
            if (hasCommentPrefix) {
                document.deleteString(startOfLookupStringOffset - 1, startOfLookupStringOffset);
                EditorModificationUtil.moveCaretRelatively(insertionContext.getEditor(), -1);
            } else {
                final boolean hasCommentPrefixWithSlash =
                        CharArrayUtil.regionMatches(document.getCharsSequence(), startOfLookupStringOffset - 2, "#/");
                if(hasCommentPrefixWithSlash) {
                    document.deleteString(startOfLookupStringOffset - 2, startOfLookupStringOffset);
                    EditorModificationUtil.moveCaretRelatively(insertionContext.getEditor(), -1);
                }
            }
        } else {
            final int startOfLookupStringOffset = caretOffset - lookupElement.getLookupString().length() - 1;
            final boolean hasCommentPrefix = document.getText().charAt(startOfLookupStringOffset - 1) == '#';
            if (hasCommentPrefix) {
                document.deleteString(startOfLookupStringOffset - 1, startOfLookupStringOffset);
            }
        }
    }

    private boolean quoted(final LookupElement lookupElement) {
        return lookupElement.getLookupString().startsWith("\"");
    }
}
