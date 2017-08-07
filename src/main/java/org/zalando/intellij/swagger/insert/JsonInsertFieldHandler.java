package org.zalando.intellij.swagger.insert;

import com.intellij.codeInsight.completion.InsertHandler;
import com.intellij.codeInsight.completion.InsertionContext;
import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.openapi.editor.EditorModificationUtil;
import com.intellij.psi.PsiFile;
import com.intellij.util.text.CharArrayUtil;
import org.jetbrains.annotations.NotNull;
import org.zalando.intellij.swagger.StringUtils;
import org.zalando.intellij.swagger.completion.field.model.Field;
import org.zalando.intellij.swagger.traversal.JsonTraversal;

import java.util.Optional;

import static org.zalando.intellij.swagger.file.FileConstants.CARET;

public class JsonInsertFieldHandler implements InsertHandler<LookupElement> {

    private final JsonTraversal jsonTraversal;
    private final Field field;

    public JsonInsertFieldHandler(final JsonTraversal jsonTraversal, final Field field) {
        this.jsonTraversal = jsonTraversal;
        this.field = field;
    }

    @Override
    public void handleInsert(final InsertionContext insertionContext, final LookupElement lookupElement) {
        handleStartingQuote(insertionContext, lookupElement);
        handleEndingQuote(insertionContext);

        if (!StringUtils.nextCharAfterSpacesAndQuotesIsColon(getStringAfterAutoCompletedValue(insertionContext))) {
            final String suffixWithCaret = field.getJsonPlaceholderSuffix(getIndentation(insertionContext, lookupElement));
            final String suffixWithoutCaret = suffixWithCaret.replace(CARET, "");
            EditorModificationUtil.insertStringAtCaret(
                    insertionContext.getEditor(),
                    withOptionalComma(suffixWithoutCaret, insertionContext), false, true,
                    getCaretIndex(suffixWithCaret));
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
        }
        EditorModificationUtil.moveCaretRelatively(insertionContext.getEditor(), 1);
    }

    private String withOptionalComma(final String suffix, final InsertionContext context) {
        return shouldAddComma(context) ? suffix + "," : suffix;
    }

    private int getCaretIndex(final String suffix) {
        return suffix.indexOf(CARET);
    }

    private int getIndentation(final InsertionContext context, final LookupElement item) {
        final String stringBeforeAutoCompletedValue = getStringBeforeAutoCompletedValue(context, item);
        return StringUtils.getNumberOfSpacesInRowStartingFromEnd(stringBeforeAutoCompletedValue);
    }

    @NotNull
    private String getStringAfterAutoCompletedValue(final InsertionContext context) {
        return context.getDocument().getText().substring(context.getTailOffset());
    }

    @NotNull
    private String getStringBeforeAutoCompletedValue(final InsertionContext context, final LookupElement item) {
        final int tailOffset = context.getDocument().getText().substring(0, context.getTailOffset()).endsWith("\"")
                ? context.getTailOffset()
                : context.getTailOffset() + 1;

        return context.getDocument().getText().substring(0, tailOffset)
                .replace("\"" + item.getLookupString() + "\"", "");
    }

    private boolean shouldAddComma(final @NotNull InsertionContext context) {
        PsiFile psiFile = context.getFile();
        return Optional.ofNullable(psiFile.findElementAt(context.getStartOffset()))
                .map(el -> !jsonTraversal.isLastChild(el))
                .orElse(false);
    }

}
