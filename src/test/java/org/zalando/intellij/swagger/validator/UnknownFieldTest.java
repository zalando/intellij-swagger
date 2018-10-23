package org.zalando.intellij.swagger.validator;

import org.zalando.intellij.swagger.SwaggerLightCodeInsightFixtureTestCase;

public class UnknownFieldTest extends SwaggerLightCodeInsightFixtureTestCase {

  private void doTest(final String fileName) {
    myFixture.testHighlighting(true, false, false, "validator/field/unknown/" + fileName);
  }

  public void testUnknownRootField() {
    doTest("json/unknown_root.json");
    doTest("yaml/unknown_root.yaml");
  }

  public void testUnknownContactField() {
    doTest("json/unknown_contact.json");
    doTest("yaml/unknown_contact.yaml");
  }

  public void testUnknownSchemaField() {
    doTest("json/unknown_schema.json");
    doTest("yaml/unknown_schema.yaml");
  }

  public void testUnknownSchemaItemsField() {
    doTest("json/unknown_schema_items.json");
    doTest("yaml/unknown_schema_items.yaml");
  }

  public void testUnknownExternalDocsField() {
    doTest("json/unknown_external_docs.json");
    doTest("yaml/unknown_external_docs.yaml");
  }

  public void testUnknownHeaderField() {
    doTest("json/unknown_header.json");
    doTest("yaml/unknown_header.yaml");
  }

  public void testUnknownInfoField() {
    doTest("json/unknown_info.json");
    doTest("yaml/unknown_info.yaml");
  }

  public void testUnknownItemsField() {
    doTest("json/unknown_parameter_items.json");
    doTest("yaml/unknown_parameter_items.yaml");
  }

  public void testUnknownLicenseField() {
    doTest("json/unknown_license.json");
    doTest("yaml/unknown_license.yaml");
  }

  public void testUnknownOperationField() {
    doTest("json/unknown_operation.json");
    doTest("yaml/unknown_operation.yaml");
  }

  public void testUnknownParametersField() {
    doTest("json/unknown_parameters.json");
    doTest("yaml/unknown_parameters.yaml");
  }

  public void testUnknownPathField() {
    doTest("json/unknown_path.json");
    doTest("yaml/unknown_path.yaml");
  }

  public void testUnknownResponseField() {
    doTest("json/unknown_response.json");
    doTest("yaml/unknown_response.yaml");
  }

  public void testUnknownResponsesField() {
    doTest("json/unknown_responses.json");
    doTest("yaml/unknown_responses.yaml");
  }

  public void testUnknownSecurityDefinitionsField() {
    doTest("json/unknown_security_definitions.json");
    doTest("yaml/unknown_security_definitions.yaml");
  }

  public void testUnknownTagsField() {
    doTest("json/unknown_tags.json");
    doTest("yaml/unknown_tags.yaml");
  }

  public void testUnknownXmlField() {
    doTest("json/unknown_xml.json");
    doTest("yaml/unknown_xml.yaml");
  }
}
