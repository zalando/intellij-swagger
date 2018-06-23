package org.zalando.intellij.swagger.rename.json;

import com.intellij.openapi.vfs.VirtualFile;
import org.zalando.intellij.swagger.SwaggerLightCodeInsightFixtureTestCase;

public class RenameDefinitionRefTest extends SwaggerLightCodeInsightFixtureTestCase {

    private static final String FILES_PATH = "rename/swagger/definitions/json/";

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


    public void testRenameReferenceWithDotInKey() {
        testRename("newName", "rename_definition_with_dot_in_key.json", "rename_definition_with_dot_in_key_after.json");
    }

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
}
