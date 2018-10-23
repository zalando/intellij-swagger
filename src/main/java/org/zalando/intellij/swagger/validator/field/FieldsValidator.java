package org.zalando.intellij.swagger.validator.field;

import com.intellij.lang.annotation.AnnotationHolder;
import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;
import org.zalando.intellij.swagger.completion.field.model.common.CommonFields;
import org.zalando.intellij.swagger.completion.field.model.swagger.SwaggerFields;
import org.zalando.intellij.swagger.traversal.Traversal;
import org.zalando.intellij.swagger.traversal.path.swagger.MainPathResolver;

public class FieldsValidator {

  private final Traversal traversal;
  private final MainPathResolver pathResolver;
  private final UnknownKeyValidator unknownKeyValidator;

  public FieldsValidator(
      final Traversal traversal,
      final MainPathResolver pathResolver,
      final UnknownKeyValidator unknownKeyValidator) {
    this.traversal = traversal;
    this.pathResolver = pathResolver;
    this.unknownKeyValidator = unknownKeyValidator;
  }

  public void validate(
      @NotNull PsiElement psiElement, @NotNull final AnnotationHolder annotationHolder) {
    traversal
        .getKeyNameIfKey(psiElement)
        .ifPresent(
            keyName -> {
              if (pathResolver.childOfRoot(psiElement)) {
                unknownKeyValidator.validate(
                    keyName, SwaggerFields.root(), psiElement, annotationHolder);
                return;
              }

              final PsiElement keyObject = traversal.extractObjectForValidation(psiElement);

              if (pathResolver.childOfContact(keyObject)) {
                unknownKeyValidator.validate(
                    keyName, CommonFields.contact(), psiElement, annotationHolder);
              } else if (pathResolver.childOfSchema(keyObject)) {
                unknownKeyValidator.validate(
                    keyName, SwaggerFields.schema(), psiElement, annotationHolder);
              } else if (pathResolver.childOfSchemaItems(keyObject)) {
                unknownKeyValidator.validate(
                    keyName, SwaggerFields.schemaItems(), psiElement, annotationHolder);
              } else if (pathResolver.childOfExternalDocs(keyObject)) {
                unknownKeyValidator.validate(
                    keyName, CommonFields.externalDocs(), psiElement, annotationHolder);
              } else if (pathResolver.childOfHeader(keyObject)) {
                unknownKeyValidator.validate(
                    keyName, SwaggerFields.header(), psiElement, annotationHolder);
              } else if (pathResolver.childOfInfo(keyObject)) {
                unknownKeyValidator.validate(
                    keyName, CommonFields.info(), psiElement, annotationHolder);
              } else if (pathResolver.childOfParameterItems(keyObject)) {
                unknownKeyValidator.validate(
                    keyName, SwaggerFields.parameterItems(), psiElement, annotationHolder);
              } else if (pathResolver.childOfLicense(keyObject)) {
                unknownKeyValidator.validate(
                    keyName, CommonFields.license(), psiElement, annotationHolder);
              } else if (pathResolver.childOfOperation(keyObject)) {
                unknownKeyValidator.validate(
                    keyName, SwaggerFields.operation(), psiElement, annotationHolder);
              } else if (pathResolver.childOfParameters(keyObject)) {
                unknownKeyValidator.validate(
                    keyName, SwaggerFields.parametersWithRef(), psiElement, annotationHolder);
              } else if (pathResolver.childOfPath(keyObject)) {
                unknownKeyValidator.validate(
                    keyName, SwaggerFields.path(), psiElement, annotationHolder);
              } else if (pathResolver.childOfResponse(keyObject)) {
                unknownKeyValidator.validate(
                    keyName, SwaggerFields.response(), psiElement, annotationHolder);
              } else if (pathResolver.childOfResponses(keyObject)) {
                unknownKeyValidator.validate(
                    keyName, CommonFields.responses(), psiElement, annotationHolder);
              } else if (pathResolver.childOfSecurityDefinition(keyObject)) {
                unknownKeyValidator.validate(
                    keyName, SwaggerFields.securityDefinitions(), psiElement, annotationHolder);
              } else if (pathResolver.childOfTag(keyObject)) {
                unknownKeyValidator.validate(
                    keyName, CommonFields.tag(), psiElement, annotationHolder);
              } else if (pathResolver.childOfXml(keyObject)) {
                unknownKeyValidator.validate(
                    keyName, SwaggerFields.xml(), psiElement, annotationHolder);
              }
            });
  }
}
