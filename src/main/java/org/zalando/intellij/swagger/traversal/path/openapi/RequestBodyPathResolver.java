package org.zalando.intellij.swagger.traversal.path.openapi;

import com.intellij.psi.PsiElement;

public class RequestBodyPathResolver implements PathResolver {

  @Override
  public final boolean childOfRequestBody(final PsiElement psiElement) {
    return hasPath(psiElement, "$");
  }
}
