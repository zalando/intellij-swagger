package org.zalando.intellij.swagger.reference.extractor;

import org.apache.commons.lang.StringUtils;

public class ValueExtractor {

    public String getDefinitionValue(final String definitionsText) {
        return getTextWithoutPrefix(definitionsText, "#/definitions/");
    }

    public String getParameterValue(final String parametersText) {
        return getTextWithoutPrefix(parametersText, "#/parameters/");
    }

    private String getTextWithoutPrefix(final String text, final String prefix) {
        return StringUtils.substringAfter(text, prefix)
                .replace("'", "")
                .replace("\"", "");
    }
}
