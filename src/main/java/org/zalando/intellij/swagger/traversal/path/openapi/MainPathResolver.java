package org.zalando.intellij.swagger.traversal.path.openapi;

import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;

public class MainPathResolver implements PathResolver {

    @Override
    public boolean childOfRoot(final PsiElement psiElement) {
        return psiElement.getParent() instanceof PsiFile ||
                psiElement.getParent().getParent() instanceof PsiFile ||
                psiElement.getParent().getParent().getParent() instanceof PsiFile ||
                psiElement.getParent().getParent().getParent().getParent() instanceof PsiFile;
    }

    @Override
    public final boolean childOfInfo(final PsiElement psiElement) {
        return hasPath(psiElement, "$.info");
    }

    @Override
    public final boolean childOfContact(final PsiElement psiElement) {
        return hasPath(psiElement, "$.info.contact");
    }

    @Override
    public final boolean childOfLicense(final PsiElement psiElement) {
        return hasPath(psiElement, "$.info.license");
    }

    @Override
    public final boolean childOfPath(final PsiElement psiElement) {
        return hasPath(psiElement, "$.paths.*");
    }

    @Override
    public final boolean childOfOperation(final PsiElement psiElement) {
        return hasPath(psiElement, "$.paths.*.*") && !childOfParameters(psiElement);
    }

    @Override
    public final boolean childOfExternalDocs(final PsiElement psiElement) {
        return hasPath(psiElement, "$.**.externalDocs");
    }

    @Override
    public final boolean childOfParameters(final PsiElement psiElement) {
        return hasPath(psiElement, "$.paths.*.*.parameters") ||
                hasPath(psiElement, "$.paths.*.parameters") ||
                hasPath(psiElement, "$.components.parameters.*");
    }

    @Override
    public final boolean childOfParameterItems(final PsiElement psiElement) {
        return hasPath(psiElement, "$.paths.*.*.parameters.items") ||
                hasPath(psiElement, "$.paths.*.*.responses.*.headers.*.items") ||
                hasPath(psiElement, "$.paths.*.parameters.items");
    }

    @Override
    public final boolean childOfResponses(final PsiElement psiElement) {
        return hasPath(psiElement, "$.paths.*.*.responses");
    }

    @Override
    public final boolean childOfResponse(final PsiElement psiElement) {
        return hasPath(psiElement, "$.paths.*.*.responses.*") ||
                hasPath(psiElement, "$.components.responses.*");
    }

    @Override
    public final boolean childOfResponseDefinition(final PsiElement psiElement) {
        return hasPath(psiElement, "$.responses.*");
    }

    @Override
    public final boolean childOfHeader(final PsiElement psiElement) {
        return hasPath(psiElement, "$.paths.*.*.responses.*.headers.*") ||
                hasPath(psiElement, "$.components.headers.*");
    }

    @Override
    public final boolean childOfHeaders(final PsiElement psiElement) {
        return hasPath(psiElement, "$.paths.*.*.responses.*.headers");
    }

    @Override
    public final boolean childOfTag(final PsiElement psiElement) {
        return hasPath(psiElement, "$.tags");
    }

    @Override
    public final boolean childOfSecurityDefinition(final PsiElement psiElement) {
        return hasPath(psiElement, "$.securityDefinitions.*");
    }

    @Override
    public final boolean childOfSchema(final PsiElement psiElement) {
        return hasPath(psiElement, "$.**.schema") ||
                hasPath(psiElement, "$.components.schemas.*");
    }

    @Override
    public final boolean childOfSchemaItems(final PsiElement psiElement) {
        return hasPath(psiElement, "$.**.schema.items");
    }

    @Override
    public final boolean childOfPropertiesSchema(final PsiElement psiElement) {
        return hasPath(psiElement, "$.**.properties.*");
    }

    @Override
    public final boolean childOfAdditionalProperties(PsiElement psiElement) {
        return hasPath(psiElement, "$.**.additionalProperties");
    }

