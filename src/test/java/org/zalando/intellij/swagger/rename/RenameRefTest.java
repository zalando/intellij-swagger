package org.zalando.intellij.swagger.rename;

import org.zalando.intellij.swagger.SwaggerLightCodeInsightFixtureTestCase;

public class RenameRefTest extends SwaggerLightCodeInsightFixtureTestCase {

    private void doTest(final String newName, final String beforeFileName, final String afterFileName) {
        myFixture.configureByFile("rename/" + beforeFileName);
        myFixture.renameElementAtCaret(newName);
        myFixture.checkResultByFile("rename/" + afterFileName);
    }

    public void testRenameDefinition() {
        doTest("NewPets", "rename_definition_ref_declaration.json", "rename_definition_ref_declaration_after.json");
        doTest("NewPets", "rename_definition_ref_declaration.yaml", "rename_definition_ref_declaration_after.yaml");

        doTest("NewPets", "rename_definition_ref_reference.json", "rename_definition_ref_reference_after.json");
        doTest("NewPets", "rename_definition_ref_reference.yaml", "rename_definition_ref_reference_after.yaml");
    }

    public void testRenameParameter() {
        doTest("newName", "rename_parameter_ref_declaration.json", "rename_parameter_ref_declaration_after.json");
        doTest("newName", "rename_parameter_ref_declaration.yaml", "rename_parameter_ref_declaration_after.yaml");

        doTest("newName", "rename_parameter_ref_reference.json", "rename_parameter_ref_reference_after.json");
        doTest("newName", "rename_parameter_ref_reference.yaml", "rename_parameter_ref_reference_after.yaml");
    }

}
