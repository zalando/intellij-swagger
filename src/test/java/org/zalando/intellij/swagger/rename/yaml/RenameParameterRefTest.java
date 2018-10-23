package org.zalando.intellij.swagger.rename.yaml;

import com.intellij.openapi.vfs.VirtualFile;
import org.zalando.intellij.swagger.SwaggerLightCodeInsightFixtureTestCase;

public class RenameParameterRefTest extends SwaggerLightCodeInsightFixtureTestCase {

  private static final String FILES_PATH = "rename/swagger/parameters/yaml/";

  private void testRename(
      final String newName, final String beforeFileName, final String afterFileName) {
    myFixture.configureByFile(FILES_PATH + beforeFileName);
    myFixture.renameElementAtCaret(newName);
    myFixture.checkResultByFile(FILES_PATH + afterFileName);
  }

  public void testRenameLocalParameterReference() {
    testRename(
        "newName",
        "rename_parameter_ref_reference.yaml",
        "rename_parameter_ref_reference_after.yaml");
  }

  public void testRenameLocalParameterDeclaration() {
    testRename(
        "newName",
        "rename_parameter_ref_declaration.yaml",
        "rename_parameter_ref_declaration_after.yaml");
  }

  public void testRenameExternalParameterDefinitionWhereDefinitionsAreInRoot() {
    myFixture.copyFileToProject(
        FILES_PATH + "parameter_definitions_in_root.yaml", "parameters.yaml");
    final VirtualFile swaggerFile =
        myFixture.copyFileToProject(
            FILES_PATH + "external_definition_parameter_definitions_in_root_with_caret.yaml",
            "swagger.yaml");
    myFixture.configureFromExistingVirtualFile(swaggerFile);
    myFixture.renameElementAtCaret("newName");
    myFixture.checkResultByFile(
        "swagger.yaml",
        FILES_PATH + "external_definition_parameter_definitions_in_root_with_caret_after.yaml",
        true);
    myFixture.checkResultByFile(
        "parameters.yaml", FILES_PATH + "parameter_definitions_in_root_after.yaml", true);
  }

  public void testRenameExternalParameterDefinitionDeclarationWhereParameterDefinitionsAreInRoot() {
    final VirtualFile definitionsFile =
        myFixture.copyFileToProject(
            FILES_PATH + "parameter_definitions_in_root_with_caret.yaml", "parameters.yaml");
    myFixture.copyFileToProject(
        FILES_PATH + "external_definition_parameter_definitions_in_root.yaml", "swagger.yaml");
    myFixture.configureFromExistingVirtualFile(definitionsFile);
    myFixture.renameElementAtCaret("newName");
    myFixture.checkResultByFile(
        "swagger.yaml",
        FILES_PATH + "external_definition_parameter_definitions_in_root_after.yaml",
        true);
    myFixture.checkResultByFile(
        "parameters.yaml",
        FILES_PATH + "parameter_definitions_in_root_with_caret_after.yaml",
        true);
  }

  public void testRenameExternalParameterDefinitionWhereDefinitionsAreNotInRoot() {
    myFixture.copyFileToProject(
        FILES_PATH + "parameter_definitions_not_in_root.yaml", "parameters.yaml");
    final VirtualFile swaggerFile =
        myFixture.copyFileToProject(
            FILES_PATH + "external_definition_parameter_definitions_not_in_root_with_caret.yaml",
            "swagger.yaml");
    myFixture.configureFromExistingVirtualFile(swaggerFile);
    myFixture.renameElementAtCaret("newName");
    myFixture.checkResultByFile(
        "swagger.yaml",
        FILES_PATH + "external_definition_parameter_definitions_not_in_root_with_caret_after.yaml",
        true);
    myFixture.checkResultByFile(
        "parameters.yaml", FILES_PATH + "parameter_definitions_not_in_root_after.yaml", true);
  }

  public void
      testRenameExternalParameterDefinitionDeclarationWhereParameterDefinitionsAreNotInRoot() {
    final VirtualFile definitionsFile =
        myFixture.copyFileToProject(
            FILES_PATH + "parameter_definitions_not_in_root_with_caret.yaml", "parameters.yaml");
    myFixture.copyFileToProject(
        FILES_PATH + "external_definition_parameter_definitions_not_in_root.yaml", "swagger.yaml");
    myFixture.configureFromExistingVirtualFile(definitionsFile);
    myFixture.renameElementAtCaret("newName");
    myFixture.checkResultByFile(
        "swagger.yaml",
        FILES_PATH + "external_definition_parameter_definitions_not_in_root_after.yaml",
        true);
    myFixture.checkResultByFile(
        "parameters.yaml",
        FILES_PATH + "parameter_definitions_not_in_root_with_caret_after.yaml",
        true);
  }
}
