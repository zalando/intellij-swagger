package org.zalando.intellij.swagger.validator.value;

import com.intellij.codeInsight.intention.IntentionAction;
import com.intellij.lang.annotation.Annotation;
import com.intellij.lang.annotation.AnnotationHolder;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiNamedElement;
import java.util.Set;
import java.util.stream.Collectors;
import org.zalando.intellij.swagger.StringUtils;
import org.zalando.intellij.swagger.reference.ReferenceValueExtractor;
import org.zalando.intellij.swagger.traversal.path.PathFinder;

public class ReferenceValidator {

  private final IntentionAction intentionAction;

  public ReferenceValidator(final IntentionAction intentionAction) {
    this.intentionAction = intentionAction;
  }

  void validateDefinitionReference(
      final PsiElement psiElement, final AnnotationHolder annotationHolder) {
    if (isLocalReference(psiElement)) {
      final boolean definitionFound =
          getAvailableDefinitions(psiElement)
              .contains(ReferenceValueExtractor.extractValue(psiElement.getText()));

      if (!definitionFound) {
        final Annotation errorAnnotation =
            annotationHolder.createErrorAnnotation(psiElement, "Definition not found");
        errorAnnotation.registerFix(intentionAction);
      }
    }
  }

  void validateParameterReference(
      final PsiElement psiElement, final AnnotationHolder annotationHolder) {
    if (isLocalReference(psiElement)) {
      final boolean parameterFound =
          getAvailableParameters(psiElement)
              .contains(ReferenceValueExtractor.extractValue(psiElement.getText()));

      if (!parameterFound) {
        final Annotation errorAnnotation =
            annotationHolder.createErrorAnnotation(psiElement, "Parameter not found");
        errorAnnotation.registerFix(intentionAction);
      }
    }
  }

  void validateResponseReference(
      final PsiElement psiElement, final AnnotationHolder annotationHolder) {
    if (isLocalReference(psiElement)) {
      final boolean responseFound =
          getAvailableResponses(psiElement)
              .contains(ReferenceValueExtractor.extractValue(psiElement.getText()));

      if (!responseFound) {
        final Annotation errorAnnotation =
            annotationHolder.createErrorAnnotation(psiElement, "Response not found");
        errorAnnotation.registerFix(intentionAction);
      }
    }
  }

  private boolean isLocalReference(final PsiElement psiElement) {
    return StringUtils.removeAllQuotes(psiElement.getText()).startsWith("#/");
  }

  private Set<String> getAvailableDefinitions(final PsiElement psiElement) {
    return getAvailableNames(psiElement, "definitions");
  }

  private Set<String> getAvailableParameters(final PsiElement psiElement) {
    return getAvailableNames(psiElement, "parameters");
  }

  private Set<String> getAvailableResponses(final PsiElement psiElement) {
    return getAvailableNames(psiElement, "responses");
  }

  private Set<String> getAvailableNames(final PsiElement psiElement, final String refType) {
    String pathExpression = String.format("$.%s", refType);

    return new PathFinder()
        .findNamedChildren(pathExpression, psiElement.getContainingFile())
        .stream()
        .map(PsiNamedElement::getName)
        .collect(Collectors.toSet());
  }
}
