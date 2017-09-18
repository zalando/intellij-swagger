package org.zalando.intellij.swagger.traversal.path.openapi;

import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;

public class MainPathResolver implements PathResolver {

    public boolean childOfRoot(final PsiElement psiElement) {
        return psiElement.getParent() instanceof PsiFile ||
                psiElement.getParent().getParent() instanceof PsiFile ||
                psiElement.getParent().getParent().getParent() instanceof PsiFile ||
                psiElement.getParent().getParent().getParent().getParent() instanceof PsiFile;
    }

    public final boolean childOfInfo(final PsiElement psiElement) {
        return hasPath(psiElement, "$.info");
    }

    public final boolean childOfContact(final PsiElement psiElement) {
        return hasPath(psiElement, "$.info.contact");
    }

    public final boolean childOfLicense(final PsiElement psiElement) {
        return hasPath(psiElement, "$.info.license");
    }

    public final boolean childOfPath(final PsiElement psiElement) {
        return hasPath(psiElement, "$.paths.*");
    }

    public final boolean childOfOperation(final PsiElement psiElement) {
        return hasPath(psiElement, "$.paths.*.*") && !childOfParameters(psiElement);
    }

    public final boolean childOfExternalDocs(final PsiElement psiElement) {
        return hasPath(psiElement, "$.**.externalDocs");
    }

    public final boolean childOfParameters(final PsiElement psiElement) {
        return hasPath(psiElement, "$.paths.*.*.parameters") ||
                hasPath(psiElement, "$.paths.*.parameters") ||
                hasPath(psiElement, "$.components.parameters.*");
    }

    public final boolean childOfParameterItems(final PsiElement psiElement) {
        return hasPath(psiElement, "$.paths.*.*.parameters.items") ||
                hasPath(psiElement, "$.paths.*.*.responses.*.headers.*.items") ||
                hasPath(psiElement, "$.paths.*.parameters.items");
    }

    public final boolean childOfResponses(final PsiElement psiElement) {
        return hasPath(psiElement, "$.paths.*.*.responses");
    }

    public final boolean childOfResponse(final PsiElement psiElement) {
        return hasPath(psiElement, "$.paths.*.*.responses.*") ||
                hasPath(psiElement, "$.components.responses.*");
    }

    public final boolean childOfResponseDefinition(final PsiElement psiElement) {
        return hasPath(psiElement, "$.responses.*");
    }

    public final boolean childOfHeader(final PsiElement psiElement) {
        return hasPath(psiElement, "$.paths.*.*.responses.*.headers.*") ||
                hasPath(psiElement, "$.components.headers.*");
    }

    public final boolean childOfHeaders(final PsiElement psiElement) {
        return hasPath(psiElement, "$.paths.*.*.responses.*.headers");
    }

    public final boolean childOfTag(final PsiElement psiElement) {
        return hasPath(psiElement, "$.tags");
    }

    public final boolean childOfSecurityDefinition(final PsiElement psiElement) {
        return hasPath(psiElement, "$.securityDefinitions.*");
    }

    public final boolean childOfSchema(final PsiElement psiElement) {
        return hasPath(psiElement, "$.**.schema") ||
                hasPath(psiElement, "$.components.schemas.*");
    }

    public final boolean childOfSchemaItems(final PsiElement psiElement) {
        return hasPath(psiElement, "$.**.schema.items");
    }

    public final boolean childOfPropertiesSchema(final PsiElement psiElement) {
        return hasPath(psiElement, "$.**.properties.*");
    }

    public final boolean childOfAdditionalProperties(PsiElement psiElement) {
        return hasPath(psiElement, "$.**.additionalProperties");
    }

    public final boolean childOfXml(final PsiElement psiElement) {
        return hasPath(psiElement, "$.**.schema.xml");
    }

    public final boolean childOfDefinitions(final PsiElement psiElement) {
        return hasPath(psiElement, "$.definitions.*");
    }

    public final boolean childOfParameterDefinition(final PsiElement psiElement) {
        return hasPath(psiElement, "$.parameters.*");
    }

    public final boolean isMimeValue(final PsiElement psiElement) {
        return hasPath(psiElement, "$.consumes") ||
                hasPath(psiElement, "$.produces") ||
                hasPath(psiElement, "$.paths.*.*.consumes") ||
                hasPath(psiElement, "$.paths.*.*.produces");

    }

    public final boolean isSchemesValue(final PsiElement psiElement) {
        return hasPath(psiElement, "$.schemes");
    }

    public final boolean isTagsValue(final PsiElement psiElement) {
        return hasPath(psiElement, "$.paths.*.*.tags");
    }

    public final boolean isSchemaRefValue(final PsiElement psiElement) {
        return hasPath(psiElement, "$.**.schema.$ref") ||
                hasPath(psiElement, "$.**.items.$ref");
    }

    public final boolean isParameterRefValue(final PsiElement psiElement) {
        return hasPath(psiElement, "$.**.parameters.$ref");
    }

    public final boolean isResponseRefValue(final PsiElement psiElement) {
        return hasPath(psiElement, "$.**.responses.*.$ref");
    }

