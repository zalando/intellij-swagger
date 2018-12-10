package org.zalando.intellij.swagger.traversal.path.openapi;

import com.intellij.psi.PsiElement;

public class ResponsesPathResolver implements PathResolver {

  @Override
  public final boolean childOfResponse(final PsiElement psiElement) {
    return hasPath(psiElement, "$.*");
  }

  @Override
  public final boolean isResponse(final PsiElement psiElement) {
    return hasPath(psiElement, "$");
  }
}
