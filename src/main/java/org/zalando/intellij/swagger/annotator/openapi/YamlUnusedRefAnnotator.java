package org.zalando.intellij.swagger.annotator.openapi;

import com.intellij.psi.PsiElement;
import org.zalando.intellij.swagger.traversal.YamlTraversal;

public class YamlUnusedRefAnnotator extends UnusedRefAnnotator {

  public YamlUnusedRefAnnotator() {
    super(new YamlTraversal());
  }

  @Override
  public PsiElement getPsiElement(final PsiElement psiElement) {
    return psiElement.getParent().getParent();
  }

  @Override
  public PsiElement getSearchablePsiElement(final PsiElement psiElement) {
    return psiElement.getParent();
  }
}
