package org.zalando.intellij.swagger.validator;

import org.zalando.intellij.swagger.SwaggerLightCodeInsightFixtureTestCase;

public class UnknownFieldTest extends SwaggerLightCodeInsightFixtureTestCase {

    private void doTest(final String fileName) {
        myFixture.testHighlighting(true, false, false, "validator/field/" + fileName);
    }

    public void testUnknownRootField() {
        doTest("unknown_root.json");
        doTest("unknown_root.yaml");
    }

    public void testUnknownContactField() {
        doTest("unknown_contact.json");
        doTest("unknown_contact.yaml");
    }

    public void testUnknownSchemaField() {
        doTest("unknown_schema.json");
        doTest("unknown_schema.yaml");
    }

    public void testUnknownExternalDocsField() {
        doTest("unknown_external_docs.json");
        doTest("unknown_external_docs.yaml");
    }

    public void testUnknownHeaderField() {
        doTest("unknown_header.json");
        doTest("unknown_header.yaml");
    }

    public void testUnknownInfoField() {
        doTest("unknown_info.json");
        doTest("unknown_info.yaml");
    }

    public void testUnknownItemsField() {
        doTest("unknown_items.json");
        doTest("unknown_items.yaml");
    }

    public void testUnknownLicenseField() {
        doTest("unknown_license.json");
        doTest("unknown_license.yaml");
    }

    public void testUnknownOperationField() {
        doTest("unknown_operation.json");
        doTest("unknown_operation.yaml");
    }

    public void testUnknownParametersField() {
        doTest("unknown_parameters.json");
        doTest("unknown_parameters.yaml");
    }

    public void testUnknownPathField() {
        doTest("unknown_path.json");
        doTest("unknown_path.yaml");
    }

    public void testUnknownResponseField() {
        doTest("unknown_response.json");
        doTest("unknown_response.yaml");
    }

    public void testUnknownResponsesField() {
        doTest("unknown_responses.json");
        doTest("unknown_responses.yaml");
    }

    public void testUnknownSecurityDefinitionsField() {
        doTest("unknown_security_definitions.json");
        doTest("unknown_security_definitions.yaml");
    }

    public void testUnknownTagsField() {
        doTest("unknown_tags.json");
        doTest("unknown_tags.yaml");
    }

    public void testUnknownXmlField() {
        doTest("unknown_xml.json");
        doTest("unknown_xml.yaml");
    }
}
