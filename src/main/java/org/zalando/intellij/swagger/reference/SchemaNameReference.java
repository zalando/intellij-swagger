package org.zalando.intellij.swagger.reference;

import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiReferenceBase;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.zalando.intellij.swagger.traversal.path.PathExpressionUtil;
import org.zalando.intellij.swagger.traversal.path.PathFinder;

public class SchemaNameReference extends PsiReferenceBase<PsiElement> {

  private final String originalRefValue;

  public SchemaNameReference(
      @NotNull final PsiElement element, @NotNull final String originalRefValue) {
    super(element);
    this.originalRefValue = originalRefValue;
  }

  @Nullable
  @Override
  public PsiElement resolve() {
    final String escaped = PathExpressionUtil.escape(originalRefValue);
    final String pathExpression = String.format("$.components.schemas.%s", escaped);

    final PsiFile psiFile = getElement().getContainingFile();

    return new PathFinder().findByPathFrom(pathExpression, psiFile).orElse(null);
  }

  @NotNull
  @Override
  public Object[] getVariants() {
    return new Object[0];
  }
}
