package org.zalando.intellij.swagger.traversal;

import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;

/*
 * This class is used for partial Swagger specification files (referenced via $ref)
 */
public class DefinitionPathResolver implements PathResolver {

    @Override
    public boolean childOfRoot(final PsiElement psiElement) {
        return false;
    }

    @Override
    public final boolean childOfInfo(final PsiElement psiElement) {
        return false;
    }

    @Override
    public final boolean childOfContact(final PsiElement psiElement) {
        return false;
    }

    @Override
    public final boolean childOfLicense(final PsiElement psiElement) {
        return false;
    }

    @Override
    public final boolean childOfPath(final PsiElement psiElement) {
        return false;
    }

    @Override
    public final boolean childOfOperation(final PsiElement psiElement) {
        return false;
    }

    @Override
    public final boolean childOfExternalDocs(final PsiElement psiElement) {
        return false;
    }

    @Override
    public final boolean childOfParameters(final PsiElement psiElement) {
        return false;
    }

    @Override
    public final boolean childOfItems(final PsiElement psiElement) {
        return false;
    }

    @Override
    public final boolean childOfResponses(final PsiElement psiElement) {
        return false;
    }

    @Override
    public final boolean childOfResponse(final PsiElement psiElement) {
        return false;
    }

    @Override
    public final boolean childOfResponseDefinition(final PsiElement psiElement) {
        return false;
    }

    @Override
    public final boolean childOfHeader(final PsiElement psiElement) {
        return false;
    }

    @Override
    public final boolean childOfHeaders(final PsiElement psiElement) {
        return false;
    }

    @Override
    public final boolean childOfTag(final PsiElement psiElement) {
        return false;
    }

    @Override
    public final boolean childOfSecurityDefinition(final PsiElement psiElement) {
        return false;
    }

    @Override
    public final boolean childOfSchema(final PsiElement psiElement) {
        return false;
    }

    @Override
    public final boolean childOfXml(final PsiElement psiElement) {
        return false;
    }

    @Override
    public final boolean childOfDefinitions(final PsiElement psiElement) {
        return psiElement.getParent() instanceof PsiFile ||
                psiElement.getParent().getParent() instanceof PsiFile ||
                psiElement.getParent().getParent().getParent() instanceof PsiFile ||
                psiElement.getParent().getParent().getParent().getParent() instanceof PsiFile;
    }

    @Override
    public final boolean childOfParameterDefinition(final PsiElement psiElement) {
        return false;
    }

    @Override
    public final boolean isMimeValue(final PsiElement psiElement) {
        return false;
    }

    @Override
    public final boolean isSchemesValue(final PsiElement psiElement) {
        return false;
    }

    @Override
    public final boolean isTagsValue(final PsiElement psiElement) {
        return false;
    }

    @Override
    public final boolean isDefinitionRefValue(final PsiElement psiElement) {
        return false;
    }

    @Override
    public final boolean isParameterRefValue(final PsiElement psiElement) {
        return false;
    }

    @Override
    public final boolean isResponseRefValue(final PsiElement psiElement) {
        return false;
    }

    @Override
    public final boolean childOfRootSecurityKey(final PsiElement psiElement) {
        return false;
    }

    @Override
    public final boolean childOfOperationSecurityKey(final PsiElement psiElement) {
        return false;
    }

    @Override
    public boolean isSecurityScopeNameValue(final PsiElement psiElement) {
        return false;
    }

    @Override
    public boolean childOfItemsCollectionFormat(final PsiElement psiElement) {
        return false;
    }

    @Override
    public boolean childOfParametersCollectionFormat(final PsiElement psiElement) {
        return false;
    }

    @Override
    public boolean childOfHeadersCollectionFormat(final PsiElement psiElement) {
        return false;
    }
}
