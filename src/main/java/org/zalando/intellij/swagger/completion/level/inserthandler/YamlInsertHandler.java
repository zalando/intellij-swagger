package org.zalando.intellij.swagger.completion.level.inserthandler;

import com.intellij.codeInsight.completion.InsertHandler;
import com.intellij.codeInsight.completion.InsertionContext;
import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.openapi.editor.EditorModificationUtil;
import org.jetbrains.annotations.NotNull;
import org.zalando.intellij.swagger.completion.level.field.Field;
import org.zalando.intellij.swagger.completion.level.string.StringUtils;

public class YamlInsertHandler implements InsertHandler<LookupElement> {

    private final Field field;

    public YamlInsertHandler(final Field field) {
        this.field = field;
    }

    @Override
    public void handleInsert(final InsertionContext context, final LookupElement item) {
        if (!StringUtils.nextCharAfterSpacesIsColonOrQuote(getStringAfterAutoCompletedValue(context))) {
            final String suffixWithCaret = field.getYamlPlaceholderSuffix(getIndentation(context, item));
            final String suffixWithoutCaret = suffixWithCaret.replace("<caret>", "");
            EditorModificationUtil.insertStringAtCaret(context.getEditor(), suffixWithoutCaret, false, true, getCaretIndex(suffixWithCaret));
        }
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
}
