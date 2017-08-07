package org.zalando.intellij.swagger.validator.value;

import com.intellij.codeInsight.intention.IntentionAction;
import com.intellij.lang.annotation.Annotation;
import com.intellij.lang.annotation.AnnotationHolder;
import com.intellij.psi.PsiElement;
import org.zalando.intellij.swagger.StringUtils;
import org.zalando.intellij.swagger.reference.extractor.ReferenceValueExtractor;
import org.zalando.intellij.swagger.traversal.Traversal;

import java.util.Set;
import java.util.stream.Collectors;

public class ReferenceValidator {

    private final IntentionAction intentionAction;
    private final ReferenceValueExtractor referenceValueExtractor;
    private final Traversal traversal;

    public ReferenceValidator(final IntentionAction intentionAction,
                              final ReferenceValueExtractor referenceValueExtractor,
                              final Traversal traversal) {
        this.intentionAction = intentionAction;
        this.referenceValueExtractor = referenceValueExtractor;
        this.traversal = traversal;
    }

    void validateDefinitionReference(final PsiElement psiElement,
                                     final AnnotationHolder annotationHolder) {
        if (isLocalReference(psiElement)) {
            final boolean definitionFound =
                    getAvailableDefinitions(psiElement)
                            .contains(referenceValueExtractor.extractValue(psiElement.getText()));

            if (!definitionFound) {
                final Annotation errorAnnotation =
                        annotationHolder.createErrorAnnotation(psiElement, "Definition not found");
                errorAnnotation.registerFix(intentionAction);
            }
        }
    }

    void validateParameterReference(final PsiElement psiElement,
                                    final AnnotationHolder annotationHolder) {
        if (isLocalReference(psiElement)) {
            final boolean parameterFound =
                    getAvailableParameters(psiElement)
                            .contains(referenceValueExtractor.extractValue(psiElement.getText()));

            if (!parameterFound) {
                final Annotation errorAnnotation =
                        annotationHolder.createErrorAnnotation(psiElement, "Parameter not found");
                errorAnnotation.registerFix(intentionAction);
            }
        }
    }

    void validateResponseReference(final PsiElement psiElement,
                                   final AnnotationHolder annotationHolder) {
        if (isLocalReference(psiElement)) {
            final boolean responseFound =
                    getAvailableResponses(psiElement)
                            .contains(referenceValueExtractor.extractValue(psiElement.getText()));

            if (!responseFound) {
                final Annotation errorAnnotation = annotationHolder.createErrorAnnotation(psiElement, "Response not found");
                errorAnnotation.registerFix(intentionAction);
            }
        }
    }

    private boolean isLocalReference(final PsiElement psiElement) {
        return StringUtils.removeAllQuotes(psiElement.getText()).startsWith("#/");
    }

    private Set<String> getAvailableDefinitions(final PsiElement psiElement) {
        return traversal.getKeyNamesOfDefinition("definitions", psiElement.getContainingFile())
                .stream()
                .collect(Collectors.toSet());
    }

    private Set<String> getAvailableParameters(final PsiElement psiElement) {
        return traversal.getKeyNamesOfDefinition("parameters", psiElement.getContainingFile())
                .stream()
                .collect(Collectors.toSet());
    }

    private Set<String> getAvailableResponses(final PsiElement psiElement) {
        return traversal.getKeyNamesOfDefinition("responses", psiElement.getContainingFile())
                .stream()
                .collect(Collectors.toSet());
    }

}
