package org.zalando.intellij.swagger.validator.value;

import com.intellij.lang.annotation.AnnotationHolder;
import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;
import org.zalando.intellij.swagger.traversal.path.swagger.MainPathResolver;
import org.zalando.intellij.swagger.traversal.Traversal;

public class ValuesValidator {

    private final Traversal traversal;
    private final MainPathResolver pathResolver;
    private final ReferenceValidator referenceValidator;
    private final SchemesValidator schemesValidator;

    public ValuesValidator(final Traversal traversal,
                           final MainPathResolver pathResolver,
                           final ReferenceValidator referenceValidator,
                           final SchemesValidator schemesValidator) {
        this.traversal = traversal;
        this.pathResolver = pathResolver;
        this.referenceValidator = referenceValidator;
        this.schemesValidator = schemesValidator;
    }

    public void validate(@NotNull final PsiElement psiElement, @NotNull final AnnotationHolder annotationHolder) {
        if (traversal.isValue(psiElement)) {
            handleKeyValue(psiElement, annotationHolder);
        } else if (traversal.isArrayStringElement(psiElement)) {
            handleArrayStringValue(psiElement, annotationHolder);
        }
    }

    private void handleKeyValue(final @NotNull PsiElement psiElement, final @NotNull AnnotationHolder annotationHolder) {
        if (pathResolver.isDefinitionRefValue(psiElement)) {
            referenceValidator.validateDefinitionReference(psiElement, annotationHolder);
        } else if (pathResolver.isParameterRefValue(psiElement)) {
            referenceValidator.validateParameterReference(psiElement, annotationHolder);
        } else if (pathResolver.isResponseRefValue(psiElement)) {
            referenceValidator.validateResponseReference(psiElement, annotationHolder);
        }
    }

    private void handleArrayStringValue(final @NotNull PsiElement psiElement, final @NotNull AnnotationHolder annotationHolder) {
        if (pathResolver.isSchemesValue(psiElement)) {
            schemesValidator.validate(psiElement, annotationHolder);
        }
    }
}
