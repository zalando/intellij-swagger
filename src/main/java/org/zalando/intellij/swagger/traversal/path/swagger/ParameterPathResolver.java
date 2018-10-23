package org.zalando.intellij.swagger.traversal.path.swagger;

import com.intellij.psi.PsiElement;

public class ParameterPathResolver implements PathResolver {

  @Override
  public final boolean childOfParameterItems(final PsiElement psiElement) {
    return hasPath(psiElement, "$.**.items");
  }

  @Override
  public final boolean childOfParameterDefinition(final PsiElement psiElement) {
    return hasPath(psiElement, "$");
  }
}
