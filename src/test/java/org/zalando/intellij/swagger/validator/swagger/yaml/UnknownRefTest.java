package org.zalando.intellij.swagger.validator.swagger.yaml;

import org.zalando.intellij.swagger.SwaggerLightCodeInsightFixtureTestCase;
import org.zalando.intellij.swagger.index.IndexFacade;
import org.zalando.intellij.swagger.index.openapi.OpenApiIndexService;
import org.zalando.intellij.swagger.index.swagger.SwaggerIndexService;
import org.zalando.intellij.swagger.inspection.reference.YamlReferenceInspection;
import org.zalando.intellij.swagger.service.intellij.DumbService;

public class UnknownRefTest extends SwaggerLightCodeInsightFixtureTestCase {

  private final IndexFacade indexFacade =
      new IndexFacade(new OpenApiIndexService(), new SwaggerIndexService(), new DumbService());

  private void doTest(final String fileName) {
    myFixture.enableInspections(new YamlReferenceInspection(indexFacade));

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
