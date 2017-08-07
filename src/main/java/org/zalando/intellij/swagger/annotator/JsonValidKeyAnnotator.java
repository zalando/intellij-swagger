package org.zalando.intellij.swagger.annotator;

import com.intellij.lang.annotation.AnnotationHolder;
import com.intellij.lang.annotation.Annotator;
import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;
import org.zalando.intellij.swagger.file.FileDetector;
import org.zalando.intellij.swagger.intention.field.RemoveJsonFieldIntentionAction;
import org.zalando.intellij.swagger.traversal.JsonTraversal;
import org.zalando.intellij.swagger.traversal.path.swagger.MainPathResolver;
import org.zalando.intellij.swagger.validator.field.FieldsValidator;
import org.zalando.intellij.swagger.validator.field.UnknownJsonKeyValidator;

public class JsonValidKeyAnnotator implements Annotator {

    @Override
    public void annotate(@NotNull final PsiElement psiElement, @NotNull final AnnotationHolder annotationHolder) {

        if (new FileDetector().isMainSwaggerJsonFile(psiElement.getContainingFile())) {
            final FieldsValidator fieldsValidator = new FieldsValidator(new JsonTraversal(),
                    new MainPathResolver(),
                    new UnknownJsonKeyValidator(new RemoveJsonFieldIntentionAction(psiElement)));

            fieldsValidator.validate(psiElement, annotationHolder);
        }
    }
}
