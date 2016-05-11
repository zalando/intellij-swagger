package org.zalando.intellij.swagger.completion;

import com.intellij.json.JsonElementTypes;
import com.intellij.psi.PsiElement;
import org.zalando.intellij.swagger.completion.traversal.Traversal;

class PositionResolver {

    private final PsiElement psiElement;
    private final Traversal traversal;

    PositionResolver(final PsiElement psiElement, final Traversal traversal) {
        this.psiElement = psiElement;
        this.traversal = traversal;
    }

    boolean completeRootKey() {
        return traversal.isRoot(psiElement);
    }

    boolean shouldQuote() {
        return !psiElement.toString().contains(JsonElementTypes.DOUBLE_QUOTED_STRING.toString());
    }

    boolean completeInfoKey() {
        return traversal.isInfo(psiElement);
    }

    boolean completeContactKey() {
        return traversal.isContact(psiElement);
    }

    boolean completeLicenseKey() {
        return traversal.isLicense(psiElement);
    }

    boolean completePathKey() {
        return traversal.isPath(psiElement);
    }

    boolean completeOperationKey() {
        return traversal.isOperation(psiElement);
    }

    boolean completeExternalDocsKey() {
        return traversal.isExternalDocs(psiElement);
    }

    boolean completeParametersKey() {
        return traversal.isParameters(psiElement);
    }

    boolean completeItemsKey() {
        return traversal.isItems(psiElement);
    }

    boolean completeResponsesKey() {
        return traversal.isResponses(psiElement);
    }

    boolean completeResponseKey() {
        return traversal.isResponse(psiElement);
    }

    boolean completeHeaderKey() {
        return traversal.isHeader(psiElement);
    }

    boolean completeTagKey() {
        return traversal.isTag(psiElement);
    }

    boolean completeSecurityDefinitionKey() {
        return traversal.isSecurityDefinition(psiElement);
    }

    boolean completeSchemaKey() {
        return traversal.isSchema(psiElement);
    }

    boolean completeXmlKey() {
        return traversal.isXml(psiElement);
    }

    boolean completeDefinitionsKey() {
        return traversal.isDefinitions(psiElement);
    }

    boolean completeParameterDefinitionKey() {
        return traversal.isParameterDefinition(psiElement);
    }
}
