package org.zalando.intellij.swagger.validator.value;

import com.intellij.lang.annotation.AnnotationHolder;
import com.intellij.psi.PsiElement;
import java.util.stream.Collectors;
import org.zalando.intellij.swagger.StringUtils;
import org.zalando.intellij.swagger.completion.value.model.common.Value;
import org.zalando.intellij.swagger.completion.value.model.swagger.SwaggerValues;

public class SchemesValidator {

  public void validate(final PsiElement psiElement, final AnnotationHolder annotationHolder) {

    final String schemeValue = StringUtils.removeAllQuotes(psiElement.getText());

    final boolean schemeFound =
        SwaggerValues.schemes()
            .stream()
            .map(Value::getValue)
            .collect(Collectors.toSet())
            .contains(schemeValue);

    if (!schemeFound) {
      annotationHolder.createErrorAnnotation(psiElement, "Invalid scheme");
    }
  }
}
