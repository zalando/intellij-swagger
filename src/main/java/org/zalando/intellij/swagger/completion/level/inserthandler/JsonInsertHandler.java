package org.zalando.intellij.swagger.completion.level.inserthandler;

import com.intellij.codeInsight.completion.InsertHandler;
import com.intellij.codeInsight.completion.InsertionContext;
import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.openapi.editor.EditorModificationUtil;
import com.intellij.psi.PsiFile;
import org.jetbrains.annotations.NotNull;
import org.zalando.intellij.swagger.completion.level.field.Field;
import org.zalando.intellij.swagger.completion.level.string.StringUtils;
import org.zalando.intellij.swagger.completion.traversal.JsonTraversal;

import java.util.Optional;

public class JsonInsertHandler implements InsertHandler<LookupElement> {

    private final JsonTraversal jsonTraversal;
    private final Field field;

    public JsonInsertHandler(final JsonTraversal jsonTraversal, final Field field) {
        this.jsonTraversal = jsonTraversal;
        this.field = field;
    }

    @Override
    public void handleInsert(final InsertionContext context, final LookupElement item) {
        if (!StringUtils.nextCharAfterSpacesIsColonOrQuote(getStringAfterAutoCompletedValue(context))) {
            final String suffixWithCaret = field.getJsonPlaceholderSuffix(getIndentation(context, item));
            final String suffixWithoutCaret = suffixWithCaret.replace("<caret>", "");
            EditorModificationUtil.insertStringAtCaret(context.getEditor(), withOptionalComma(suffixWithoutCaret, context), false, true, getCaretIndex(suffixWithCaret));
        }
    }

    private String withOptionalComma(final String suffix, final InsertionContext context) {
        return shouldAddComma(context) ? suffix + "," : suffix;
    }

    private int getCaretIndex(final String suffix) {
        return suffix.indexOf("<caret>");
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
        return context.getDocument().getText().substring(0, context.getTailOffset() - item.getLookupString().length());
    }

    private boolean shouldAddComma(final @NotNull InsertionContext context) {
        PsiFile psiFile = context.getFile();
        return Optional.ofNullable(psiFile.findElementAt(context.getStartOffset()))
                .map(jsonTraversal::isLastChild)
                .map(b -> !b)
                .orElse(false);
    }

}
