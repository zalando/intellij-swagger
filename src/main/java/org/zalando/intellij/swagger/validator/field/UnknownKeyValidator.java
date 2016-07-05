package org.zalando.intellij.swagger.validator.field;

import com.intellij.codeInsight.intention.IntentionAction;
import com.intellij.lang.annotation.Annotation;
import com.intellij.lang.annotation.AnnotationHolder;
import com.intellij.psi.PsiElement;
import org.zalando.intellij.swagger.completion.field.model.Field;

import java.util.List;

public class UnknownKeyValidator {

    private static final String VENDOR_EXTENSION_PREFIX = "x-";

    private final IntentionAction intentionAction;

    public UnknownKeyValidator(final IntentionAction intentionAction) {
        this.intentionAction = intentionAction;
    }

    void validate(final String key,
                         final List<Field> availableKeys,
                         final PsiElement psiElement,
                         final AnnotationHolder annotationHolder) {
        boolean keyFoundInAvailableKeys = availableKeys.stream().anyMatch(field -> field.getName().equals(key));
        if (!keyFoundInAvailableKeys && !key.startsWith(VENDOR_EXTENSION_PREFIX)) {
            final Annotation errorAnnotation = annotationHolder.createErrorAnnotation(psiElement, "Invalid key");
            errorAnnotation.registerFix(intentionAction);
        }
    }
}
