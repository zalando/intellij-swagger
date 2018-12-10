package org.zalando.intellij.swagger.annotator.openapi;

import com.intellij.psi.PsiElement;
import org.zalando.intellij.swagger.traversal.JsonTraversal;

public class JsonUnusedRefAnnotator extends UnusedRefAnnotator {

  public JsonUnusedRefAnnotator() {
    super(new JsonTraversal());
  }

  @Override
  public PsiElement getPsiElement(final PsiElement psiElement) {
    return psiElement.getParent().getParent().getParent();
  }

  @Override
  public PsiElement getSearchablePsiElement(final PsiElement psiElement) {
    return psiElement.getParent().getParent();
  }
}
