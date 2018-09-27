package org.zalando.intellij.swagger.examples.extensions.zalando.validator.zally;

import org.zalando.intellij.swagger.examples.extensions.zalando.SwaggerLightCodeInsightFixtureTestCase;

public class SwaggerYamlFileValidatorTest extends SwaggerLightCodeInsightFixtureTestCase {

    private void doTest(final String fileName) {
        myFixture.enableInspections(new SwaggerYamlFileValidator());
        myFixture.testHighlighting(true, false, false, "zally/" + fileName);
    }

    public void testUnknownRootField() {
        doTest("yaml/swagger.yaml");
    }
}
