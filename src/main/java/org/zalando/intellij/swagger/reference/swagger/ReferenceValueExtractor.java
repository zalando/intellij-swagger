package org.zalando.intellij.swagger.reference.swagger;


import org.zalando.intellij.swagger.StringUtils;

import static org.zalando.intellij.swagger.reference.swagger.SwaggerConstants.REFERENCE_PREFIX;
import static org.zalando.intellij.swagger.reference.swagger.SwaggerConstants.SLASH;

public class ReferenceValueExtractor {

    public static String extractValue(String refValue) {
        refValue = StringUtils.removeAllQuotes(refValue);
        return org.apache.commons.lang.StringUtils.substringAfterLast(refValue, SLASH);
    }

    public static String extractType(String refValue) {
        refValue = StringUtils.removeAllQuotes(refValue);
        return org.apache.commons.lang.StringUtils.substringBetween(refValue, REFERENCE_PREFIX, SLASH);
    }

    public static String extractFilePath(String refValue) {
        refValue = StringUtils.removeAllQuotes(refValue);
        return org.apache.commons.lang.StringUtils.substringBefore(refValue, REFERENCE_PREFIX);
    }
}
