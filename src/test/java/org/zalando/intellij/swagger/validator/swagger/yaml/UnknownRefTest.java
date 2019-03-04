package org.zalando.intellij.swagger.validator.swagger.yaml;

import org.zalando.intellij.swagger.SwaggerLightCodeInsightFixtureTestCase;
import org.zalando.intellij.swagger.inspection.reference.YamlReferenceInspection;

public class UnknownRefTest extends SwaggerLightCodeInsightFixtureTestCase {

  private void doTest(final String fileName) {
    myFixture.enableInspections(new YamlReferenceInspection());

    myFixture.testHighlighting(true, false, false, "validator/value/" + fileName);
  }

  public void testUnknownDefinitionRef() {
    doTest("unknown_definition_ref.yaml");
  }

  public void testUnknownParameterRef() {
    doTest("unknown_parameter_ref.yaml");
  }

  public void testUnknownResponseRef() {
    doTest("unknown_response_ref.yaml");
  }

  public void testUnknownYamlFileRef() {
    doTest("unknown_definition_file_ref.yaml");
  }

  public void testUnknownScheme() {
    doTest("unknown_scheme.yaml");
  }

  public void testUrlReferenceShouldNotBeReportedAsError() {
    doTest("url_ref.yaml");
  }

  public void testUrlReferenceSingleQuoteShouldNotBeReportedAsError() {
    doTest("url_ref_single_quote.yaml");
  }

  public void testUrlReferenceDoubleQuoteShouldNotBeReportedAsError() {
    doTest("url_ref_double_quote.yaml");
  }

  public void testUnknownFileRef() {
    doTest("unknown_file_format.yaml");
  }
}
