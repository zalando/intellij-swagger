package org.zalando.intellij.swagger.intention.field;

import com.intellij.codeInsight.intention.IntentionAction;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiDocumentManager;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;

public class RemoveYamlFieldIntentionAction implements IntentionAction {

    private final PsiElement psiElement;

    public RemoveYamlFieldIntentionAction(final PsiElement psiElement) {
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
        removeEmptyLineIfNecessary(editor);
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
        psiElement.getParent().delete();

        //noinspection ConstantConditions
        PsiDocumentManager.getInstance(editor.getProject()).doPostponedOperationsAndUnblockDocument(editor.getDocument());
    }

    @Override
    public boolean startInWriteAction() {
        return true;
    }
}
