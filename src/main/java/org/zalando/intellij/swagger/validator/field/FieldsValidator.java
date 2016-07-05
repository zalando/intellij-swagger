package org.zalando.intellij.swagger.validator.field;

import com.intellij.lang.annotation.AnnotationHolder;
import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;
import org.zalando.intellij.swagger.completion.field.model.Fields;
import org.zalando.intellij.swagger.traversal.Traversal;

public class FieldsValidator {

    private final Traversal traversal;
    private final UnknownKeyValidator unknownKeyValidator;

    public FieldsValidator(final Traversal traversal, final UnknownKeyValidator unknownKeyValidator) {
        this.traversal = traversal;
        this.unknownKeyValidator = unknownKeyValidator;
    }

    public void validate(@NotNull PsiElement psiElement, @NotNull final AnnotationHolder annotationHolder) {
        traversal.getKeyNameIfKey(psiElement).ifPresent(keyName -> {

            if (traversal.childOfRoot(psiElement)) {
                unknownKeyValidator.validate(keyName, Fields.root(), psiElement, annotationHolder);
                return;
            }

            final PsiElement keyObject = traversal.extractObjectForValidation(psiElement);

            if (traversal.childOfContact(keyObject)) {
                unknownKeyValidator.validate(keyName, Fields.contact(), psiElement, annotationHolder);
            } else if (traversal.childOfSchema(keyObject)) {
                unknownKeyValidator.validate(keyName, Fields.schema(), psiElement, annotationHolder);
            } else if (traversal.childOfExternalDocs(keyObject)) {
                unknownKeyValidator.validate(keyName, Fields.externalDocs(), psiElement, annotationHolder);
            } else if (traversal.childOfHeader(keyObject)) {
                unknownKeyValidator.validate(keyName, Fields.header(), psiElement, annotationHolder);
            } else if (traversal.childOfInfo(keyObject)) {
                unknownKeyValidator.validate(keyName, Fields.info(), psiElement, annotationHolder);
            } else if (traversal.childOfItems(keyObject)) {
                unknownKeyValidator.validate(keyName, Fields.items(), psiElement, annotationHolder);
            } else if (traversal.childOfLicense(keyObject)) {
                unknownKeyValidator.validate(keyName, Fields.license(), psiElement, annotationHolder);
            } else if (traversal.childOfOperation(keyObject)) {
                unknownKeyValidator.validate(keyName, Fields.operation(), psiElement, annotationHolder);
            } else if (traversal.childOfParameters(keyObject)) {
                unknownKeyValidator.validate(keyName, Fields.parametersWithRef(), psiElement, annotationHolder);
            } else if (traversal.childOfPath(keyObject)) {
                unknownKeyValidator.validate(keyName, Fields.path(), psiElement, annotationHolder);
            } else if (traversal.childOfResponse(keyObject)) {
                unknownKeyValidator.validate(keyName, Fields.response(), psiElement, annotationHolder);
            } else if (traversal.childOfResponses(keyObject)) {
                unknownKeyValidator.validate(keyName, Fields.responses(), psiElement, annotationHolder);
            } else if (traversal.childOfSecurityDefinition(keyObject)) {
                unknownKeyValidator.validate(keyName, Fields.securityDefinitions(), psiElement, annotationHolder);
            } else if (traversal.childOfTag(keyObject)) {
                unknownKeyValidator.validate(keyName, Fields.tags(), psiElement, annotationHolder);
            } else if (traversal.childOfXml(keyObject)) {
                unknownKeyValidator.validate(keyName, Fields.xml(), psiElement, annotationHolder);
            }
        });
    }
}
