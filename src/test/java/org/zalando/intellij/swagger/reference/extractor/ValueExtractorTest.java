package org.zalando.intellij.swagger.reference.extractor;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ValueExtractorTest {

    private final ValueExtractor valueExtractor = new ValueExtractor();

    @Test
    public void thatDefinitionValueWithSingleQuotesIsExtracted() throws Exception {
        final String definitionValue = valueExtractor.getDefinitionValue("'#/definitions/Value'");

        assertEquals("Value", definitionValue);
    }

    @Test
    public void thatDefinitionValueWithDoubleQuotesIsExtracted() throws Exception {
        final String definitionValue = valueExtractor.getDefinitionValue("\"#/definitions/Value\"");

        assertEquals("Value", definitionValue);
    }

}
