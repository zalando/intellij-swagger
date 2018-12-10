package org.zalando.intellij.swagger.validator.swagger;

import org.zalando.intellij.swagger.SwaggerLightCodeInsightFixtureTestCase;

public class UnknownValueTest extends SwaggerLightCodeInsightFixtureTestCase {

  private void doTest(final String fileName) {
    myFixture.testHighlighting(true, false, false, "validator/value/" + fileName);
  }

  public void testUnknownDefinitionRef() {
    doTest("unknown_definition_ref.json");
    doTest("unknown_definition_ref.yaml");
  }

  public void testUnknownParameterRef() {
    doTest("unknown_parameter_ref.json");
    doTest("unknown_parameter_ref.yaml");
  }

  public void testUnknownResponseRef() {
    doTest("unknown_response_ref.json");
    doTest("unknown_response_ref.yaml");
  }

  public void testUnknownFileRefDoesNotShowAsError() {
    doTest("unknown_definition_file_ref.json");
    doTest("unknown_definition_file_ref.yaml");

    doTest("unknown_parameter_file_ref.json");
    doTest("unknown_parameter_file_ref.yaml");
  }

  public void testUnknownScheme() {
    doTest("unknown_scheme.json");
    doTest("unknown_scheme.yaml");
  }
}
