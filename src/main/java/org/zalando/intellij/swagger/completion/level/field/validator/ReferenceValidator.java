package org.zalando.intellij.swagger.completion.level.field.validator;

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
}
