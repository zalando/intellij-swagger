package org.zalando.intellij.swagger.traversal.path.openapi;

import com.intellij.psi.PsiElement;

public class RequestBodiesPathResolver implements PathResolver {

  @Override
  public final boolean childOfRequestBody(final PsiElement psiElement) {
    return hasPath(psiElement, "$.*");
  }

  @Override
  public final boolean isRequestBody(final PsiElement psiElement) {
    return hasPath(psiElement, "$");
  }
}
