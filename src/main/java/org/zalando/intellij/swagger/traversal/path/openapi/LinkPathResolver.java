package org.zalando.intellij.swagger.traversal.path.openapi;

import com.intellij.psi.PsiElement;

public class LinkPathResolver implements PathResolver {

  @Override
  public final boolean childOfLink(final PsiElement psiElement) {
    return hasPath(psiElement, "$");
  }
}
