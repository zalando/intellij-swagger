package org.zalando.intellij.swagger.traversal.path.openapi;

import com.intellij.psi.PsiElement;

public class CallbacksPathResolver implements PathResolver {

  @Override
  public final boolean childOfCallback(final PsiElement psiElement) {
    return hasPath(psiElement, "$.*");
  }

  @Override
  public final boolean isCallback(final PsiElement psiElement) {
    return hasPath(psiElement, "$");
  }
}
