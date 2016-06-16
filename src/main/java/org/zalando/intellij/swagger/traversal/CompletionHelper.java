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
        return traversal.isRoot(psiElement);
    }

    public boolean completeInfoKey() {
        return traversal.isInfo(psiElement);
    }

    public boolean completeContactKey() {
        return traversal.isContact(psiElement);
    }

    public boolean completeLicenseKey() {
        return traversal.isLicense(psiElement);
    }

    public boolean completePathKey() {
        return traversal.isPath(psiElement);
    }

    public boolean completeOperationKey() {
        return traversal.isOperation(psiElement);
    }

    public boolean completeExternalDocsKey() {
        return traversal.isExternalDocs(psiElement);
    }

    public boolean completeParametersKey() {
        return traversal.isParameters(psiElement);
    }

    public boolean completeItemsKey() {
        return traversal.isItems(psiElement);
    }

    public boolean completeResponsesKey() {
        return traversal.isResponses(psiElement);
    }

    public boolean completeResponseKey() {
        return traversal.isResponse(psiElement);
    }

    public boolean completeHeaderKey() {
        return traversal.isHeader(psiElement);
    }

    public boolean completeHeadersKey() {
        return traversal.isHeaders(psiElement);
    }

    public boolean completeTagKey() {
        return traversal.isTag(psiElement);
    }

    public boolean completeSecurityDefinitionKey() {
        return traversal.isSecurityDefinition(psiElement);
    }

    public boolean completeSchemaKey() {
        return traversal.isSchema(psiElement);
    }

    public boolean completeXmlKey() {
        return traversal.isXml(psiElement);
    }

    public boolean completeDefinitionsKey() {
        return traversal.isDefinitions(psiElement);
    }

    public boolean completeParameterDefinitionKey() {
        return traversal.isParameterDefinition(psiElement);
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

    public List<PsiElement> getChildrenOf(final String propertyName) {
        return traversal.getChildrenOf(propertyName, psiElement.getContainingFile());
    }

    public List<String> getKeyNamesOf(final String propertyName) {
        return traversal.getKeyNamesOf(propertyName, psiElement.getContainingFile());
    }

    public boolean isUniqueKey(final String keyName) {
        return traversal.isUniqueKey(keyName, psiElement);
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

    public boolean completeInValue() {
        return traversal.isInValue(psiElement);
    }

    public boolean completeResponseRefValue() {
        return traversal.isResponseRefValue(psiElement);
    }

    public boolean completeResponseDefinition() {
        return traversal.isResponseDefinition(psiElement);
    }

    public boolean completeSecurityKey() {
        return traversal.isSecurityKey(psiElement);
    }

    public Optional<String> extractSecurityNameFromSecurityObject(final PsiElement psiElement) {
        return traversal.extractSecurityNameFromSecurityItem(psiElement);
    }
}
