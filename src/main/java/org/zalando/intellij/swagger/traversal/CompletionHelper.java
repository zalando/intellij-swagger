package org.zalando.intellij.swagger.traversal;

import com.intellij.codeInsight.completion.InsertHandler;
import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.psi.PsiElement;
import org.zalando.intellij.swagger.completion.field.model.Field;
import org.zalando.intellij.swagger.completion.value.model.Value;

import java.util.List;
import java.util.Optional;

public class CompletionHelper {

    private final PsiElement psiElement;
    private final Traversal traversal;

    public CompletionHelper(final PsiElement psiElement, final Traversal traversal) {
        this.psiElement = psiElement;
        this.traversal = traversal;
    }

    public boolean completeRootKey() {
        return traversal.childOfRoot(psiElement);
    }

    public boolean completeInfoKey() {
        return traversal.childOfInfo(psiElement);
    }

    public boolean completeContactKey() {
        return traversal.childOfContact(psiElement);
    }

    public boolean completeLicenseKey() {
        return traversal.childOfLicense(psiElement);
    }

    public boolean completePathKey() {
        return traversal.childOfPath(psiElement);
    }

    public boolean completeOperationKey() {
        return traversal.childOfOperation(psiElement);
    }

    public boolean completeExternalDocsKey() {
        return traversal.childOfExternalDocs(psiElement);
    }

    public boolean completeParametersKey() {
        return traversal.childOfParameters(psiElement);
    }

    public boolean completeItemsKey() {
        return traversal.childOfItems(psiElement);
    }

    public boolean completeResponsesKey() {
        return traversal.childOfResponses(psiElement);
    }

    public boolean completeResponseKey() {
        return traversal.childOfResponse(psiElement);
    }

    public boolean completeHeaderKey() {
        return traversal.childOfHeader(psiElement);
    }

    public boolean completeHeadersKey() {
        return traversal.childOfHeaders(psiElement);
    }

    public boolean completeTagKey() {
        return traversal.childOfTag(psiElement);
    }

    public boolean completeSecurityDefinitionKey() {
        return traversal.childOfSecurityDefinition(psiElement);
    }

    public boolean completeSchemaKey() {
        return traversal.childOfSchema(psiElement);
    }

    public boolean completeXmlKey() {
        return traversal.childOfXml(psiElement);
    }

    public boolean completeDefinitionsKey() {
        return traversal.childOfDefinitions(psiElement);
    }

    public boolean completeParameterDefinitionKey() {
        return traversal.childOfParameterDefinition(psiElement);
    }

    public boolean completeMimeValue() {
        return traversal.isMimeValue(psiElement);
    }

    public boolean completeSchemesValue() {
        return traversal.isSchemesValue(psiElement);
    }

    public boolean completeDefinitionRefValue() {
        return traversal.isDefinitionRefValue(psiElement);
    }

    public boolean completeParameterRefValue() {
        return traversal.isParameterRefValue(psiElement);
    }

    public List<PsiElement> getChildrenOfRoot(final String propertyName) {
        return traversal.getChildrenOf(propertyName, psiElement.getContainingFile());
    }

    public List<PsiElement> getChildrenOfArrayObject(final PsiElement psiElement) {
        return traversal.getChildrenOfArrayObject(psiElement);
    }

    public List<String> getKeyNamesOf(final String propertyName) {
        return traversal.getKeyNamesOf(propertyName, psiElement.getContainingFile());
    }

    public boolean isUniqueKey(final String keyName) {
        return traversal.isUniqueKey(keyName, psiElement);
    }

    public boolean isUniqueArrayStringValue(final String keyName) {
        return traversal.isUniqueArrayStringValue(keyName, psiElement);
    }

    public InsertHandler<LookupElement> createInsertFieldHandler(final Field field) {
        return traversal.createInsertFieldHandler(field);
    }

    public InsertHandler<LookupElement> createInsertValueHandler(final Value value) {
        return traversal.createInsertValueHandler(value);
    }

    public boolean completeBooleanValue() {
        return traversal.isBooleanValue(psiElement);
    }

    public boolean completeTypeValue() {
        return traversal.isTypeValue(psiElement);
    }

    public boolean completeFormatValue() {
        return traversal.isFormatValue(psiElement);
    }

    public boolean completeInValue() {
        return traversal.isInValue(psiElement);
    }

    public boolean completeResponseRefValue() {
        return traversal.isResponseRefValue(psiElement);
    }

    public boolean completeResponseDefinition() {
        return traversal.childOfResponseDefinition(psiElement);
    }

    public boolean completeRootSecurityKey() {
        return traversal.childOfRootSecurityKey(psiElement);
    }

    public boolean completeOperationSecurityKey() {
        return traversal.childOfOperationSecurityKey(psiElement);
    }

    public Optional<String> extractSecurityNameFromSecurityObject(final PsiElement psiElement) {
        return traversal.extractSecurityNameFromSecurityItem(psiElement);
    }

    public List<String> getSecurityScopesIfOAuth2(final PsiElement securityDefinitionItem) {
        return traversal.getSecurityScopesIfOAuth2(securityDefinitionItem);
    }

    public boolean completeSecurityScopeNameValue() {
        return traversal.isSecurityScopeNameValue(psiElement);
    }

    public Optional<String> getKeyNameOfObject(final PsiElement psiElement) {
        return traversal.getKeyNameOfObject(psiElement);
    }

    public Optional<String> getParentKeyName() {
        return traversal.getParentKeyName(psiElement);
    }

    public Optional<PsiElement> getParentByName(final String parentName) {
        return traversal.getParentByName(psiElement, parentName);
    }

    public boolean completeItemsCollectionFormat() {
        return traversal.childOfItemsCollectionFormat(psiElement);
    }

    public boolean completeParametersCollectionFormat() {
        return traversal.childOfParametersCollectionFormat(psiElement);
    }

    public boolean completeHeadersCollectionFormat() {
        return traversal.childOfHeadersCollectionFormat(psiElement);
    }
}
