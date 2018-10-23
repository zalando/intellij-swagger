package org.zalando.intellij.swagger.rename.yaml;

import com.intellij.openapi.vfs.VirtualFile;
import org.zalando.intellij.swagger.SwaggerLightCodeInsightFixtureTestCase;

public class RenameDefinitionRefTest extends SwaggerLightCodeInsightFixtureTestCase {

  private static final String FILES_PATH = "rename/swagger/definitions/yaml/";

  private void testRename(
      final String newName, final String beforeFileName, final String afterFileName) {
    myFixture.configureByFile(FILES_PATH + beforeFileName);
    myFixture.renameElementAtCaret(newName);
    myFixture.checkResultByFile(FILES_PATH + afterFileName);
  }

  public void testRenameLocalDefinitionReference() {
    testRename(
        "NewPets",
        "rename_definition_ref_reference.yaml",
        "rename_definition_ref_reference_after.yaml");
  }

  public void testRenameLocalDefinitionDeclaration() {
    testRename(
        "NewPets",
        "rename_definition_ref_declaration.yaml",
        "rename_definition_ref_declaration_after.yaml");
  }

  public void testRenameLocalNestedDefinitionReference() {
    testRename(
        "NewCategory",
        "rename_definition_ref_nested_reference.yaml",
        "rename_definition_ref_nested_reference_after.yaml");
  }

  public void testRenameLocalNestedDefinitionDeclaration() {
    testRename(
        "NewCategory",
        "rename_definition_ref_nested_declaration.yaml",
        "rename_definition_ref_nested_declaration_after.yaml");
  }

  public void testRenameReferenceWithDotInKey() {
    testRename(
        "newName",
        "rename_definition_with_dot_in_key.yaml",
        "rename_definition_with_dot_in_key_after.yaml");
  }

  public void testRenameExternalDefinitionWhereDefinitionsAreInRoot() {
    myFixture.copyFileToProject(FILES_PATH + "definitions_in_root.yaml", "definitions.yaml");
    final VirtualFile swaggerFile =
        myFixture.copyFileToProject(
            FILES_PATH + "external_definition_definitions_in_root_with_caret.yaml", "swagger.yaml");
    myFixture.configureFromExistingVirtualFile(swaggerFile);
    myFixture.renameElementAtCaret("newName");
    myFixture.checkResultByFile(
        "swagger.yaml",
        FILES_PATH + "external_definition_definitions_in_root_with_caret_after.yaml",
        true);
    myFixture.checkResultByFile(
        "definitions.yaml", FILES_PATH + "definitions_in_root_after.yaml", true);
  }

  public void testRenameExternalDefinitionDeclarationWhereDefinitionsAreInRoot() {
    final VirtualFile definitionsFile =
        myFixture.copyFileToProject(
            FILES_PATH + "definitions_in_root_with_caret.yaml", "definitions.yaml");
    myFixture.copyFileToProject(
        FILES_PATH + "external_definition_definitions_in_root.yaml", "swagger.yaml");
    myFixture.configureFromExistingVirtualFile(definitionsFile);
    myFixture.renameElementAtCaret("newName");
    myFixture.checkResultByFile(
        "swagger.yaml", FILES_PATH + "external_definition_definitions_in_root_after.yaml", true);
    myFixture.checkResultByFile(
        "definitions.yaml", FILES_PATH + "definitions_in_root_with_caret_after.yaml", true);
  }

  public void testRenameExternalDefinitionWhereDefinitionsAreNotInRoot() {
    myFixture.copyFileToProject(FILES_PATH + "definitions_not_in_root.yaml", "definitions.yaml");
    final VirtualFile swaggerFile =
        myFixture.copyFileToProject(
            FILES_PATH + "external_definition_definitions_not_in_root_with_caret.yaml",
            "swagger.yaml");
    myFixture.configureFromExistingVirtualFile(swaggerFile);
    myFixture.renameElementAtCaret("newName");
    myFixture.checkResultByFile(
        "swagger.yaml",
        FILES_PATH + "external_definition_definitions_not_in_root_with_caret_after.yaml",
        true);
    myFixture.checkResultByFile(
        "definitions.yaml", FILES_PATH + "definitions_not_in_root_after.yaml", true);
  }

  public void testRenameExternalDefinitionDeclarationWhereDefinitionsAreNotInRoot() {
    final VirtualFile definitionsFile =
        myFixture.copyFileToProject(
            FILES_PATH + "definitions_not_in_root_with_caret.yaml", "definitions.yaml");
    myFixture.copyFileToProject(
        FILES_PATH + "external_definition_definitions_not_in_root.yaml", "swagger.yaml");
    myFixture.configureFromExistingVirtualFile(definitionsFile);
    myFixture.renameElementAtCaret("newName");
    myFixture.checkResultByFile(
        "swagger.yaml",
        FILES_PATH + "external_definition_definitions_not_in_root_after.yaml",
        true);
    myFixture.checkResultByFile(
        "definitions.yaml", FILES_PATH + "definitions_not_in_root_with_caret_after.yaml", true);
  }
}
