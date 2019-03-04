package org.zalando.intellij.swagger.validator.value;

import com.intellij.lang.annotation.AnnotationHolder;
import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;
import org.zalando.intellij.swagger.traversal.Traversal;
import org.zalando.intellij.swagger.traversal.path.swagger.MainPathResolver;

public class ValuesValidator {

  private final Traversal traversal;
  private final MainPathResolver pathResolver;
  private final SchemesValidator schemesValidator;

  public ValuesValidator(
      final Traversal traversal,
      final MainPathResolver pathResolver,
      final SchemesValidator schemesValidator) {
    this.traversal = traversal;
    this.pathResolver = pathResolver;
    this.schemesValidator = schemesValidator;
  }

  public void validate(
      @NotNull final PsiElement psiElement, @NotNull final AnnotationHolder annotationHolder) {
    if (traversal.isValue(psiElement)) {
      // handleKeyValue(psiElement, annotationHolder);
    } else if (traversal.isArrayStringElement(psiElement)) {
      handleArrayStringValue(psiElement, annotationHolder);
    }
  }

  private void handleArrayStringValue(
      final @NotNull PsiElement psiElement, final @NotNull AnnotationHolder annotationHolder) {
    if (pathResolver.isSchemesValue(psiElement)) {
      schemesValidator.validate(psiElement, annotationHolder);
    }
  }
}
