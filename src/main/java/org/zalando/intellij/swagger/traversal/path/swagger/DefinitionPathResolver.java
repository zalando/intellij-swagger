package org.zalando.intellij.swagger.traversal.path.swagger;

import com.intellij.psi.PsiElement;

public class DefinitionPathResolver implements PathResolver {

  @Override
  public final boolean childOfSchema(final PsiElement psiElement) {
    return hasPath(psiElement, "$");
  }

  @Override
  public final boolean childOfSchemaItems(final PsiElement psiElement) {
    return hasPath(psiElement, "$.**.items");
  }
}
