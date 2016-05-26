package org.zalando.intellij.swagger.completion.traversal;

import com.intellij.psi.PsiElement;
import org.zalando.intellij.swagger.completion.style.CompletionStyle;

import java.util.List;

public class PositionResolver {

    private final PsiElement psiElement;
    private final Traversal traversal;

    public PositionResolver(final PsiElement psiElement, final Traversal traversal) {
        this.psiElement = psiElement;
        this.traversal = traversal;
    }

    public boolean completeRootKey() {
        return traversal.isRoot(psiElement);
    }

    public boolean shouldQuote() {
        return traversal.shouldQuote(psiElement);
    }

    public CompletionStyle.QuoteStyle getQuoteStyle() {
        return traversal.getQuoteStyle();
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

    public boolean completeRefValue() {
        return traversal.isRefValue(psiElement);
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
}
