package org.zalando.intellij.swagger.validator.swagger.json;

import org.zalando.intellij.swagger.SwaggerLightCodeInsightFixtureTestCase;
import org.zalando.intellij.swagger.index.IndexFacade;
import org.zalando.intellij.swagger.index.openapi.OpenApiIndexService;
import org.zalando.intellij.swagger.index.swagger.SwaggerIndexService;
import org.zalando.intellij.swagger.inspection.reference.JsonReferenceInspection;
import org.zalando.intellij.swagger.service.intellij.DumbService;

public class UnknownRefTest extends SwaggerLightCodeInsightFixtureTestCase {

  private final IndexFacade indexFacade =
      new IndexFacade(new OpenApiIndexService(), new SwaggerIndexService(), new DumbService());

  private void doTest(final String fileName) {
    myFixture.enableInspections(new JsonReferenceInspection(indexFacade));

    myFixture.testHighlighting(true, false, false, "validator/value/" + fileName);
  }

  public void testUnknownDefinitionRef() {
    doTest("unknown_definition_ref.json");
  }

  public void testUnknownParameterRef() {
    doTest("unknown_parameter_ref.json");
  }

  public void testUnknownResponseRef() {
    doTest("unknown_response_ref.json");
  }

  public void testUnknownJsonFileRef() {
    doTest("unknown_definition_file_ref.json");
  }

  public void testUnknownFileRef() {
    doTest("unknown_file_format.json");
  }

  public void testUrlReferenceShouldNotBeReportedAsError() {
    doTest("url_ref.json");
  }
}
