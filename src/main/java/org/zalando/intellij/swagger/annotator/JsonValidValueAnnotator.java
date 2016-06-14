package org.zalando.intellij.swagger.annotator;

import com.intellij.lang.annotation.AnnotationHolder;
import com.intellij.lang.annotation.Annotator;
import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;
import org.zalando.intellij.swagger.completion.level.field.validator.FieldsValidator;
import org.zalando.intellij.swagger.completion.level.field.validator.ReferenceValidator;
import org.zalando.intellij.swagger.completion.level.field.validator.ValuesValidator;
import org.zalando.intellij.swagger.completion.traversal.JsonTraversal;
import org.zalando.intellij.swagger.completion.traversal.YamlTraversal;
import org.zalando.intellij.swagger.completion.traversal.keydepth.JsonCompletionKeyDepth;
import org.zalando.intellij.swagger.completion.traversal.keydepth.YamlFieldValidationKeyDepth;

public class JsonValidValueAnnotator implements Annotator {

    private final ValuesValidator valuesValidator = new ValuesValidator(new JsonTraversal(new JsonCompletionKeyDepth()));

    @Override
    public void annotate(@NotNull final PsiElement psiElement, @NotNull final AnnotationHolder annotationHolder) {
        valuesValidator.validate(psiElement, annotationHolder);
    }
}
