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

    public void testRenameLocalNestedDefinitionReference() {
        testRename("NewCategory", "rename_definition_ref_nested_reference.yaml", "rename_definition_ref_nested_reference_after.yaml");
    }

    public void testRenameLocalNestedDefinitionDeclaration() {
        testRename("NewCategory", "rename_definition_ref_nested_declaration.yaml", "rename_definition_ref_nested_declaration_after.yaml");
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

    public void testRenameReferenceWithDotInKey() {
        testRename("newName", "rename_definition_with_dot_in_key.yaml", "rename_definition_with_dot_in_key_after.yaml");
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

    //Definitions

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

    // Parameters

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

    // Responses

    public void testRenameExternalResponseDefinitionWhereDefinitionsAreInRoot() {
        myFixture.copyFileToProject(FILES_PATH + "response_definitions_in_root.yaml", "responses.yaml");
        final VirtualFile swaggerFile = myFixture.copyFileToProject(FILES_PATH + "external_definition_response_definitions_in_root_with_caret.yaml", "swagger.yaml");
        myFixture.configureFromExistingVirtualFile(swaggerFile);
        myFixture.renameElementAtCaret("newName");
        myFixture.checkResultByFile("swagger.yaml", FILES_PATH + "external_definition_response_definitions_in_root_with_caret_after.yaml", true);
        myFixture.checkResultByFile("responses.yaml", FILES_PATH + "response_definitions_in_root_after.yaml", true);
    }

    public void testRenameExternalResponseDefinitionDeclarationWhereResponseDefinitionsAreInRoot() {
        final VirtualFile definitionsFile = myFixture.copyFileToProject(FILES_PATH + "response_definitions_in_root_with_caret.yaml", "responses.yaml");
        myFixture.copyFileToProject(FILES_PATH + "external_definition_response_definitions_in_root.yaml", "swagger.yaml");
        myFixture.configureFromExistingVirtualFile(definitionsFile);
        myFixture.renameElementAtCaret("newName");
        myFixture.checkResultByFile("swagger.yaml", FILES_PATH + "external_definition_response_definitions_in_root_after.yaml", true);
        myFixture.checkResultByFile("responses.yaml", FILES_PATH + "response_definitions_in_root_with_caret_after.yaml", true);
    }

    public void testRenameExternalResponseDefinitionWhereDefinitionsAreNotInRoot() {
        myFixture.copyFileToProject(FILES_PATH + "response_definitions_not_in_root.yaml", "responses.yaml");
        final VirtualFile swaggerFile = myFixture.copyFileToProject(FILES_PATH + "external_definition_response_definitions_not_in_root_with_caret.yaml", "swagger.yaml");
        myFixture.configureFromExistingVirtualFile(swaggerFile);
        myFixture.renameElementAtCaret("newName");
        myFixture.checkResultByFile("swagger.yaml", FILES_PATH + "external_definition_response_definitions_not_in_root_with_caret_after.yaml", true);
        myFixture.checkResultByFile("responses.yaml", FILES_PATH + "response_definitions_not_in_root_after.yaml", true);
    }

    public void testRenameExternalResponseDefinitionDeclarationWhereResponseDefinitionsAreNotInRoot() {
        final VirtualFile definitionsFile = myFixture.copyFileToProject(FILES_PATH + "response_definitions_not_in_root_with_caret.yaml", "responses.yaml");
        myFixture.copyFileToProject(FILES_PATH + "external_definition_response_definitions_not_in_root.yaml", "swagger.yaml");
        myFixture.configureFromExistingVirtualFile(definitionsFile);
        myFixture.renameElementAtCaret("newName");
        myFixture.checkResultByFile("swagger.yaml", FILES_PATH + "external_definition_response_definitions_not_in_root_after.yaml", true);
        myFixture.checkResultByFile("responses.yaml", FILES_PATH + "response_definitions_not_in_root_with_caret_after.yaml", true);
    }


    // Paths

    public void testRenameExternalPathDefinitionWhereDefinitionsAreInRoot() {
        myFixture.copyFileToProject(FILES_PATH + "path_definitions_in_root.yaml", "paths.yaml");
        final VirtualFile swaggerFile = myFixture.copyFileToProject(FILES_PATH + "external_definition_path_definitions_in_root_with_caret.yaml", "swagger.yaml");
        myFixture.configureFromExistingVirtualFile(swaggerFile);
        myFixture.renameElementAtCaret("newName");
        myFixture.checkResultByFile("swagger.yaml", FILES_PATH + "external_definition_path_definitions_in_root_with_caret_after.yaml", true);
        myFixture.checkResultByFile("paths.yaml", FILES_PATH + "path_definitions_in_root_after.yaml", true);
    }

    public void testRenameExternalPathDefinitionWhereDefinitionsAreNotInRoot() {
        myFixture.copyFileToProject(FILES_PATH + "path_definitions_not_in_root.yaml", "paths.yaml");
        final VirtualFile swaggerFile = myFixture.copyFileToProject(FILES_PATH + "external_definition_path_definitions_not_in_root_with_caret.yaml", "swagger.yaml");
        myFixture.configureFromExistingVirtualFile(swaggerFile);
        myFixture.renameElementAtCaret("newName");
        myFixture.checkResultByFile("swagger.yaml", FILES_PATH + "external_definition_path_definitions_not_in_root_with_caret_after.yaml", true);
        myFixture.checkResultByFile("paths.yaml", FILES_PATH + "path_definitions_not_in_root_after.yaml", true);
    }

    public void testRenameRefInReferencedFile() {
        final VirtualFile definitionsFile = myFixture.copyFileToProject(FILES_PATH + "ref_in_referenced_file_with_caret.yaml", "definitions.yaml");
        final VirtualFile swaggerFile = myFixture.copyFileToProject(FILES_PATH + "rename_ref_in_referenced_file.yaml", "swagger.yaml");
        myFixture.configureFromExistingVirtualFile(swaggerFile);
        myFixture.configureFromExistingVirtualFile(definitionsFile);
        myFixture.renameElementAtCaret("newName");
        myFixture.checkResultByFile("definitions.yaml", FILES_PATH + "ref_in_referenced_file_with_caret_after.yaml", true);
    }
}
