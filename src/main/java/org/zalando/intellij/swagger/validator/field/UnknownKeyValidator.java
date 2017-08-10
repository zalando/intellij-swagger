package org.zalando.intellij.swagger.validator.field;

import com.intellij.codeInsight.intention.IntentionAction;
import com.intellij.lang.annotation.Annotation;
import com.intellij.lang.annotation.AnnotationHolder;
import com.intellij.psi.PsiElement;
import org.zalando.intellij.swagger.completion.field.model.common.Field;

import java.util.List;

abstract class UnknownKeyValidator {

    private final IntentionAction intentionAction;

    UnknownKeyValidator(final IntentionAction intentionAction) {
        this.intentionAction = intentionAction;
    }

    void validate(final String key,
                  final List<Field> availableKeys,
                  final PsiElement psiElement,
                  final AnnotationHolder annotationHolder) {
        if (shouldIgnore(key, psiElement)) {
            return;
        }

        if (isInvalid(key, availableKeys)) {
            final Annotation errorAnnotation = annotationHolder.createErrorAnnotation(psiElement, "Invalid key");
            errorAnnotation.registerFix(intentionAction);
        }
    }

    abstract boolean isInvalid(final String key, final List<Field> availableKeys);

    abstract boolean shouldIgnore(final String key, final PsiElement psiElement);
}
