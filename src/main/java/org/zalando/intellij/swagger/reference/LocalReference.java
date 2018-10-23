package org.zalando.intellij.swagger.reference;

import static org.zalando.intellij.swagger.reference.SwaggerConstants.SLASH;

import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiReferenceBase;
import com.intellij.util.IncorrectOperationException;
import java.util.Arrays;
import java.util.stream.Collectors;
import org.apache.commons.lang.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.zalando.intellij.swagger.traversal.path.PathFinder;

public class LocalReference extends PsiReferenceBase<PsiElement> {

  private final String originalRefValue;

  public LocalReference(@NotNull final PsiElement element, @NotNull final String originalRefValue) {
    super(element);
    this.originalRefValue = originalRefValue;
  }

  @Nullable
  @Override
  public PsiElement resolve() {
    final String pathExpression =
        Arrays.stream(originalRefValue.substring(2, originalRefValue.length()).split("/"))
            .map(s -> s.replace(".", "\\."))
            .collect(Collectors.joining(".", "$.", ""));

    final PsiFile psiFile = getElement().getContainingFile();

    return new PathFinder().findByPathFrom(pathExpression, psiFile).orElse(null);
  }

  @NotNull
  @Override
  public Object[] getVariants() {
    return new Object[0];
  }

  @Override
  public PsiElement handleElementRename(String newElementName) throws IncorrectOperationException {
    final String referencePrefix = StringUtils.substringBeforeLast(originalRefValue, "/");
    return super.handleElementRename(referencePrefix + SLASH + newElementName);
  }
}
