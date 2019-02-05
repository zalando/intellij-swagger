package org.zalando.intellij.swagger.validator.swagger.yaml;

import org.zalando.intellij.swagger.SwaggerLightCodeInsightFixtureTestCase;
import org.zalando.intellij.swagger.inspection.schema.YamlSchemaInspection;

public class JsonSchemaTest extends SwaggerLightCodeInsightFixtureTestCase {

  private void doTest(final String testDataPath) {
    myFixture.enableInspections(new YamlSchemaInspection());

    myFixture.testHighlighting(true, false, true, "validator/jsonschema/" + testDataPath);
  }

  public void testSwaggerFile() {
    doTest("swagger/swagger.yaml");
  }

  public void testOpenApiFile() {
    doTest("openapi/openapi.yaml");
  }

}
