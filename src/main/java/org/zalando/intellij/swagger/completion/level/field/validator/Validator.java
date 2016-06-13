package org.zalando.intellij.swagger.completion.level.field.validator;

import com.intellij.lang.annotation.AnnotationHolder;
import com.intellij.psi.PsiElement;
import org.zalando.intellij.swagger.completion.level.field.Field;

import java.util.List;

public interface Validator {

    void validate(final String key, final List<Field> availableKeys, final PsiElement psiElement, final AnnotationHolder annotationHolder);
}
