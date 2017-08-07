package org.zalando.intellij.swagger.annotator;

import com.intellij.lang.annotation.AnnotationHolder;
import com.intellij.lang.annotation.Annotator;
import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;
import org.zalando.intellij.swagger.file.FileDetector;
import org.zalando.intellij.swagger.intention.field.RemoveYamlFieldIntentionAction;
import org.zalando.intellij.swagger.traversal.YamlTraversal;
import org.zalando.intellij.swagger.traversal.path.swagger.MainPathResolver;
import org.zalando.intellij.swagger.validator.field.FieldsValidator;
import org.zalando.intellij.swagger.validator.field.UnknownYamlKeyValidator;

public class YamlValidKeyAnnotator implements Annotator {

    @Override
    public void annotate(@NotNull final PsiElement psiElement, @NotNull final AnnotationHolder annotationHolder) {

        if (new FileDetector().isMainSwaggerYamlFile(psiElement.getContainingFile())) {
            final YamlTraversal yamlTraversal = new YamlTraversal();

            final FieldsValidator fieldsValidator = new FieldsValidator(
                    yamlTraversal,
                    new MainPathResolver(),
                    new UnknownYamlKeyValidator(new RemoveYamlFieldIntentionAction(psiElement), yamlTraversal)
            );

            fieldsValidator.validate(psiElement, annotationHolder);
        }
    }
}
