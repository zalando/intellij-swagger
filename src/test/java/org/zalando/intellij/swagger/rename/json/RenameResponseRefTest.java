package org.zalando.intellij.swagger.rename.json;

import com.intellij.openapi.vfs.VirtualFile;
import org.zalando.intellij.swagger.SwaggerLightCodeInsightFixtureTestCase;

public class RenameResponseRefTest extends SwaggerLightCodeInsightFixtureTestCase {

  private static final String FILES_PATH = "rename/swagger/responses/json/";

  private void testRename(
      final String newName, final String beforeFileName, final String afterFileName) {
    myFixture.configureByFile(FILES_PATH + beforeFileName);
    myFixture.renameElementAtCaret(newName);
    myFixture.checkResultByFile(FILES_PATH + afterFileName);
  }

  public void testRenameLocalResponseReference() {
    testRename(
        "newName",
        "rename_response_ref_reference.json",
        "rename_response_ref_reference_after.json");
  }

  public void testRenameLocalResponseDeclaration() {
    testRename(
        "newName",
        "rename_response_ref_declaration.json",
        "rename_response_ref_declaration_after.json");
  }

  public void testRenameExternalResponseDefinitionWhereDefinitionsAreInRoot() {
    myFixture.copyFileToProject(FILES_PATH + "response_definitions_in_root.json", "responses.json");
    final VirtualFile swaggerFile =
        myFixture.copyFileToProject(
            FILES_PATH + "external_definition_response_definitions_in_root_with_caret.json",
            "swagger.json");
    myFixture.configureFromExistingVirtualFile(swaggerFile);
    myFixture.renameElementAtCaret("newName");
    myFixture.checkResultByFile(
        "swagger.json",
        FILES_PATH + "external_definition_response_definitions_in_root_with_caret_after.json",
        true);
    myFixture.checkResultByFile(
        "responses.json", FILES_PATH + "response_definitions_in_root_after.json", true);
  }

  public void testRenameExternalResponseDefinitionDeclarationWhereResponseDefinitionsAreInRoot() {
    final VirtualFile definitionsFile =
        myFixture.copyFileToProject(
            FILES_PATH + "response_definitions_in_root_with_caret.json", "responses.json");
    myFixture.copyFileToProject(
        FILES_PATH + "external_definition_response_definitions_in_root.json", "swagger.json");
    myFixture.configureFromExistingVirtualFile(definitionsFile);
    myFixture.renameElementAtCaret("newName");
    myFixture.checkResultByFile(
        "swagger.json",
        FILES_PATH + "external_definition_response_definitions_in_root_after.json",
        true);
    myFixture.checkResultByFile(
        "responses.json", FILES_PATH + "response_definitions_in_root_with_caret_after.json", true);
  }

  public void testRenameExternalResponseDefinitionWhereDefinitionsAreNotInRoot() {
    myFixture.copyFileToProject(
        FILES_PATH + "response_definitions_not_in_root.json", "responses.json");
    final VirtualFile swaggerFile =
        myFixture.copyFileToProject(
            FILES_PATH + "external_definition_response_definitions_not_in_root_with_caret.json",
            "swagger.json");
    myFixture.configureFromExistingVirtualFile(swaggerFile);
    myFixture.renameElementAtCaret("newName");
    myFixture.checkResultByFile(
        "swagger.json",
        FILES_PATH + "external_definition_response_definitions_not_in_root_with_caret_after.json",
        true);
    myFixture.checkResultByFile(
        "responses.json", FILES_PATH + "response_definitions_not_in_root_after.json", true);
  }

  public void
      testRenameExternalResponseDefinitionDeclarationWhereResponseDefinitionsAreNotInRoot() {
    final VirtualFile definitionsFile =
        myFixture.copyFileToProject(
            FILES_PATH + "response_definitions_not_in_root_with_caret.json", "responses.json");
    myFixture.copyFileToProject(
        FILES_PATH + "external_definition_response_definitions_not_in_root.json", "swagger.json");
    myFixture.configureFromExistingVirtualFile(definitionsFile);
    myFixture.renameElementAtCaret("newName");
    myFixture.checkResultByFile(
        "swagger.json",
        FILES_PATH + "external_definition_response_definitions_not_in_root_after.json",
        true);
    myFixture.checkResultByFile(
        "responses.json",
        FILES_PATH + "response_definitions_not_in_root_with_caret_after.json",
        true);
  }
}
