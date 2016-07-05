package org.zalando.intellij.swagger.reference.extractor;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class ReferenceValueExtractorTest {

    private final ReferenceValueExtractor referenceValueExtractor = new ReferenceValueExtractor();

    @Test
    public void thatDefinitionValueWithSingleQuotesIsExtracted() throws Exception {
        final String definitionValue = referenceValueExtractor.getValue("'#/definitions/Value'");

        assertEquals("Value", definitionValue);
    }

    @Test
    public void thatDefinitionValueWithDoubleQuotesIsExtracted() throws Exception {
        final String definitionValue = referenceValueExtractor.getValue("\"#/definitions/Value\"");

        assertEquals("Value", definitionValue);
    }

}
