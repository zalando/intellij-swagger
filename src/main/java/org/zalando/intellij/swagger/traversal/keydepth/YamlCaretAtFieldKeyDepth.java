package org.zalando.intellij.swagger.traversal.keydepth;

public class YamlCaretAtFieldKeyDepth implements KeyDepth {

    @Override
    public int getInfoNth() {
        return 1;
    }

    @Override
    public int getContactNth() {
        return 1;
    }

    @Override
    public int getLicenseNth() {
        return 1;
    }

    @Override
    public int getPathNth() {
        return 2;
    }

    @Override
    public int getOperationNth() {
        return 3;
    }

    @Override
    public int getExternalDocsNth() {
        return 1;
    }

    @Override
    public int getParametersNth() {
        return 1;
    }

    @Override
    public int getItemsNth() {
        return 1;
    }

    @Override
    public int getResponsesNth() {
        return 1;
    }

    @Override
    public int getResponseNth() {
        return 2;
    }

    @Override
    public int getHeaderNth() {
        return 2;
    }

    @Override
    public int getHeadersNth() {
        return 1;
    }

    @Override
    public int getTagsNth() {
        return 1;
    }

    @Override
    public int getSecurityDefinitionsNth() {
        return 2;
    }

    @Override
    public int getSchemaNth() {
        return 1;
    }

    @Override
    public int getXmlNth() {
        return 1;
    }

    @Override
    public int getDefinitionsNth() {
        return 2;
    }

    @Override
    public int getParameterDefinitionNth() {
        return 2;
    }

    @Override
    public int getMimeNth() {
        return 1;
    }

    @Override
    public int getSchemesNth() {
        return 1;
    }

    @Override
    public int getSecurityNth() {
        return 1;
    }

    @Override
    public int getSecurityValueNth() {
        return 2;
    }

    @Override
    public int itemsCollectionFormatNth() {
        return 2;
    }

    @Override
    public int parametersCollectionFormatNth() {
        return 2;
    }

    @Override
    public int headersCollectionFormatNth() {
        return 3;
    }
}
