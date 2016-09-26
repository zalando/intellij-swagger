package org.zalando.intellij.swagger.traversal;

import com.intellij.psi.PsiElement;

public interface PathResolver {

    boolean childOfRoot(PsiElement psiElement);

    boolean childOfInfo(PsiElement psiElement);

    boolean childOfContact(PsiElement psiElement);

    boolean childOfLicense(PsiElement psiElement);

    boolean childOfPath(PsiElement psiElement);

    boolean childOfOperation(PsiElement psiElement);

    boolean childOfExternalDocs(PsiElement psiElement);

    boolean childOfParameters(PsiElement psiElement);

    boolean childOfItems(PsiElement psiElement);

    boolean childOfResponses(PsiElement psiElement);

    boolean childOfResponse(PsiElement psiElement);

    boolean childOfResponseDefinition(PsiElement psiElement);

    boolean childOfHeader(PsiElement psiElement);

    boolean childOfHeaders(PsiElement psiElement);

    boolean childOfTag(PsiElement psiElement);

    boolean childOfSecurityDefinition(PsiElement psiElement);

    boolean childOfSchema(PsiElement psiElement);

    boolean childOfXml(PsiElement psiElement);

    boolean childOfDefinitions(PsiElement psiElement);

    boolean childOfParameterDefinition(PsiElement psiElement);

    boolean isMimeValue(PsiElement psiElement);

    boolean isSchemesValue(PsiElement psiElement);

    boolean isTagsValue(PsiElement psiElement);

    boolean isDefinitionRefValue(PsiElement psiElement);

    boolean isParameterRefValue(PsiElement psiElement);

    boolean isResponseRefValue(PsiElement psiElement);

    boolean childOfRootSecurityKey(PsiElement psiElement);

    boolean childOfOperationSecurityKey(PsiElement psiElement);

    boolean isSecurityScopeNameValue(PsiElement psiElement);

    boolean childOfItemsCollectionFormat(PsiElement psiElement);

    boolean childOfParametersCollectionFormat(PsiElement psiElement);

    boolean childOfHeadersCollectionFormat(PsiElement psiElement);

    default boolean hasPath(final PsiElement psiElement, final String pathExpression) {
        return new Path(psiElement, pathExpression).exists();
    }
}
