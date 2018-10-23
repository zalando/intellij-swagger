package org.zalando.intellij.swagger.traversal.path.swagger;

import com.intellij.psi.PsiElement;

public class ResponseDefinitionsNotInRootPathResolver implements PathResolver {

  @Override
  public final boolean childOfResponseDefinition(final PsiElement psiElement) {
    return hasPath(psiElement, "$.*.*");
  }

  @Override
  public final boolean childOfSchema(final PsiElement psiElement) {
    return hasPath(psiElement, "$.**.schema");
  }

  @Override
  public final boolean childOfSchemaItems(final PsiElement psiElement) {
    return hasPath(psiElement, "$.**.schema.items");
  }

  @Override
  public final boolean isResponse(final PsiElement psiElement) {
    return hasPath(psiElement, "$.*");
  }
}
