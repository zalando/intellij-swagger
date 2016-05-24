package org.zalando.intellij.swagger.completion.level.inserthandler;

import com.intellij.codeInsight.completion.InsertHandler;
import com.intellij.codeInsight.completion.InsertionContext;
import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.openapi.editor.EditorModificationUtil;
import org.jetbrains.annotations.NotNull;
import org.zalando.intellij.swagger.completion.level.string.StringUtils;
import org.zalando.intellij.swagger.completion.type.FieldType;

public class YamlInsertHandler implements InsertHandler<LookupElement> {

    @Override
    public void handleInsert(final InsertionContext context, final LookupElement item) {
        if (!StringUtils.nextCharAfterSpacesIsColonOrQuote(getStringAfterAutoCompletedValue(context))) {
            final FieldType fieldType = FieldType.fromFieldName(item.getLookupString());

            if (fieldType == FieldType.ARRAY) {
                insertArray(context, item);
            } else if (fieldType == FieldType.OBJECT) {
                insertObject(context, item);
            } else {
                insertString(context);
            }
        }
    }

    private void insertString(final InsertionContext context) {
        final String stringToInsert = ": ";
        EditorModificationUtil.insertStringAtCaret(context.getEditor(), stringToInsert, false, true, stringToInsert.length());
    }

    private void insertObject(final InsertionContext context, final LookupElement item) {
        final String indentation = getIndentation(context, item);
        final String stringToInsert = ":\n" + indentation;
        EditorModificationUtil.insertStringAtCaret(context.getEditor(), stringToInsert, false, true, stringToInsert.length());
    }

    private String getIndentation(final InsertionContext context, final LookupElement item) {
        final String stringBeforeAutoCompletedValue = getStringBeforeAutoCompletedValue(context, item);
        final int numberOfSpaces = StringUtils.getNumberOfSpacesInRowStartingFromEnd(stringBeforeAutoCompletedValue);
        return org.apache.commons.lang.StringUtils.repeat(" ", numberOfSpaces + 2);
    }

    private void insertArray(final InsertionContext context, final LookupElement item) {
        final String indentation = getIndentation(context, item);
        final String stringToInsert = ":\n" + indentation + "- ";
        EditorModificationUtil.insertStringAtCaret(context.getEditor(), stringToInsert, false, true, stringToInsert.length());
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
