package org.zalando.intellij.swagger.intention.field;

import com.intellij.codeInsight.intention.IntentionAction;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiDocumentManager;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.zalando.intellij.swagger.StringUtils;

public class RemoveJsonFieldIntentionAction implements IntentionAction {

    private final PsiElement psiElement;

    public RemoveJsonFieldIntentionAction(final PsiElement psiElement) {
        this.psiElement = psiElement;
    }

    @Nls
    @NotNull
    @Override
    public String getText() {
        return "Remove field";
    }

    @Nls
    @NotNull
    @Override
    public String getFamilyName() {
        return "Remove field";
    }

    @Override
    public boolean isAvailable(@NotNull final Project project, final Editor editor, final PsiFile psiFile) {
        return true;
    }

    @Override
    public void invoke(@NotNull final Project project, final Editor editor, final PsiFile psiFile) {
        deleteField(editor);
        removeCommaIfNecessary(editor);
        removeEmptyLineIfNecessary(editor);
        removeSiblingFieldCommaIfNecessary(editor);
    }

    private void removeSiblingFieldCommaIfNecessary(final Editor editor) {
        final int offset = editor.getCaretModel().getOffset();
        final String textAfterOffset = editor.getDocument().getText().substring(offset);
        if (StringUtils.nextCharAfterSpacesAndLineBreaksIsCurlyBraces(textAfterOffset)) {
            final String textBeforeOffset = editor.getDocument().getText().substring(0, offset);
            if (StringUtils.nextCharAfterSpacesAndLineBreaksIsCommaStartingFromEnd(textBeforeOffset)) {
                final int lastCommaIndex = textBeforeOffset.lastIndexOf(",");
                editor.getDocument().deleteString(lastCommaIndex, lastCommaIndex + 1);
            }
        }
    }

    private void removeEmptyLineIfNecessary(final Editor editor) {
        final int offset = editor.getCaretModel().getOffset();
        final int lineNumber = editor.getDocument().getLineNumber(offset);
        final int lineStartOffset = editor.getDocument().getLineStartOffset(lineNumber);
        final int lineEndOffset = editor.getDocument().getLineEndOffset(lineNumber);
        final String lineContent = editor.getDocument().getText().substring(lineStartOffset, lineEndOffset);
        if ("".equals(lineContent.trim())) {
            final int endIndex = editor.getDocument().getText().length() > lineEndOffset ? lineEndOffset + 1 : lineEndOffset;
            editor.getDocument().deleteString(lineStartOffset, endIndex);
        }
    }

    private void deleteField(final Editor editor) {
        psiElement.getParent().getParent().delete();
        //noinspection ConstantConditions
        PsiDocumentManager.getInstance(editor.getProject()).doPostponedOperationsAndUnblockDocument(editor.getDocument());
    }

    private void removeCommaIfNecessary(final Editor editor) {
        final int offset = editor.getCaretModel().getOffset();
        if (editor.getDocument().getText().charAt(offset) == ',') {
            editor.getDocument().deleteString(offset, offset + 1);
        }
    }

    @Override
    public boolean startInWriteAction() {
        return true;
    }
}
