package org.zalando.intellij.swagger.annotator;

import com.intellij.lang.annotation.AnnotationHolder;
import com.intellij.lang.annotation.Annotator;
import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;
import org.zalando.intellij.swagger.file.FileDetector;
import org.zalando.intellij.swagger.traversal.JsonTraversal;
import org.zalando.intellij.swagger.traversal.path.swagger.MainPathResolver;
import org.zalando.intellij.swagger.validator.value.SchemesValidator;
import org.zalando.intellij.swagger.validator.value.ValuesValidator;

public class JsonValidValueAnnotator implements Annotator {

  @Override
  public void annotate(
      @NotNull final PsiElement psiElement, @NotNull final AnnotationHolder annotationHolder) {
    if (new FileDetector().isMainSwaggerJsonFile(psiElement.getContainingFile())) {
      final ValuesValidator valuesValidator =
          new ValuesValidator(new JsonTraversal(), new MainPathResolver(), new SchemesValidator());

      valuesValidator.validate(psiElement, annotationHolder);
    }
  }
}
