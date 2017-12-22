package org.zalando.intellij.swagger.rename.json;

import com.intellij.openapi.vfs.VirtualFile;
import org.zalando.intellij.swagger.SwaggerLightCodeInsightFixtureTestCase;

public class RenameRefTest extends SwaggerLightCodeInsightFixtureTestCase {

    private static final String FILES_PATH = "rename/json/";

    private void testRename(final String newName, final String beforeFileName, final String afterFileName) {
        myFixture.configureByFile(FILES_PATH + beforeFileName);
        myFixture.renameElementAtCaret(newName);
        myFixture.checkResultByFile(FILES_PATH + afterFileName);
    }

    public void testRenameLocalDefinitionReference() {
        testRename("NewPets", "rename_definition_ref_reference.json", "rename_definition_ref_reference_after.json");
    }

    public void testRenameLocalDefinitionDeclaration() {
        testRename("NewPets", "rename_definition_ref_declaration.json", "rename_definition_ref_declaration_after.json");
    }

    public void testRenameLocalNestedDefinitionReference() {
        testRename("NewCategory", "rename_definition_ref_nested_reference.json", "rename_definition_ref_nested_reference_after.json");
    }

    public void testRenameLocalNestedDefinitionDeclaration() {
        testRename("NewCategory", "rename_definition_ref_nested_declaration.json", "rename_definition_ref_nested_declaration_after.json");
    }

    public void testRenameLocalParameterReference() {
        testRename("newName", "rename_parameter_ref_reference.json", "rename_parameter_ref_reference_after.json");
    }

    public void testRenameLocalParameterDeclaration() {
        testRename("newName", "rename_parameter_ref_declaration.json", "rename_parameter_ref_declaration_after.json");
    }

    public void testRenameLocalResponseReference() {
        testRename("newName", "rename_response_ref_reference.json", "rename_response_ref_reference_after.json");
    }

    public void testRenameLocalResponseDeclaration() {
        testRename("newName", "rename_response_ref_declaration.json", "rename_response_ref_declaration_after.json");
    }

    public void testRenameJsonFileReference() {
        myFixture.copyFileToProject(FILES_PATH + "empty.json", "definitions/pet.json");
        final VirtualFile swaggerFile = myFixture.copyFileToProject(FILES_PATH + "rename_file_reference.json", "swagger.json");
        myFixture.configureFromExistingVirtualFile(swaggerFile);
        myFixture.renameElementAtCaret("newName.json");
        myFixture.checkResultByFile("swagger.json", FILES_PATH + "rename_file_reference_after.json", true);
        assertNotNull(myFixture.findFileInTempDir("definitions/newName.json"));
        assertNull(myFixture.findFileInTempDir("definitions/pet.json"));
    }

    // Definitions

    public void testRenameExternalDefinitionWhereDefinitionsAreInRoot() {
        myFixture.copyFileToProject(FILES_PATH + "definitions_in_root.json", "definitions.json");
        final VirtualFile swaggerFile = myFixture.copyFileToProject(FILES_PATH + "external_definition_definitions_in_root_with_caret.json", "swagger.json");
        myFixture.configureFromExistingVirtualFile(swaggerFile);
        myFixture.renameElementAtCaret("newName");
        myFixture.checkResultByFile("swagger.json", FILES_PATH + "external_definition_definitions_in_root_with_caret_after.json", true);
        myFixture.checkResultByFile("definitions.json", FILES_PATH + "definitions_in_root_after.json", true);
    }

    public void testRenameExternalDefinitionDeclarationWhereDefinitionsAreInRoot() {
        final VirtualFile definitionsFile = myFixture.copyFileToProject(FILES_PATH + "definitions_in_root_with_caret.json", "definitions.json");
        myFixture.copyFileToProject(FILES_PATH + "external_definition_definitions_in_root.json", "swagger.json");
        myFixture.configureFromExistingVirtualFile(definitionsFile);
        myFixture.renameElementAtCaret("newName");
        myFixture.checkResultByFile("swagger.json", FILES_PATH + "external_definition_definitions_in_root_after.json", true);
        myFixture.checkResultByFile("definitions.json", FILES_PATH + "definitions_in_root_with_caret_after.json", true);
    }

