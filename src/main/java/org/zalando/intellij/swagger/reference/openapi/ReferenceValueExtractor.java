package org.zalando.intellij.swagger.reference.openapi;


import org.zalando.intellij.swagger.StringUtils;

import static org.zalando.intellij.swagger.reference.swagger.OpenApiConstants.COMPONENT_REFERENCE_PREFIX;
import static org.zalando.intellij.swagger.reference.swagger.OpenApiConstants.SLASH;

public class ReferenceValueExtractor {

    public static String extractValue(String refValue) {
        refValue = StringUtils.removeAllQuotes(refValue);
        return org.apache.commons.lang.StringUtils.substringAfterLast(refValue, SLASH);
    }

    public static String extractType(String refValue) {
        refValue = StringUtils.removeAllQuotes(refValue);
        return org.apache.commons.lang.StringUtils.substringBetween(refValue, COMPONENT_REFERENCE_PREFIX, SLASH);
    }

    public static String extractFilePath(String refValue) {
        refValue = StringUtils.removeAllQuotes(refValue);
        return org.apache.commons.lang.StringUtils.substringBefore(refValue, COMPONENT_REFERENCE_PREFIX);
    }
}
