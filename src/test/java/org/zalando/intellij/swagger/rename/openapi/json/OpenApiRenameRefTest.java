package org.zalando.intellij.swagger.rename.openapi.json;

import org.zalando.intellij.swagger.SwaggerLightCodeInsightFixtureTestCase;

public class OpenApiRenameRefTest extends SwaggerLightCodeInsightFixtureTestCase {

    private static final String FILES_PATH = "rename/openapi/json/";

    private void testRename(final String newName, final String beforeFileName, final String afterFileName) {
        myFixture.configureByFile(FILES_PATH + beforeFileName);
        myFixture.renameElementAtCaret(newName);
        myFixture.checkResultByFile(FILES_PATH + afterFileName);
    }

    public void testRenameLocalSchemaReference() {
        testRename("NewPet", "rename_component_schema.json", "rename_component_schema_after.json");
    }

    public void testRenameLocalResponseReference() {
        testRename("NewNotFound", "rename_component_response.json", "rename_component_response_after.json");
    }

    public void testRenameLocalParameterReference() {
        testRename("NewParameter", "rename_component_parameter.json", "rename_component_parameter_after.json");
    }

    public void testRenameLocalExampleReference() {
        testRename("NewExample", "rename_component_example.json", "rename_component_example_after.json");
    }

    public void testRenameLocalRequestBodyReference() {
        testRename("NewFoo", "rename_component_request_body.json", "rename_component_request_body_after.json");
    }

    public void testRenameLocalHeaderReference() {
        testRename("NewHeader", "rename_component_header.json", "rename_component_header_after.json");
    }

    public void testRenameLocalSecuritySchemeReference() {
        testRename("NewLink", "rename_component_link.json", "rename_component_link_after.json");
    }

    public void testRenameLocalCallbackReference() {
        testRename("NewCallback", "rename_component_callback.json", "rename_component_callback_after.json");
    }
}