    public void testRenameExternalDefinitionWhereDefinitionsAreNotInRoot() {
        myFixture.copyFileToProject(FILES_PATH + "definitions_not_in_root.json", "definitions.json");
        final VirtualFile swaggerFile = myFixture.copyFileToProject(FILES_PATH + "external_definition_definitions_not_in_root_with_caret.json", "swagger.json");
        myFixture.configureFromExistingVirtualFile(swaggerFile);
        myFixture.renameElementAtCaret("newName");
        myFixture.checkResultByFile("swagger.json", FILES_PATH + "external_definition_definitions_not_in_root_with_caret_after.json", true);
        myFixture.checkResultByFile("definitions.json", FILES_PATH + "definitions_not_in_root_after.json", true);
    }

    public void testRenameExternalDefinitionDeclarationWhereDefinitionsAreNotInRoot() {
        final VirtualFile definitionsFile = myFixture.copyFileToProject(FILES_PATH + "definitions_not_in_root_with_caret.json", "definitions.json");
        myFixture.copyFileToProject(FILES_PATH + "external_definition_definitions_not_in_root.json", "swagger.json");
        myFixture.configureFromExistingVirtualFile(definitionsFile);
        myFixture.renameElementAtCaret("newName");
        myFixture.checkResultByFile("swagger.json", FILES_PATH + "external_definition_definitions_not_in_root_after.json", true);
        myFixture.checkResultByFile("definitions.json", FILES_PATH + "definitions_not_in_root_with_caret_after.json", true);
    }

    // Parameters

    public void testRenameExternalParameterDefinitionWhereDefinitionsAreInRoot() {
        myFixture.copyFileToProject(FILES_PATH + "parameter_definitions_in_root.json", "parameters.json");
        final VirtualFile swaggerFile = myFixture.copyFileToProject(FILES_PATH + "external_definition_parameter_definitions_in_root_with_caret.json", "swagger.json");
        myFixture.configureFromExistingVirtualFile(swaggerFile);
        myFixture.renameElementAtCaret("newName");
        myFixture.checkResultByFile("swagger.json", FILES_PATH + "external_definition_parameter_definitions_in_root_with_caret_after.json", true);
        myFixture.checkResultByFile("parameters.json", FILES_PATH + "parameter_definitions_in_root_after.json", true);
    }

    public void testRenameExternalParameterDefinitionDeclarationWhereParameterDefinitionsAreInRoot() {
        final VirtualFile definitionsFile = myFixture.copyFileToProject(FILES_PATH + "parameter_definitions_in_root_with_caret.json", "parameters.json");
        myFixture.copyFileToProject(FILES_PATH + "external_definition_parameter_definitions_in_root.json", "swagger.json");
        myFixture.configureFromExistingVirtualFile(definitionsFile);
        myFixture.renameElementAtCaret("newName");
        myFixture.checkResultByFile("swagger.json", FILES_PATH + "external_definition_parameter_definitions_in_root_after.json", true);
        myFixture.checkResultByFile("parameters.json", FILES_PATH + "parameter_definitions_in_root_with_caret_after.json", true);
    }

    public void testRenameExternalParameterDefinitionWhereDefinitionsAreNotInRoot() {
        myFixture.copyFileToProject(FILES_PATH + "parameter_definitions_not_in_root.json", "parameters.json");
        final VirtualFile swaggerFile = myFixture.copyFileToProject(FILES_PATH + "external_definition_parameter_definitions_not_in_root_with_caret.json", "swagger.json");
        myFixture.configureFromExistingVirtualFile(swaggerFile);
        myFixture.renameElementAtCaret("newName");
        myFixture.checkResultByFile("swagger.json", FILES_PATH + "external_definition_parameter_definitions_not_in_root_with_caret_after.json", true);
        myFixture.checkResultByFile("parameters.json", FILES_PATH + "parameter_definitions_not_in_root_after.json", true);
    }

    public void testRenameExternalParameterDefinitionDeclarationWhereParameterDefinitionsAreNotInRoot() {
        final VirtualFile definitionsFile = myFixture.copyFileToProject(FILES_PATH + "parameter_definitions_not_in_root_with_caret.json", "parameters.json");
        myFixture.copyFileToProject(FILES_PATH + "external_definition_parameter_definitions_not_in_root.json", "swagger.json");
        myFixture.configureFromExistingVirtualFile(definitionsFile);
        myFixture.renameElementAtCaret("newName");
        myFixture.checkResultByFile("swagger.json", FILES_PATH + "external_definition_parameter_definitions_not_in_root_after.json", true);
        myFixture.checkResultByFile("parameters.json", FILES_PATH + "parameter_definitions_not_in_root_with_caret_after.json", true);
    }

    // Responses

