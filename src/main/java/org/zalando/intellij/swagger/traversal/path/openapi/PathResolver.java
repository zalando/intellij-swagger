package org.zalando.intellij.swagger.traversal.path.openapi;

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

    default boolean childOfExternalDocs(PsiElement psiElement) {
        return false;
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
        return false;
    }

    default boolean childOfAdditionalProperties(PsiElement psiElement) {
        return false;
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

    default boolean isSchemaRefValue(PsiElement psiElement) {
        return false;
    }

    default boolean isParameterRefValue(PsiElement psiElement) {
        return false;
    }

    default boolean isResponseRefValue(PsiElement psiElement) {
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

    default boolean childOfServer(PsiElement psiElement) {
        return false;
    }

    default boolean childOfServerVariable(PsiElement psiElement) {
        return false;
    }

    default boolean childOfComponent(PsiElement psiElement) {
        return false;
    }

    default boolean childOfRequestBody(PsiElement psiElement) {
        return false;
    }

    default boolean childOfMediaType(PsiElement psiElement) {
        return false;
    }

    default boolean childOfExample(PsiElement psiElement) {
        return false;
    }

    default boolean childOfEncoding(PsiElement psiElement) {
        return false;
    }

    default boolean childOfLink(PsiElement psiElement) {
        return false;
    }

    default boolean childOfCallback(PsiElement psiElement) {
        return false;
    }

    default boolean childOfSecurityScheme(PsiElement psiElement) {
        return false;
    }

    default boolean childOfContent(PsiElement psiElement) {
        return false;
    }

    default boolean isExampleRefValue(PsiElement psiElement) {
        return false;
    }

    default boolean isRequestBodyRefValue(PsiElement psiElement) {
        return false;
    }

    default boolean isHeaderRefValue(PsiElement psiElement) {
        return false;
    }

    default boolean isLinkRefValue(PsiElement psiElement) {
        return false;
    }

    default boolean isCallbackRefValue(PsiElement psiElement) {
        return false;
    }

    default boolean isBooleanValue(PsiElement psiElement) {
        return false;
    }

    default boolean isTypeValue(PsiElement psiElement) {
        return false;
    }

    default boolean isInValue(PsiElement psiElement) {
        return false;
    }

    default boolean isFormatValue(PsiElement psiElement) {
        return false;
    }

    default boolean isStyleValue(PsiElement psiElement) {
        return false;
    }

    default boolean hasPath(final PsiElement psiElement, final String pathExpression) {
        return new PathFinder().isInsidePath(psiElement, pathExpression);
    }
}
