package org.zalando.intellij.swagger.intention.reference;

import com.intellij.psi.PsiElement;
import org.zalando.intellij.swagger.traversal.Traversal;

class ReferenceCreator {

  private final String path;
  private final PsiElement anchorPsiElement;
  private final Traversal traversal;

  ReferenceCreator(
      final String path, final PsiElement anchorPsiElement, final Traversal traversal) {
    this.path = path;
    this.anchorPsiElement = anchorPsiElement;
    this.traversal = traversal;
  }

  public void create() {
    traversal.addReferenceDefinition(path, anchorPsiElement);
  }
}
