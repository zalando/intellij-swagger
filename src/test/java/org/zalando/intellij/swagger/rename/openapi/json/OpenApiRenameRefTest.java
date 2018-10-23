package org.zalando.intellij.swagger.rename.openapi.json;

import com.intellij.openapi.vfs.VirtualFile;
import org.zalando.intellij.swagger.SwaggerLightCodeInsightFixtureTestCase;

public class OpenApiRenameRefTest extends SwaggerLightCodeInsightFixtureTestCase {

  private static final String FILES_PATH = "rename/openapi/json/";

  private void testRename(
      final String newName, final String beforeFileName, final String afterFileName) {
    myFixture.configureByFile(FILES_PATH + beforeFileName);
    myFixture.renameElementAtCaret(newName);
    myFixture.checkResultByFile(FILES_PATH + afterFileName);
  }

  public void testRenameLocalSchemaReference() {
    testRename("NewPet", "rename_component_schema.json", "rename_component_schema_after.json");
  }

  public void testRenameLocalResponseReference() {
    testRename(
        "NewNotFound", "rename_component_response.json", "rename_component_response_after.json");
  }

  public void testRenameLocalParameterReference() {
    testRename(
        "NewParameter", "rename_component_parameter.json", "rename_component_parameter_after.json");
  }

  public void testRenameLocalExampleReference() {
    testRename(
        "NewExample", "rename_component_example.json", "rename_component_example_after.json");
  }

  public void testRenameLocalRequestBodyReference() {
    testRename(
        "NewFoo", "rename_component_request_body.json", "rename_component_request_body_after.json");
  }

  public void testRenameLocalHeaderReference() {
    testRename("NewHeader", "rename_component_header.json", "rename_component_header_after.json");
  }

  public void testRenameLocalSecuritySchemeReference() {
    testRename("NewLink", "rename_component_link.json", "rename_component_link_after.json");
  }

  public void testRenameLocalCallbackReference() {
    testRename(
        "NewCallback", "rename_component_callback.json", "rename_component_callback_after.json");
  }

  public void testRenameReferenceWithDotInKey() {
    testRename(
        "NewName",
        "rename_component_with_dot_in_key.json",
        "rename_component_with_dot_in_key_after.json");
  }

  public void testRenameFileReference() {
    myFixture.copyFileToProject(getSourceFile("pet.json"), "pet.json");

    rename("rename_file_reference.json", "NewName.json");

    myFixture.checkResultByFile(getSourceFile("rename_file_reference_after.json"));

    assertNotNull(myFixture.findFileInTempDir("NewName.json"));
    assertNull(myFixture.findFileInTempDir("pet.json"));
  }

  public void testRenameComponentFileReference() {
    myFixture.copyFileToProject(getSourceFile("components.json"), "components.json");

    rename("rename_component_file_reference.json", "NewName");

    myFixture.checkResultByFile(getSourceFile("rename_component_file_reference_after.json"));
  }

  private void rename(final String file, final String newName) {
    final VirtualFile specFile = myFixture.copyFileToProject(getSourceFile(file), "openapi.json");

    myFixture.configureFromExistingVirtualFile(specFile);
    myFixture.renameElementAtCaret(newName);
  }

  private String getSourceFile(final String file) {
    return FILES_PATH + file;
  }
}
