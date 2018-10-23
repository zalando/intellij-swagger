package org.zalando.intellij.swagger.rename.yaml;

import com.intellij.openapi.vfs.VirtualFile;
import org.zalando.intellij.swagger.SwaggerLightCodeInsightFixtureTestCase;

public class RenameResponseRefTest extends SwaggerLightCodeInsightFixtureTestCase {

  private static final String FILES_PATH = "rename/swagger/responses/yaml/";

  private void testRename(
      final String newName, final String beforeFileName, final String afterFileName) {
    myFixture.configureByFile(FILES_PATH + beforeFileName);
    myFixture.renameElementAtCaret(newName);
    myFixture.checkResultByFile(FILES_PATH + afterFileName);
  }

  public void testRenameLocalResponseReference() {
    testRename(
        "newName",
        "rename_response_ref_reference.yaml",
        "rename_response_ref_reference_after.yaml");
  }

  public void testRenameLocalResponseDeclaration() {
    testRename(
        "newName",
        "rename_response_ref_declaration.yaml",
        "rename_response_ref_declaration_after.yaml");
  }

  public void testRenameExternalResponseDefinitionWhereDefinitionsAreInRoot() {
    myFixture.copyFileToProject(FILES_PATH + "response_definitions_in_root.yaml", "responses.yaml");
    final VirtualFile swaggerFile =
        myFixture.copyFileToProject(
            FILES_PATH + "external_definition_response_definitions_in_root_with_caret.yaml",
            "swagger.yaml");
    myFixture.configureFromExistingVirtualFile(swaggerFile);
    myFixture.renameElementAtCaret("newName");
    myFixture.checkResultByFile(
        "swagger.yaml",
        FILES_PATH + "external_definition_response_definitions_in_root_with_caret_after.yaml",
        true);
    myFixture.checkResultByFile(
        "responses.yaml", FILES_PATH + "response_definitions_in_root_after.yaml", true);
  }

  public void testRenameExternalResponseDefinitionDeclarationWhereResponseDefinitionsAreInRoot() {
    final VirtualFile definitionsFile =
        myFixture.copyFileToProject(
            FILES_PATH + "response_definitions_in_root_with_caret.yaml", "responses.yaml");
    myFixture.copyFileToProject(
        FILES_PATH + "external_definition_response_definitions_in_root.yaml", "swagger.yaml");
    myFixture.configureFromExistingVirtualFile(definitionsFile);
    myFixture.renameElementAtCaret("newName");
    myFixture.checkResultByFile(
        "swagger.yaml",
        FILES_PATH + "external_definition_response_definitions_in_root_after.yaml",
        true);
    myFixture.checkResultByFile(
        "responses.yaml", FILES_PATH + "response_definitions_in_root_with_caret_after.yaml", true);
  }

  public void testRenameExternalResponseDefinitionWhereDefinitionsAreNotInRoot() {
    myFixture.copyFileToProject(
        FILES_PATH + "response_definitions_not_in_root.yaml", "responses.yaml");
    final VirtualFile swaggerFile =
        myFixture.copyFileToProject(
            FILES_PATH + "external_definition_response_definitions_not_in_root_with_caret.yaml",
            "swagger.yaml");
    myFixture.configureFromExistingVirtualFile(swaggerFile);
    myFixture.renameElementAtCaret("newName");
    myFixture.checkResultByFile(
        "swagger.yaml",
        FILES_PATH + "external_definition_response_definitions_not_in_root_with_caret_after.yaml",
        true);
    myFixture.checkResultByFile(
        "responses.yaml", FILES_PATH + "response_definitions_not_in_root_after.yaml", true);
  }

  public void
      testRenameExternalResponseDefinitionDeclarationWhereResponseDefinitionsAreNotInRoot() {
    final VirtualFile definitionsFile =
        myFixture.copyFileToProject(
            FILES_PATH + "response_definitions_not_in_root_with_caret.yaml", "responses.yaml");
    myFixture.copyFileToProject(
        FILES_PATH + "external_definition_response_definitions_not_in_root.yaml", "swagger.yaml");
    myFixture.configureFromExistingVirtualFile(definitionsFile);
    myFixture.renameElementAtCaret("newName");
    myFixture.checkResultByFile(
        "swagger.yaml",
        FILES_PATH + "external_definition_response_definitions_not_in_root_after.yaml",
        true);
    myFixture.checkResultByFile(
        "responses.yaml",
        FILES_PATH + "response_definitions_not_in_root_with_caret_after.yaml",
        true);
  }
}
