package org.zalando.intellij.swagger.rename.yaml;

import com.intellij.openapi.vfs.VirtualFile;
import org.zalando.intellij.swagger.SwaggerLightCodeInsightFixtureTestCase;

public class RenameRefTest extends SwaggerLightCodeInsightFixtureTestCase {

    private static final String FILES_PATH = "rename/yaml/";

    private void testRename(final String newName, final String beforeFileName, final String afterFileName) {
        myFixture.configureByFile(FILES_PATH + beforeFileName);
        myFixture.renameElementAtCaret(newName);
        myFixture.checkResultByFile(FILES_PATH + afterFileName);
    }

    public void testRenameLocalDefinitionReference() {
        testRename("NewPets", "rename_definition_ref_reference.yaml", "rename_definition_ref_reference_after.yaml");
    }

    public void testRenameLocalDefinitionDeclaration() {
        testRename("NewPets", "rename_definition_ref_declaration.yaml", "rename_definition_ref_declaration_after.yaml");
    }

    public void testRenameLocalParameterReference() {
        testRename("newName", "rename_parameter_ref_reference.yaml", "rename_parameter_ref_reference_after.yaml");
    }

    public void testRenameLocalParameterDeclaration() {
        testRename("newName", "rename_parameter_ref_declaration.yaml", "rename_parameter_ref_declaration_after.yaml");
    }

    public void testRenameLocalResponseReference() {
        testRename("newName", "rename_response_ref_reference.yaml", "rename_response_ref_reference_after.yaml");
    }

    public void testRenameLocalResponseDeclaration() {
        testRename("newName", "rename_response_ref_declaration.yaml", "rename_response_ref_declaration_after.yaml");
    }

    public void testRenameFileReference() {
        myFixture.copyFileToProject(FILES_PATH + "empty.yaml", "definitions/pet.yaml");
        final VirtualFile swaggerFile = myFixture.copyFileToProject(FILES_PATH + "rename_file_reference.yaml", "swagger.yaml");
        myFixture.configureFromExistingVirtualFile(swaggerFile);
        myFixture.renameElementAtCaret("newName.yaml");
        myFixture.checkResultByFile("swagger.yaml", FILES_PATH + "rename_file_reference_after.yaml", true);
        assertNotNull(myFixture.findFileInTempDir("definitions/newName.yaml"));
        assertNull(myFixture.findFileInTempDir("definitions/pet.yaml"));
    }

    public void testRenameExternalDefinitionWhereDefinitionsAreInRoot() {
        myFixture.copyFileToProject(FILES_PATH + "definitions_in_root.yaml", "definitions.yaml");
        final VirtualFile swaggerFile = myFixture.copyFileToProject(FILES_PATH + "external_definition_definitions_in_root_with_caret.yaml", "swagger.yaml");
        myFixture.configureFromExistingVirtualFile(swaggerFile);
        myFixture.renameElementAtCaret("newName");
        myFixture.checkResultByFile("swagger.yaml", FILES_PATH + "external_definition_definitions_in_root_with_caret_after.yaml", true);
        myFixture.checkResultByFile("definitions.yaml", FILES_PATH + "definitions_in_root_after.yaml", true);
    }

    public void testRenameExternalDefinitionDeclarationWhereDefinitionsAreInRoot() {
        final VirtualFile definitionsFile = myFixture.copyFileToProject(FILES_PATH + "definitions_in_root_with_caret.yaml", "definitions.yaml");
        myFixture.copyFileToProject(FILES_PATH + "external_definition_definitions_in_root.yaml", "swagger.yaml");
        myFixture.configureFromExistingVirtualFile(definitionsFile);
        myFixture.renameElementAtCaret("newName");
        myFixture.checkResultByFile("swagger.yaml", FILES_PATH + "external_definition_definitions_in_root_after.yaml", true);
        myFixture.checkResultByFile("definitions.yaml", FILES_PATH + "definitions_in_root_with_caret_after.yaml", true);
    }

    public void testRenameExternalDefinitionWhereDefinitionsAreNotInRoot() {
        myFixture.copyFileToProject(FILES_PATH + "definitions_not_in_root.yaml", "definitions.yaml");
        final VirtualFile swaggerFile = myFixture.copyFileToProject(FILES_PATH + "external_definition_definitions_not_in_root_with_caret.yaml", "swagger.yaml");
        myFixture.configureFromExistingVirtualFile(swaggerFile);
        myFixture.renameElementAtCaret("newName");
        myFixture.checkResultByFile("swagger.yaml", FILES_PATH + "external_definition_definitions_not_in_root_with_caret_after.yaml", true);
        myFixture.checkResultByFile("definitions.yaml", FILES_PATH + "definitions_not_in_root_after.yaml", true);
    }

