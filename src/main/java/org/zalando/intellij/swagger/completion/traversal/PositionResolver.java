package org.zalando.intellij.swagger.completion.traversal;

import com.intellij.json.JsonElementTypes;
import com.intellij.psi.PsiElement;
import org.zalando.intellij.swagger.completion.traversal.Traversal;

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
        return !psiElement.toString().contains(JsonElementTypes.DOUBLE_QUOTED_STRING.toString());
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
}
