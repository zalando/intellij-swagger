package org.zalando.intellij.swagger.annotator;

import com.intellij.lang.annotation.AnnotationHolder;
import com.intellij.lang.annotation.Annotator;
import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;
import org.zalando.intellij.swagger.file.FileDetector;
import org.zalando.intellij.swagger.intention.field.RemoveYamlFieldIntentionAction;
import org.zalando.intellij.swagger.traversal.PathResolver;
import org.zalando.intellij.swagger.traversal.YamlTraversal;
import org.zalando.intellij.swagger.validator.field.FieldsValidator;
import org.zalando.intellij.swagger.validator.field.UnknownKeyValidator;

public class YamlValidKeyAnnotator implements Annotator {

    @Override
    public void annotate(@NotNull final PsiElement psiElement, @NotNull final AnnotationHolder annotationHolder) {

        if (new FileDetector().isSwaggerYamlFile(psiElement.getContainingFile())) {
            final FieldsValidator fieldsValidator = new FieldsValidator(
                    new YamlTraversal(),
                    new PathResolver(),
                    new UnknownKeyValidator(
                            new RemoveYamlFieldIntentionAction(psiElement)));

            fieldsValidator.validate(psiElement, annotationHolder);
        }
    }
}
