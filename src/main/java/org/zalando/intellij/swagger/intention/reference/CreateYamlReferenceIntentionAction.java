package org.zalando.intellij.swagger.intention.reference;

import com.intellij.codeInsight.intention.IntentionAction;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiDocumentManager;
import com.intellij.psi.PsiFile;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.zalando.intellij.swagger.reference.extractor.ReferenceValueExtractor;
import org.zalando.intellij.swagger.traversal.YamlTraversal;

public class CreateYamlReferenceIntentionAction implements IntentionAction {

    private final String referenceValueWithPrefix;
    private final ReferenceValueExtractor referenceValueExtractor;

    public CreateYamlReferenceIntentionAction(final String referenceValueWithPrefix,
                                              final ReferenceValueExtractor referenceValueExtractor) {
        this.referenceValueWithPrefix = referenceValueWithPrefix;
        this.referenceValueExtractor = referenceValueExtractor;
    }

    @Nls
    @NotNull
    @Override
    public String getText() {
        return "Create";
    }

    @Nls
    @NotNull
    @Override
    public String getFamilyName() {
        return "Create";
    }

    @Override
    public boolean isAvailable(@NotNull final Project project, final Editor editor, final PsiFile psiFile) {
        return true;
    }

    @Override
    public void invoke(@NotNull final Project project, final Editor editor, final PsiFile psiFile) {
        final String referenceType = referenceValueExtractor.getReferenceType(referenceValueWithPrefix);
        final String referenceValueWithoutPrefix = referenceValueExtractor.getValue(referenceValueWithPrefix);

        new ReferenceCreator(referenceValueWithoutPrefix, referenceType, psiFile, new YamlTraversal()).create();
        PsiDocumentManager.getInstance(psiFile.getProject()).doPostponedOperationsAndUnblockDocument(editor.getDocument());

        forceDocumentUpdate(editor, psiFile);
    }

    private void forceDocumentUpdate(final Editor editor, final PsiFile psiFile) {
        editor.getDocument().insertString(0, " ");
        PsiDocumentManager.getInstance(psiFile.getProject()).commitDocument(editor.getDocument());
        editor.getDocument().deleteString(0, 1);
    }

    @Override
    public boolean startInWriteAction() {
        return true;
    }
}
