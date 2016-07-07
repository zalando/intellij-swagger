package org.zalando.intellij.swagger.intention.reference;

import com.intellij.psi.PsiFile;
import org.zalando.intellij.swagger.traversal.Traversal;

class ReferenceCreator {

    private final String referenceValueWithoutPrefix;
    private final String referenceType;
    private final PsiFile psiFile;
    private final Traversal traversal;

    ReferenceCreator(final String referenceValueWithoutPrefix,
                     final String referenceType,
                     final PsiFile psiFile,
                     final Traversal traversal) {
        this.referenceValueWithoutPrefix = referenceValueWithoutPrefix;
        this.referenceType = referenceType;
        this.psiFile = psiFile;
        this.traversal = traversal;
    }

    public void create() {
        traversal.addReferenceDefinition(referenceType, referenceValueWithoutPrefix, psiFile);
    }

}
