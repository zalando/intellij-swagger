package org.zalando.intellij.swagger.traversal.path.openapi;

import com.intellij.psi.PsiElement;

public class ParametersPathResolver implements PathResolver {

  @Override
  public final boolean childOfParameters(final PsiElement psiElement) {
    return hasPath(psiElement, "$.*");
  }

  @Override
  public final boolean isParameter(final PsiElement psiElement) {
    return hasPath(psiElement, "$");
  }
}
