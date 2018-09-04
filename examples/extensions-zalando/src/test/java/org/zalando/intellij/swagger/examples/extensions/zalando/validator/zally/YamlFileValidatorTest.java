package org.zalando.intellij.swagger.examples.extensions.zalando.validator.zally;

import com.intellij.mock.MockComponentManager;
import org.zalando.intellij.swagger.examples.extensions.zalando.SwaggerLightCodeInsightFixtureTestCase;

public class YamlFileValidatorTest extends SwaggerLightCodeInsightFixtureTestCase {

    private void doTest(final String fileName) {
        myFixture.enableInspections(new YamlFileValidator());
        myFixture.testHighlighting(true, false, false, "zally/" + fileName);
    }

    public void testUnknownRootField() {
        doTest("yaml/swagger.yaml");
    }
}
