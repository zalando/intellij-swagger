package org.zalando.intellij.swagger.reference.extractor;

import org.junit.Test;
import org.zalando.intellij.swagger.reference.ReferenceValueExtractor;

import static org.junit.Assert.assertEquals;

public class ReferenceValueExtractorTest {

    @Test
    public void thatDefinitionValueWithSingleQuotesIsExtracted() throws Exception {
        final String definitionValue = ReferenceValueExtractor.extractValue("'#/definitions/Value'");

        assertEquals("Value", definitionValue);
    }

    @Test
    public void thatDefinitionValueWithDoubleQuotesIsExtracted() throws Exception {
        final String definitionValue = ReferenceValueExtractor.extractValue("\"#/definitions/Value\"");

        assertEquals("Value", definitionValue);
    }

}