    @Override
    public final boolean childOfXml(final PsiElement psiElement) {
        return hasPath(psiElement, "$.**.schema.xml");
    }

    @Override
    public final boolean childOfDefinitions(final PsiElement psiElement) {
        return hasPath(psiElement, "$.definitions.*");
    }

    @Override
    public final boolean childOfParameterDefinition(final PsiElement psiElement) {
        return hasPath(psiElement, "$.parameters.*");
    }

    @Override
    public final boolean isMimeValue(final PsiElement psiElement) {
        return hasPath(psiElement, "$.consumes") ||
                hasPath(psiElement, "$.produces") ||
                hasPath(psiElement, "$.paths.*.*.consumes") ||
                hasPath(psiElement, "$.paths.*.*.produces");

    }

    @Override
    public final boolean isSchemesValue(final PsiElement psiElement) {
        return hasPath(psiElement, "$.schemes");
    }

    @Override
    public final boolean isTagsValue(final PsiElement psiElement) {
        return hasPath(psiElement, "$.paths.*.*.tags");
    }

    @Override
    public final boolean isSchemaRefValue(final PsiElement psiElement) {
        return hasPath(psiElement, "$.**.schema.$ref") ||
                hasPath(psiElement, "$.**.items.$ref");
    }

    @Override
    public final boolean isParameterRefValue(final PsiElement psiElement) {
        return hasPath(psiElement, "$.**.parameters.$ref");
    }

    @Override
    public final boolean isResponseRefValue(final PsiElement psiElement) {
        return hasPath(psiElement, "$.**.responses.*.$ref");
    }

    @Override
    public final boolean isExampleRefValue(final PsiElement psiElement) {
        return hasPath(psiElement, "$.**.examples.*.$ref");
    }

    @Override
    public final boolean isRequestBodyRefValue(final PsiElement psiElement) {
        return hasPath(psiElement, "$.**.requestBody.$ref");
    }

    @Override
    public final boolean isHeaderRefValue(final PsiElement psiElement) {
        return hasPath(psiElement, "$.**.headers.*.$ref");
    }

    @Override
    public final boolean isLinkRefValue(final PsiElement psiElement) {
        return hasPath(psiElement, "$.**.links.*.$ref");
    }

    @Override
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

    @Override
    public final boolean isTypeValue(final PsiElement psiElement) {
        return hasPath(psiElement, "$.**.type");
    }

    @Override
    public final boolean isInValue(final PsiElement psiElement) {
        return hasPath(psiElement, "$.**.in");
    }

    @Override
    public final boolean isFormatValue(final PsiElement psiElement) {
        return hasPath(psiElement, "$.**.format");
    }

    @Override
    public final boolean isStyleValue(final PsiElement psiElement) {
        return hasPath(psiElement, "$.**.style");
    }

    @Override
    public final boolean isCallbackRefValue(final PsiElement psiElement) {
        return hasPath(psiElement, "$.**.callbacks.*.$ref");
    }

    @Override
    public final boolean childOfRootSecurityKey(final PsiElement psiElement) {
        return hasPath(psiElement, "$.security");
    }

    @Override
    public final boolean childOfOperationSecurityKey(final PsiElement psiElement) {
        return hasPath(psiElement, "$.paths.*.*.security");
    }

    @Override
    public boolean isSecurityScopeNameValue(final PsiElement psiElement) {
        return hasPath(psiElement, "$.security.*") ||
                hasPath(psiElement, "$.paths.*.*.security.*");
    }

    @Override
    public boolean childOfItemsCollectionFormat(final PsiElement psiElement) {
        return hasPath(psiElement, "$.**.items.collectionFormat");
    }

    @Override
    public boolean childOfParametersCollectionFormat(final PsiElement psiElement) {
        return hasPath(psiElement, "$.paths.*.*.parameters.collectionFormat");
    }

    @Override
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
