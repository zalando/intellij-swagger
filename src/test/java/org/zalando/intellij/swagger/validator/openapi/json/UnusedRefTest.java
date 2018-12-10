package org.zalando.intellij.swagger.validator.openapi.json;

import org.zalando.intellij.swagger.SwaggerLightCodeInsightFixtureTestCase;

public class UnusedRefTest extends SwaggerLightCodeInsightFixtureTestCase {

  private void doTest(final String fileName) {
    myFixture.testHighlighting(
        true, false, true, "validator/field/openapi/unused/json/" + fileName);
  }

  public void testUnusedSchemaMainFile() {
    doTest("unused_schema_main_file.json");
  }

  public void testUnusedCallbackMainFile() {
    doTest("unused_callback_main_file.json");
  }

  public void testUnusedExampleMainFile() {
    doTest("unused_example_main_file.json");
  }

  public void testUnusedHeaderMainFile() {
    doTest("unused_header_main_file.json");
  }

  public void testUnusedLinkMainFile() {
    doTest("unused_link_main_file.json");
  }

  public void testUnusedParameterMainFile() {
    doTest("unused_parameter_main_file.json");
  }

  public void testUnusedRequestBodyMainFile() {
    doTest("unused_request_body_main_file.json");
  }

  public void testUnusedResponseMainFile() {
    doTest("unused_response_main_file.json");
  }
}
