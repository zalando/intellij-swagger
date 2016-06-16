package org.zalando.intellij.swagger.annotator;

import com.intellij.lang.annotation.AnnotationHolder;
import com.intellij.lang.annotation.Annotator;
import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;
import org.zalando.intellij.swagger.file.FileDetector;
import org.zalando.intellij.swagger.traversal.YamlTraversal;
import org.zalando.intellij.swagger.traversal.keydepth.YamlCaretAtFieldKeyDepth;
import org.zalando.intellij.swagger.validator.value.ValuesValidator;

public class YamlValidValueAnnotator implements Annotator {

    private final ValuesValidator valuesValidator = new ValuesValidator(new YamlTraversal(new YamlCaretAtFieldKeyDepth()));

    @Override
    public void annotate(@NotNull final PsiElement psiElement, @NotNull final AnnotationHolder annotationHolder) {
        if (new FileDetector().isSwaggerYamlFile(psiElement.getContainingFile())) {
            valuesValidator.validate(psiElement, annotationHolder);
        }
    }
}
