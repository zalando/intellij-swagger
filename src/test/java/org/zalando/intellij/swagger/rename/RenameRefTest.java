package org.zalando.intellij.swagger.rename;

import com.intellij.openapi.vfs.VirtualFile;
import org.zalando.intellij.swagger.SwaggerLightCodeInsightFixtureTestCase;

public class RenameRefTest extends SwaggerLightCodeInsightFixtureTestCase {

    private void testRename(final String newName, final String beforeFileName, final String afterFileName) {
        myFixture.configureByFile("rename/" + beforeFileName);
        myFixture.renameElementAtCaret(newName);
        myFixture.checkResultByFile("rename/" + afterFileName);
    }

    public void testRenameDefinition() {
        testRename("NewPets", "rename_definition_ref_declaration.json", "rename_definition_ref_declaration_after.json");
        testRename("NewPets", "rename_definition_ref_declaration.yaml", "rename_definition_ref_declaration_after.yaml");

        testRename("NewPets", "rename_definition_ref_reference.json", "rename_definition_ref_reference_after.json");
        testRename("NewPets", "rename_definition_ref_reference.yaml", "rename_definition_ref_reference_after.yaml");
    }

    public void testRenameParameter() {
        testRename("newName", "rename_parameter_ref_declaration.json", "rename_parameter_ref_declaration_after.json");
        testRename("newName", "rename_parameter_ref_declaration.yaml", "rename_parameter_ref_declaration_after.yaml");

        testRename("newName", "rename_parameter_ref_reference.json", "rename_parameter_ref_reference_after.json");
        testRename("newName", "rename_parameter_ref_reference.yaml", "rename_parameter_ref_reference_after.yaml");
    }

    public void testRenameResponse() {
        testRename("newName", "rename_response_ref_declaration.json", "rename_response_ref_declaration_after.json");
        testRename("newName", "rename_response_ref_declaration.yaml", "rename_response_ref_declaration_after.yaml");

        testRename("newName", "rename_response_ref_reference.json", "rename_response_ref_reference_after.json");
        testRename("newName", "rename_response_ref_reference.yaml", "rename_response_ref_reference_after.yaml");
    }

    public void testRenameJsonFile() {
        myFixture.copyFileToProject("rename/empty.json", "definitions/pet.json");
        final VirtualFile swaggerFile = myFixture.copyFileToProject("rename/rename_file_reference.json", "swagger.json");
        myFixture.configureFromExistingVirtualFile(swaggerFile);
        myFixture.renameElementAtCaret("newName.json");
        myFixture.checkResultByFile("swagger.json", "rename/rename_file_reference_after.json", true);
        assertNotNull(myFixture.findFileInTempDir("definitions/newName.json"));
        assertNull(myFixture.findFileInTempDir("definitions/pet.json"));
    }

    public void testRenameYamlFile() {
        myFixture.copyFileToProject("rename/empty.yaml", "definitions/pet.yaml");
        final VirtualFile swaggerFile = myFixture.copyFileToProject("rename/rename_file_reference.yaml", "swagger.yaml");
        myFixture.configureFromExistingVirtualFile(swaggerFile);
        myFixture.renameElementAtCaret("newName.yaml");
        myFixture.checkResultByFile("swagger.yaml", "rename/rename_file_reference_after.yaml", true);
        assertNotNull(myFixture.findFileInTempDir("definitions/newName.yaml"));
        assertNull(myFixture.findFileInTempDir("definitions/pet.yaml"));
    }
}
