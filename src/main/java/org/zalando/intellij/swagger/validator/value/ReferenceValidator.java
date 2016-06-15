package org.zalando.intellij.swagger.validator.value;

import com.intellij.lang.annotation.AnnotationHolder;
import com.intellij.psi.PsiElement;

import java.util.Set;

public class ReferenceValidator {

    public void validateDefinitionReference(final String refValue,
                                            final Set<String> availableDefinitions,
                                            final PsiElement psiElement,
                                            final AnnotationHolder annotationHolder) {
        final boolean definitionFound = availableDefinitions.contains(refValue);

        if (!definitionFound) {
            annotationHolder.createErrorAnnotation(psiElement, "Definition not found");
        }
    }

    public void validateParameterReference(final String refValue,
                                           final Set<String> availableParameters,
                                           final PsiElement psiElement,
                                           final AnnotationHolder annotationHolder) {
        final boolean parameterFound = availableParameters.contains(refValue);

        if (!parameterFound) {
            annotationHolder.createErrorAnnotation(psiElement, "Parameter not found");
        }
    }

    public void validateResponseReference(final String refValue,
                                          final Set<String> availableResponses,
                                          final PsiElement psiElement,
                                          final AnnotationHolder annotationHolder) {
        final boolean responseFound = availableResponses.contains(refValue);

        if (!responseFound) {
            annotationHolder.createErrorAnnotation(psiElement, "Response not found");
        }
    }
}
