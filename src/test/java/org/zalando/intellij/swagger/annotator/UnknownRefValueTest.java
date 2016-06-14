package org.zalando.intellij.swagger.annotator;

import org.zalando.intellij.swagger.SwaggerLightCodeInsightFixtureTestCase;

public class UnknownRefValueTest extends SwaggerLightCodeInsightFixtureTestCase {

    private void doTest(final String fileName) {
        myFixture.testHighlighting(true, false, false, "annotator/" + fileName);
    }

    public void testUnknownDefinitionRef() {
        doTest("unknown_definition_ref.json");
        doTest("unknown_definition_ref.yaml");
    }

    public void testUnknownParameterRef() {
        doTest("unknown_parameter_ref.json");
        doTest("unknown_parameter_ref.yaml");
    }

    public void testUnknownFileRefDoesNotShowAsError() {
        doTest("unknown_definition_file_ref.json");
        doTest("unknown_definition_file_ref.yaml");

        doTest("unknown_parameter_file_ref.json");
        doTest("unknown_parameter_file_ref.yaml");
    }
}
