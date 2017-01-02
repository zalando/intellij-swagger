package org.zalando.intellij.swagger.reference.extractor;


import org.apache.commons.lang.StringUtils;
import org.zalando.intellij.swagger.completion.SwaggerStringUtils;

import static org.zalando.intellij.swagger.reference.SwaggerConstants.REFERENCE_PREFIX;
import static org.zalando.intellij.swagger.reference.SwaggerConstants.SLASH;

public class ReferenceValueExtractor {

    public static String extractValue(String refValue) {
        refValue = SwaggerStringUtils.removeAllQuotes(refValue);
        return StringUtils.substringAfterLast(refValue, SLASH);
    }

    public static String extractType(String refValue) {
        refValue = SwaggerStringUtils.removeAllQuotes(refValue);
        return StringUtils.substringBetween(refValue, REFERENCE_PREFIX, SLASH);
    }

    public static String extractFilePath(String refValue) {
        refValue = SwaggerStringUtils.removeAllQuotes(refValue);
        return StringUtils.substringBefore(refValue, REFERENCE_PREFIX);
    }
}
