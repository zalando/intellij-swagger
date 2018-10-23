package org.zalando.intellij.swagger.traversal.path.openapi;

import com.intellij.psi.PsiElement;

public class ResponsePathResolver implements PathResolver {

  @Override
  public final boolean childOfResponse(final PsiElement psiElement) {
    return hasPath(psiElement, "$");
  }
}
