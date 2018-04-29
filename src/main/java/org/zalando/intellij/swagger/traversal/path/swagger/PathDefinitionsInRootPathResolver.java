package org.zalando.intellij.swagger.traversal.path.swagger;

import com.intellij.psi.PsiElement;

public class PathDefinitionsInRootPathResolver implements PathResolver {

    public final boolean childOfPath(final PsiElement psiElement) {
        return hasPath(psiElement, "$.*");
    }

    public final boolean childOfOperation(final PsiElement psiElement) {
        return hasPath(psiElement, "$.*.*") && !childOfParameters(psiElement);
    }

    public final boolean childOfParameters(final PsiElement psiElement) {
        return hasPath(psiElement, "$.*.*.parameters");
    }

    public final boolean childOfParameterItems(final PsiElement psiElement) {
        return hasPath(psiElement, "$.*.*.parameters.items") ||
                hasPath(psiElement, "$.*.*.responses.*.headers.*.items") ||
                hasPath(psiElement, "$.parameters.items");
    }

    public final boolean childOfResponses(final PsiElement psiElement) {
        return hasPath(psiElement, "$.*.*.responses");
    }

    public final boolean childOfResponse(final PsiElement psiElement) {
        return hasPath(psiElement, "$.*.*.responses.*");
    }

    public final boolean childOfHeader(final PsiElement psiElement) {
        return hasPath(psiElement, "$.*.*.responses.*.headers.*");
    }

    public final boolean childOfHeaders(final PsiElement psiElement) {
        return hasPath(psiElement, "$.*.*.responses.*.headers");
    }

    public final boolean childOfSchema(final PsiElement psiElement) {
        return hasPath(psiElement, "$.**.schema");
    }

    public final boolean childOfSchemaItems(final PsiElement psiElement) {
        return hasPath(psiElement, "$.**.schema.items");
    }

    public final boolean childOfXml(final PsiElement psiElement) {
        return hasPath(psiElement, "$.**.schema.xml");
    }

    public final boolean isMimeValue(final PsiElement psiElement) {
        return hasPath(psiElement, "$.*.*.consumes") ||
                hasPath(psiElement, "$.*.*.produces");

    }

    public final boolean isTagsValue(final PsiElement psiElement) {
        return hasPath(psiElement, "$.*.*.tags");
    }

    public final boolean isDefinitionRefValue(final PsiElement psiElement) {
        return hasPath(psiElement, "$.**.schema.$ref") ||
                hasPath(psiElement, "$.**.items.$ref");
    }

    public final boolean isParameterRefValue(final PsiElement psiElement) {
        return hasPath(psiElement, "$.**.parameters.$ref");
    }

    public final boolean isResponseRefValue(final PsiElement psiElement) {
        return hasPath(psiElement, "$.*.*.responses.*.$ref");
    }

    public final boolean isPathRefValue(final PsiElement psiElement) {
        return hasPath(psiElement, "$.$ref");
    }

    public final boolean childOfOperationSecurityKey(final PsiElement psiElement) {
        return hasPath(psiElement, "$.*.*.security");
    }

    public boolean isSecurityScopeNameValue(final PsiElement psiElement) {
        return hasPath(psiElement, "$.*.*.security.*");
    }

    public boolean childOfItemsCollectionFormat(final PsiElement psiElement) {
        return hasPath(psiElement, "$.**.items.collectionFormat");
    }

    public boolean childOfParametersCollectionFormat(final PsiElement psiElement) {
        return hasPath(psiElement, "$.*.*.parameters.collectionFormat");
    }

    public boolean childOfHeadersCollectionFormat(final PsiElement psiElement) {
        return hasPath(psiElement, "$.*.*.responses.*.headers.*.collectionFormat");
    }

}
