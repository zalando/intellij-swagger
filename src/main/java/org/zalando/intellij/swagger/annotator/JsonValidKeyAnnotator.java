package org.zalando.intellij.swagger.annotator;

import com.intellij.lang.annotation.AnnotationHolder;
import com.intellij.lang.annotation.Annotator;
import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;
import org.zalando.intellij.swagger.completion.level.field.FieldsValidator;
import org.zalando.intellij.swagger.completion.traversal.keydepth.JsonCompletionKeyDepth;
import org.zalando.intellij.swagger.completion.traversal.JsonTraversal;

public class JsonValidKeyAnnotator implements Annotator {

    private final FieldsValidator fieldsValidator = new FieldsValidator(new JsonTraversal(new JsonCompletionKeyDepth()));

    @Override
    public void annotate(@NotNull final PsiElement psiElement, @NotNull final AnnotationHolder annotationHolder) {
        fieldsValidator.validate(psiElement, annotationHolder);
    }
}
