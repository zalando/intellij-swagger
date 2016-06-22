package org.zalando.intellij.swagger.annotator;

import com.intellij.lang.annotation.AnnotationHolder;
import com.intellij.lang.annotation.Annotator;
import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;
import org.zalando.intellij.swagger.file.FileDetector;
import org.zalando.intellij.swagger.validator.field.FieldsValidator;
import org.zalando.intellij.swagger.traversal.YamlTraversal;
import org.zalando.intellij.swagger.traversal.keydepth.YamlFieldValidationKeyDepth;

public class YamlValidKeyAnnotator implements Annotator {

    private final FieldsValidator fieldsValidator = new FieldsValidator(new YamlTraversal());

    @Override
    public void annotate(@NotNull final PsiElement psiElement, @NotNull final AnnotationHolder annotationHolder) {
        if (new FileDetector().isSwaggerYamlFile(psiElement.getContainingFile())) {
            fieldsValidator.validate(psiElement, annotationHolder);
        }
    }
}
