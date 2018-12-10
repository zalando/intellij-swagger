package org.zalando.intellij.swagger.validator.openapi.yaml;

import org.zalando.intellij.swagger.SwaggerLightCodeInsightFixtureTestCase;

public class UnusedRefTest extends SwaggerLightCodeInsightFixtureTestCase {

  private void doTest(final String fileName) {
    myFixture.testHighlighting(true, false, true, "validator/field/openapi/" + fileName);
  }

  public void testUnusedSchemaMainFile() {
    doTest("unused/yaml/unused_schema_main_file.yaml");
  }

  public void testUnusedCallbackMainFile() {
    doTest("unused/yaml/unused_callback_main_file.yaml");
  }

  public void testUnusedExampleMainFile() {
    doTest("unused/yaml/unused_example_main_file.yaml");
  }

  public void testUnusedHeaderMainFile() {
    doTest("unused/yaml/unused_header_main_file.yaml");
  }

  public void testUnusedLinkMainFile() {
    doTest("unused/yaml/unused_link_main_file.yaml");
  }

  public void testUnusedParameterMainFile() {
    doTest("unused/yaml/unused_parameter_main_file.yaml");
  }

  public void testUnusedRequestBodyMainFile() {
    doTest("unused/yaml/unused_request_body_main_file.yaml");
  }

  public void testUnusedResponseMainFile() {
    doTest("unused/yaml/unused_response_main_file.yaml");
  }
}