    public final boolean isExampleRefValue(final PsiElement psiElement) {
        return hasPath(psiElement, "$.**.examples.*.$ref");
    }

    public final boolean isRequestBodyRefValue(final PsiElement psiElement) {
        return hasPath(psiElement, "$.**.requestBody.$ref");
    }

    public final boolean isHeaderRefValue(final PsiElement psiElement) {
        return hasPath(psiElement, "$.**.headers.*.$ref");
    }

    public final boolean isLinkRefValue(final PsiElement psiElement) {
        return hasPath(psiElement, "$.**.links.*.$ref");
    }

    public final boolean isBooleanValue(final PsiElement psiElement) {
        return hasPath(psiElement, "$.**.deprecated") ||
                hasPath(psiElement, "$.**.explode") ||
                hasPath(psiElement, "$.**.allowReserved") ||
                hasPath(psiElement, "$.**.required") ||
                hasPath(psiElement, "$.**.nullable") ||
                hasPath(psiElement, "$.**.readOnly") ||
                hasPath(psiElement, "$.**.writeOnly") ||
                hasPath(psiElement, "$.**.attribute") ||
                hasPath(psiElement, "$.**.wrapped") ||
                hasPath(psiElement, "$.**.exclusiveMaximum") ||
                hasPath(psiElement, "$.**.exclusiveMinimum") ||
                hasPath(psiElement, "$.**.uniqueItems");
    }

    public final boolean isTypeValue(final PsiElement psiElement) {
        return hasPath(psiElement, "$.**.type");
    }

    public final boolean isInValue(final PsiElement psiElement) {
        return hasPath(psiElement, "$.**.in");
    }

    public final boolean isFormatValue(final PsiElement psiElement) {
        return hasPath(psiElement, "$.**.format");
    }

    public final boolean isStyleValue(final PsiElement psiElement) {
        return hasPath(psiElement, "$.**.style");
    }

    public final boolean isCallbackRefValue(final PsiElement psiElement) {
        return hasPath(psiElement, "$.**.callbacks.*.$ref");
    }

    public final boolean childOfRootSecurityKey(final PsiElement psiElement) {
        return hasPath(psiElement, "$.security");
    }

    public final boolean childOfOperationSecurityKey(final PsiElement psiElement) {
        return hasPath(psiElement, "$.paths.*.*.security");
    }

    public boolean isSecurityScopeNameValue(final PsiElement psiElement) {
        return hasPath(psiElement, "$.security.*") ||
                hasPath(psiElement, "$.paths.*.*.security.*");
    }

    public boolean childOfItemsCollectionFormat(final PsiElement psiElement) {
        return hasPath(psiElement, "$.**.items.collectionFormat");
    }

    public boolean childOfParametersCollectionFormat(final PsiElement psiElement) {
        return hasPath(psiElement, "$.paths.*.*.parameters.collectionFormat");
    }

    public boolean childOfHeadersCollectionFormat(final PsiElement psiElement) {
        return hasPath(psiElement, "$.paths.*.*.responses.*.headers.*.collectionFormat");
    }

    @Override
    public boolean childOfServer(PsiElement psiElement) {
        return hasPath(psiElement, "$.**.servers") ||
                hasPath(psiElement, "$.**.server");
    }

    @Override
    public boolean childOfServerVariable(PsiElement psiElement) {
        return hasPath(psiElement, "$.servers.variables.*");
    }

    @Override
    public boolean childOfComponent(PsiElement psiElement) {
        return hasPath(psiElement, "$.components");
    }

    @Override
    public boolean childOfRequestBody(PsiElement psiElement) {
        return hasPath(psiElement, "$.paths.*.*.requestBody") ||
                hasPath(psiElement, "$.components.requestBodies.*");
    }

    @Override
    public boolean childOfMediaType(PsiElement psiElement) {
        return hasPath(psiElement, "$.paths.*.*.requestBody.content.*") ||
                hasPath(psiElement, "$.paths.*.*.responses.*.content.*");
    }

    @Override
    public boolean childOfExample(PsiElement psiElement) {
        return hasPath(psiElement, "$.**.examples.*");
    }

    @Override
    public boolean childOfEncoding(PsiElement psiElement) {
        return hasPath(psiElement, "$.**.encoding.*");
    }

    @Override
    public boolean childOfLink(PsiElement psiElement) {
        return hasPath(psiElement, "$.paths.*.*.responses.*.links.*") ||
                hasPath(psiElement, "$.components.links.*");
    }

    @Override
    public boolean childOfCallback(PsiElement psiElement) {
        return hasPath(psiElement, "$.paths.*.*.callbacks.*") ||
                hasPath(psiElement, "$.components.callbacks.*.*");
    }

    @Override
    public boolean childOfSecurityScheme(PsiElement psiElement) {
        return hasPath(psiElement, "$.components.securitySchemes.*");
    }

    @Override
    public boolean childOfContent(PsiElement psiElement) {
        return hasPath(psiElement, "$.**.content");
    }
}
