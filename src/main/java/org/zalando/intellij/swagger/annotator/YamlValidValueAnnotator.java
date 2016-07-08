package org.zalando.intellij.swagger.annotator;

import com.intellij.lang.annotation.AnnotationHolder;
import com.intellij.lang.annotation.Annotator;
import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;
import org.zalando.intellij.swagger.completion.StringUtils;
import org.zalando.intellij.swagger.file.FileDetector;
import org.zalando.intellij.swagger.intention.reference.CreateYamlReferenceIntentionAction;
import org.zalando.intellij.swagger.reference.extractor.ReferenceValueExtractor;
import org.zalando.intellij.swagger.traversal.PathResolver;
import org.zalando.intellij.swagger.traversal.YamlTraversal;
import org.zalando.intellij.swagger.validator.value.ReferenceValidator;
import org.zalando.intellij.swagger.validator.value.SchemesValidator;
import org.zalando.intellij.swagger.validator.value.ValuesValidator;

public class YamlValidValueAnnotator implements Annotator {

    @Override
    public void annotate(@NotNull final PsiElement psiElement, @NotNull final AnnotationHolder annotationHolder) {
        if (new FileDetector().isSwaggerYamlFile(psiElement.getContainingFile())) {
            final ValuesValidator valuesValidator = new ValuesValidator(new YamlTraversal(),
                    new PathResolver(), new ReferenceValidator(
                            new CreateYamlReferenceIntentionAction(
                                    StringUtils.removeAllQuotes(psiElement.getText()),
                                    new ReferenceValueExtractor()),
                            new ReferenceValueExtractor(),
                            new YamlTraversal()),
                    new SchemesValidator());

            valuesValidator.validate(psiElement, annotationHolder);
        }
    }
}
