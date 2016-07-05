package org.zalando.intellij.swagger.intention.reference;

import com.intellij.codeInsight.intention.IntentionAction;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiFile;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.zalando.intellij.swagger.reference.extractor.ReferenceValueExtractor;
import org.zalando.intellij.swagger.traversal.JsonTraversal;

public class CreateJsonReferenceIntentionAction implements IntentionAction {

    private final String referenceValueWithPrefix;
    private final ReferenceValueExtractor referenceValueExtractor;

    public CreateJsonReferenceIntentionAction(final String referenceValueWithPrefix,
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

        new ReferenceCreator(referenceValueWithoutPrefix, referenceType, psiFile, new JsonTraversal()).create();
    }

    @Override
    public boolean startInWriteAction() {
        return true;
    }
}
