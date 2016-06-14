package org.zalando.intellij.swagger.annotator;

import com.intellij.lang.annotation.AnnotationHolder;
import com.intellij.lang.annotation.Annotator;
import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;
import org.zalando.intellij.swagger.file.FileDetector;
import org.zalando.intellij.swagger.validator.value.ValuesValidator;
import org.zalando.intellij.swagger.traversal.JsonTraversal;
import org.zalando.intellij.swagger.traversal.keydepth.JsonCompletionKeyDepth;

public class JsonValidValueAnnotator implements Annotator {

    private final ValuesValidator valuesValidator = new ValuesValidator(new JsonTraversal(new JsonCompletionKeyDepth()));

    @Override
    public void annotate(@NotNull final PsiElement psiElement, @NotNull final AnnotationHolder annotationHolder) {
        if (new FileDetector().isSwaggerJsonFile(psiElement.getContainingFile())) {
            valuesValidator.validate(psiElement, annotationHolder);
        }
    }
}
