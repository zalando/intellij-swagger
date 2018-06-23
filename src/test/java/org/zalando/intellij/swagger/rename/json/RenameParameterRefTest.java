package org.zalando.intellij.swagger.rename.json;

import com.intellij.openapi.vfs.VirtualFile;
import org.zalando.intellij.swagger.SwaggerLightCodeInsightFixtureTestCase;

public class RenameParameterRefTest extends SwaggerLightCodeInsightFixtureTestCase {

    private static final String FILES_PATH = "rename/swagger/parameters/json/";

    private void testRename(final String newName, final String beforeFileName, final String afterFileName) {
        myFixture.configureByFile(FILES_PATH + beforeFileName);
        myFixture.renameElementAtCaret(newName);
        myFixture.checkResultByFile(FILES_PATH + afterFileName);
    }

    public void testRenameLocalParameterReference() {
        testRename("newName", "rename_parameter_ref_reference.json", "rename_parameter_ref_reference_after.json");
    }

    public void testRenameLocalParameterDeclaration() {
        testRename("newName", "rename_parameter_ref_declaration.json", "rename_parameter_ref_declaration_after.json");
    }

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
}
