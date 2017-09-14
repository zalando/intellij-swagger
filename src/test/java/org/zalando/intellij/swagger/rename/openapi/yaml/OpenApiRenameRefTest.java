package org.zalando.intellij.swagger.rename.openapi.yaml;

import org.zalando.intellij.swagger.SwaggerLightCodeInsightFixtureTestCase;

public class OpenApiRenameRefTest extends SwaggerLightCodeInsightFixtureTestCase {

    private static final String FILES_PATH = "rename/openapi/yaml/";

    private void testRename(final String newName, final String beforeFileName, final String afterFileName) {
        myFixture.configureByFile(FILES_PATH + beforeFileName);
        myFixture.renameElementAtCaret(newName);
        myFixture.checkResultByFile(FILES_PATH + afterFileName);
    }

    public void testRenameLocalSchemaReference() {
        testRename("NewPet", "rename_component_schema.yaml", "rename_component_schema_after.yaml");
    }

    public void testRenameLocalResponseReference() {
        testRename("NewNotFound", "rename_component_response.yaml", "rename_component_response_after.yaml");
    }

    public void testRenameLocalParameterReference() {
        testRename("NewParameter", "rename_component_parameter.yaml", "rename_component_parameter_after.yaml");
    }

    public void testRenameLocalExampleReference() {
        testRename("NewExample", "rename_component_example.yaml", "rename_component_example_after.yaml");
    }

    public void testRenameLocalRequestBodyReference() {
        testRename("NewFoo", "rename_component_request_body.yaml", "rename_component_request_body_after.yaml");
    }

    public void testRenameLocalHeaderReference() {
        testRename("NewHeader", "rename_component_header.yaml", "rename_component_header_after.yaml");
    }

    public void testRenameLocalSecuritySchemeReference() {
        testRename("NewLink", "rename_component_link.yaml", "rename_component_link_after.yaml");
    }

    public void testRenameLocalCallbackReference() {
        testRename("NewCallback", "rename_component_callback.yaml", "rename_component_callback_after.yaml");
    }
}