    public void testRenameExternalDefinitionDeclarationWhereDefinitionsAreNotInRoot() {
        final VirtualFile definitionsFile = myFixture.copyFileToProject(FILES_PATH + "definitions_not_in_root_with_caret.yaml", "definitions.yaml");
        myFixture.copyFileToProject(FILES_PATH + "external_definition_definitions_not_in_root.yaml", "swagger.yaml");
        myFixture.configureFromExistingVirtualFile(definitionsFile);
        myFixture.renameElementAtCaret("newName");
        myFixture.checkResultByFile("swagger.yaml", FILES_PATH + "external_definition_definitions_not_in_root_after.yaml", true);
        myFixture.checkResultByFile("definitions.yaml", FILES_PATH + "definitions_not_in_root_with_caret_after.yaml", true);
    }

    public void testRenameExternalParameterDefinitionWhereDefinitionsAreInRoot() {
        myFixture.copyFileToProject(FILES_PATH + "parameter_definitions_in_root.yaml", "parameters.yaml");
        final VirtualFile swaggerFile = myFixture.copyFileToProject(FILES_PATH + "external_definition_parameter_definitions_in_root_with_caret.yaml", "swagger.yaml");
        myFixture.configureFromExistingVirtualFile(swaggerFile);
        myFixture.renameElementAtCaret("newName");
        myFixture.checkResultByFile("swagger.yaml", FILES_PATH + "external_definition_parameter_definitions_in_root_with_caret_after.yaml", true);
        myFixture.checkResultByFile("parameters.yaml", FILES_PATH + "parameter_definitions_in_root_after.yaml", true);
    }

    public void testRenameExternalParameterDefinitionDeclarationWhereParameterDefinitionsAreInRoot() {
        final VirtualFile definitionsFile = myFixture.copyFileToProject(FILES_PATH + "parameter_definitions_in_root_with_caret.yaml", "parameters.yaml");
        myFixture.copyFileToProject(FILES_PATH + "external_definition_parameter_definitions_in_root.yaml", "swagger.yaml");
        myFixture.configureFromExistingVirtualFile(definitionsFile);
        myFixture.renameElementAtCaret("newName");
        myFixture.checkResultByFile("swagger.yaml", FILES_PATH + "external_definition_parameter_definitions_in_root_after.yaml", true);
        myFixture.checkResultByFile("parameters.yaml", FILES_PATH + "parameter_definitions_in_root_with_caret_after.yaml", true);
    }

    public void testRenameExternalParameterDefinitionWhereDefinitionsAreNotInRoot() {
        myFixture.copyFileToProject(FILES_PATH + "parameter_definitions_not_in_root.yaml", "parameters.yaml");
        final VirtualFile swaggerFile = myFixture.copyFileToProject(FILES_PATH + "external_definition_parameter_definitions_not_in_root_with_caret.yaml", "swagger.yaml");
        myFixture.configureFromExistingVirtualFile(swaggerFile);
        myFixture.renameElementAtCaret("newName");
        myFixture.checkResultByFile("swagger.yaml", FILES_PATH + "external_definition_parameter_definitions_not_in_root_with_caret_after.yaml", true);
        myFixture.checkResultByFile("parameters.yaml", FILES_PATH + "parameter_definitions_not_in_root_after.yaml", true);
    }

    public void testRenameExternalParameterDefinitionDeclarationWhereParameterDefinitionsAreNotInRoot() {
        final VirtualFile definitionsFile = myFixture.copyFileToProject(FILES_PATH + "parameter_definitions_not_in_root_with_caret.yaml", "parameters.yaml");
        myFixture.copyFileToProject(FILES_PATH + "external_definition_parameter_definitions_not_in_root.yaml", "swagger.yaml");
        myFixture.configureFromExistingVirtualFile(definitionsFile);
        myFixture.renameElementAtCaret("newName");
        myFixture.checkResultByFile("swagger.yaml", FILES_PATH + "external_definition_parameter_definitions_not_in_root_after.yaml", true);
        myFixture.checkResultByFile("parameters.yaml", FILES_PATH + "parameter_definitions_not_in_root_with_caret_after.yaml", true);
    }
}
