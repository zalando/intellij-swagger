package org.zalando.intellij.swagger.reference.extractor;


import org.zalando.intellij.swagger.completion.StringUtils;

public class ReferenceValueExtractor {

    private static final String DEFINITIONS_PREFIX = "#/definitions/";
    private static final String PARAMETERS_PREFIX = "#/parameters/";
    private static final String RESPONSES_PREFIX = "#/responses/";

    public String getValue(final String referenceValue) {
        final String withoutQuotes = StringUtils.removeAllQuotes(referenceValue);

        if (isParameter(withoutQuotes)) {
            return getTextWithoutPrefix(withoutQuotes, PARAMETERS_PREFIX);
        } else if (isDefinition(withoutQuotes)) {
            return getTextWithoutPrefix(withoutQuotes, DEFINITIONS_PREFIX);
        } else if (isResponse(withoutQuotes)) {
            return getTextWithoutPrefix(withoutQuotes, RESPONSES_PREFIX);
        }
        return "";
    }

    public String getReferenceType(final String referenceValue) {
        if (isParameter(referenceValue)) {
            return "parameters";
        } else if (isDefinition(referenceValue)) {
            return "definitions";
        } else if (isResponse(referenceValue)) {
            return "responses";
        }
        return "";
    }

    private String getTextWithoutPrefix(final String text, final String prefix) {
        return StringUtils.removeAllQuotes(org.apache.commons.lang.StringUtils.substringAfter(text, prefix));
    }

    private boolean isDefinition(final String referenceValue) {
        return referenceValue.startsWith(DEFINITIONS_PREFIX);
    }

    private boolean isParameter(final String referenceValue) {
        return referenceValue.startsWith(PARAMETERS_PREFIX);
    }

    private boolean isResponse(final String referenceValue) {
        return referenceValue.startsWith(RESPONSES_PREFIX);
    }

}
