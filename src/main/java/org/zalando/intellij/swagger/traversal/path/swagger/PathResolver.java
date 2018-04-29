package org.zalando.intellij.swagger.traversal.path.swagger;

import com.intellij.psi.PsiElement;
import org.zalando.intellij.swagger.traversal.path.PathFinder;

public interface PathResolver {

    default boolean childOfRoot(PsiElement psiElement) {
        return false;
    }

    default boolean childOfInfo(PsiElement psiElement) {
        return false;
    }

    default boolean childOfContact(PsiElement psiElement) {
        return false;
    }

    default boolean childOfLicense(PsiElement psiElement) {
        return false;
    }

    default boolean childOfPath(PsiElement psiElement) {
        return false;
    }

    default boolean childOfOperation(PsiElement psiElement) {
        return false;
    }

    default boolean childOfExternalDocs(final PsiElement psiElement) {
        return hasPath(psiElement, "$.**.externalDocs");
    }


    default boolean childOfParameters(PsiElement psiElement) {
        return false;
    }

    default boolean childOfParameterItems(PsiElement psiElement) {
        return false;
    }

    default boolean childOfResponses(PsiElement psiElement) {
        return false;
    }

    default boolean childOfResponse(PsiElement psiElement) {
        return false;
    }

    default boolean childOfResponseDefinition(PsiElement psiElement) {
        return false;
    }

    default boolean childOfHeader(PsiElement psiElement) {
        return false;
    }

    default boolean childOfHeaders(PsiElement psiElement) {
        return false;
    }

    default boolean childOfTag(PsiElement psiElement) {
        return false;
    }

    default boolean childOfSecurityDefinition(PsiElement psiElement) {
        return false;
    }

    default boolean childOfSchema(PsiElement psiElement) {
        return false;
    }

    default boolean childOfSchemaItems(PsiElement psiElement) {
        return false;
    }

    default boolean childOfPropertiesSchema(PsiElement psiElement) {
        return hasPath(psiElement, "$.**.properties.*");
    }

    default boolean childOfAdditionalProperties(PsiElement psiElement) {
        return hasPath(psiElement, "$.**.additionalProperties");
    }

    default boolean childOfXml(PsiElement psiElement) {
        return false;
    }

    default boolean childOfDefinitions(PsiElement psiElement) {
        return false;
    }

    default boolean childOfParameterDefinition(PsiElement psiElement) {
        return false;
    }

    default boolean isMimeValue(PsiElement psiElement) {
        return false;
    }

    default boolean isSchemesValue(PsiElement psiElement) {
        return false;
    }

    default boolean isTagsValue(PsiElement psiElement) {
        return false;
    }

    default boolean isDefinitionRefValue(PsiElement psiElement) {
        return false;
    }

    default boolean isParameterRefValue(PsiElement psiElement) {
        return false;
    }

    default boolean isResponseRefValue(PsiElement psiElement) {
        return false;
    }

    default boolean isPathRefValue(PsiElement psiElement) {
        return false;
    }

    default boolean childOfRootSecurityKey(PsiElement psiElement) {
        return false;
    }

    default boolean childOfOperationSecurityKey(PsiElement psiElement) {
        return false;
    }

    default boolean isSecurityScopeNameValue(PsiElement psiElement) {
        return false;
    }

    default boolean childOfItemsCollectionFormat(PsiElement psiElement) {
        return false;
    }

    default boolean childOfParametersCollectionFormat(PsiElement psiElement) {
        return false;
    }

    default boolean childOfHeadersCollectionFormat(PsiElement psiElement) {
        return false;
    }

    default boolean isFormatValue(final PsiElement psiElement) {
        return hasPath(psiElement, "$.**.format");
    }

    default boolean isTypeValue(final PsiElement psiElement) {
        return hasPath(psiElement, "$.**.type");
    }

    default boolean isInValue(final PsiElement psiElement) {
        return hasPath(psiElement, "$.**.in");
    }

    default boolean isBooleanValue(final PsiElement psiElement) {
        return hasPath(psiElement, "$.**.deprecated") ||
                hasPath(psiElement, "$.**.required") ||
                hasPath(psiElement, "$.**.allowEmptyValue") ||
                hasPath(psiElement, "$.**.exclusiveMaximum") ||
                hasPath(psiElement, "$.**.exclusiveMinimum") ||
                hasPath(psiElement, "$.**.uniqueItems") ||
                hasPath(psiElement, "$.**.readOnly") ||
                hasPath(psiElement, "$.**.attribute") ||
                hasPath(psiElement, "$.**.wrapped");
    }


    default boolean hasPath(final PsiElement psiElement, final String pathExpression) {
        return new PathFinder().isInsidePath(psiElement, pathExpression);
    }
}