    public void testRenameExternalResponseDefinitionWhereDefinitionsAreInRoot() {
        myFixture.copyFileToProject(FILES_PATH + "response_definitions_in_root.json", "responses.json");
        final VirtualFile swaggerFile = myFixture.copyFileToProject(FILES_PATH + "external_definition_response_definitions_in_root_with_caret.json", "swagger.json");
        myFixture.configureFromExistingVirtualFile(swaggerFile);
        myFixture.renameElementAtCaret("newName");
        myFixture.checkResultByFile("swagger.json", FILES_PATH + "external_definition_response_definitions_in_root_with_caret_after.json", true);
        myFixture.checkResultByFile("responses.json", FILES_PATH + "response_definitions_in_root_after.json", true);
    }

    public void testRenameExternalResponseDefinitionDeclarationWhereResponseDefinitionsAreInRoot() {
        final VirtualFile definitionsFile = myFixture.copyFileToProject(FILES_PATH + "response_definitions_in_root_with_caret.json", "responses.json");
        myFixture.copyFileToProject(FILES_PATH + "external_definition_response_definitions_in_root.json", "swagger.json");
        myFixture.configureFromExistingVirtualFile(definitionsFile);
        myFixture.renameElementAtCaret("newName");
        myFixture.checkResultByFile("swagger.json", FILES_PATH + "external_definition_response_definitions_in_root_after.json", true);
        myFixture.checkResultByFile("responses.json", FILES_PATH + "response_definitions_in_root_with_caret_after.json", true);
    }

    public void testRenameExternalResponseDefinitionWhereDefinitionsAreNotInRoot() {
        myFixture.copyFileToProject(FILES_PATH + "response_definitions_not_in_root.json", "responses.json");
        final VirtualFile swaggerFile = myFixture.copyFileToProject(FILES_PATH + "external_definition_response_definitions_not_in_root_with_caret.json", "swagger.json");
        myFixture.configureFromExistingVirtualFile(swaggerFile);
        myFixture.renameElementAtCaret("newName");
        myFixture.checkResultByFile("swagger.json", FILES_PATH + "external_definition_response_definitions_not_in_root_with_caret_after.json", true);
        myFixture.checkResultByFile("responses.json", FILES_PATH + "response_definitions_not_in_root_after.json", true);
    }

    public void testRenameExternalResponseDefinitionDeclarationWhereResponseDefinitionsAreNotInRoot() {
        final VirtualFile definitionsFile = myFixture.copyFileToProject(FILES_PATH + "response_definitions_not_in_root_with_caret.json", "responses.json");
        myFixture.copyFileToProject(FILES_PATH + "external_definition_response_definitions_not_in_root.json", "swagger.json");
        myFixture.configureFromExistingVirtualFile(definitionsFile);
        myFixture.renameElementAtCaret("newName");
        myFixture.checkResultByFile("swagger.json", FILES_PATH + "external_definition_response_definitions_not_in_root_after.json", true);
        myFixture.checkResultByFile("responses.json", FILES_PATH + "response_definitions_not_in_root_with_caret_after.json", true);
    }

    // Paths

    public void testRenameExternalPathDefinitionWhereDefinitionsAreInRoot() {
        myFixture.copyFileToProject(FILES_PATH + "path_definitions_in_root.json", "paths.json");
        final VirtualFile swaggerFile = myFixture.copyFileToProject(FILES_PATH + "external_definition_path_definitions_in_root_with_caret.json", "swagger.json");
        myFixture.configureFromExistingVirtualFile(swaggerFile);
        myFixture.renameElementAtCaret("newName");
        myFixture.checkResultByFile("swagger.json", FILES_PATH + "external_definition_path_definitions_in_root_with_caret_after.json", true);
        myFixture.checkResultByFile("paths.json", FILES_PATH + "path_definitions_in_root_after.json", true);
    }

    public void testRenameExternalPathDefinitionWhereDefinitionsAreNotInRoot() {
        myFixture.copyFileToProject(FILES_PATH + "path_definitions_not_in_root.json", "paths.json");
        final VirtualFile swaggerFile = myFixture.copyFileToProject(FILES_PATH + "external_definition_path_definitions_not_in_root_with_caret.json", "swagger.json");
        myFixture.configureFromExistingVirtualFile(swaggerFile);
        myFixture.renameElementAtCaret("newName");
        myFixture.checkResultByFile("swagger.json", FILES_PATH + "external_definition_path_definitions_not_in_root_with_caret_after.json", true);
        myFixture.checkResultByFile("paths.json", FILES_PATH + "path_definitions_not_in_root_after.json", true);
    }
}
