package org.zalando.intellij.swagger.traversal.keydepth;

public class JsonCaretAtFieldKeyDepth implements KeyDepth {

    @Override
    public int getInfoNth() {
        return 2;
    }

    @Override
    public int getContactNth() {
        return 2;
    }

    @Override
    public int getLicenseNth() {
        return 2;
    }

    @Override
    public int getPathNth() {
        return 3;
    }

    @Override
    public int getOperationNth() {
        return 4;
    }

    @Override
    public int getExternalDocsNth() {
        return 2;
    }

    @Override
    public int getParametersNth() {
        return 2;
    }

    @Override
    public int getItemsNth() {
        return 2;
    }

    @Override
    public int getResponsesNth() {
        return 2;
    }

    @Override
    public int getResponseNth() {
        return 3;
    }

    @Override
    public int getHeadersNth() {
        return 2;
    }

    @Override
    public int getHeaderNth() {
        return 3;
    }

    @Override
    public int getTagsNth() {
        return 2;
    }

    @Override
    public int getSecurityDefinitionsNth() {
        return 3;
    }

    @Override
    public int getSchemaNth() {
        return 2;
    }

    @Override
    public int getXmlNth() {
        return 2;
    }

    @Override
    public int getDefinitionsNth() {
        return 3;
    }

    @Override
    public int getParameterDefinitionNth() {
        return 3;
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
        return 2;
    }
}
