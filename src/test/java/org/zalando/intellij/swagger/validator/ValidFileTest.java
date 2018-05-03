package org.zalando.intellij.swagger.validator;

import org.zalando.intellij.swagger.SwaggerLightCodeInsightFixtureTestCase;

public class ValidFileTest extends SwaggerLightCodeInsightFixtureTestCase {

    private void doTest(final String fileName) {
        myFixture.testHighlighting(true, false, false, "validator/" + fileName);
    }

    public void testThatSwaggerFileHasNoErrors() {
        doTest("no_errors.json");
        doTest("no_errors.yaml");
    }

    public void testNonSwaggerFileHasNoErrors() {
        doTest("not_swagger.json");
        doTest("not_swagger.yaml");
    }

    public void testSwaggerRootNoVersionIsNotASwaggerFile(){
        doTest("swagger-root-no-version.json");
        doTest("swagger-root-no-version.yaml");
    }

    public void testOpenApiRootNoVersionIsNotOpenApiFile(){
        doTest("openapi-root-no-version.json");
        doTest("openapi-root-no-version.yaml");
    }
}
