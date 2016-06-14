package org.zalando.intellij.swagger.annotator;

import com.intellij.lang.annotation.AnnotationHolder;
import com.intellij.lang.annotation.Annotator;
import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;
import org.zalando.intellij.swagger.validator.field.ValuesValidator;
import org.zalando.intellij.swagger.traversal.YamlTraversal;
import org.zalando.intellij.swagger.traversal.keydepth.YamlFieldValidationKeyDepth;

public class YamlValidValueAnnotator implements Annotator {

    private final ValuesValidator valuesValidator = new ValuesValidator(new YamlTraversal(new YamlFieldValidationKeyDepth()));

    @Override
    public void annotate(@NotNull final PsiElement psiElement, @NotNull final AnnotationHolder annotationHolder) {
        valuesValidator.validate(psiElement, annotationHolder);
    }
}
