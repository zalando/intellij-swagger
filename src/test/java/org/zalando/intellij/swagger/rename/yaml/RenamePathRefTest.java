package org.zalando.intellij.swagger.rename.yaml;

import com.intellij.openapi.vfs.VirtualFile;
import org.zalando.intellij.swagger.SwaggerLightCodeInsightFixtureTestCase;

public class RenamePathRefTest extends SwaggerLightCodeInsightFixtureTestCase {

    private static final String FILES_PATH = "rename/swagger/paths/yaml/";

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
}
