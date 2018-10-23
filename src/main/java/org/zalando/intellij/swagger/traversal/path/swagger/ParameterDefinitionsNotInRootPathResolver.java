package org.zalando.intellij.swagger.traversal.path.swagger;

import com.intellij.psi.PsiElement;

public class ParameterDefinitionsNotInRootPathResolver implements PathResolver {

  @Override
  public final boolean childOfParameterDefinition(final PsiElement psiElement) {
    return hasPath(psiElement, "$.*.*");
  }

  @Override
  public final boolean isParameter(final PsiElement psiElement) {
    return hasPath(psiElement, "$.*");
  }
}
