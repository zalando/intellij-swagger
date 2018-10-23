package org.zalando.intellij.swagger.rename.json;

import com.intellij.openapi.vfs.VirtualFile;
import org.zalando.intellij.swagger.SwaggerLightCodeInsightFixtureTestCase;

public class RenamePathRefTest extends SwaggerLightCodeInsightFixtureTestCase {

  private static final String FILES_PATH = "rename/swagger/paths/json/";

  public void testRenameExternalPathDefinitionWhereDefinitionsAreInRoot() {
    myFixture.copyFileToProject(FILES_PATH + "path_definitions_in_root.json", "paths.json");
    final VirtualFile swaggerFile =
        myFixture.copyFileToProject(
            FILES_PATH + "external_definition_path_definitions_in_root_with_caret.json",
            "swagger.json");
    myFixture.configureFromExistingVirtualFile(swaggerFile);
    myFixture.renameElementAtCaret("newName");
    myFixture.checkResultByFile(
        "swagger.json",
        FILES_PATH + "external_definition_path_definitions_in_root_with_caret_after.json",
        true);
    myFixture.checkResultByFile(
        "paths.json", FILES_PATH + "path_definitions_in_root_after.json", true);
  }

  public void testRenameExternalPathDefinitionWhereDefinitionsAreNotInRoot() {
    myFixture.copyFileToProject(FILES_PATH + "path_definitions_not_in_root.json", "paths.json");
    final VirtualFile swaggerFile =
        myFixture.copyFileToProject(
            FILES_PATH + "external_definition_path_definitions_not_in_root_with_caret.json",
            "swagger.json");
    myFixture.configureFromExistingVirtualFile(swaggerFile);
    myFixture.renameElementAtCaret("newName");
    myFixture.checkResultByFile(
        "swagger.json",
        FILES_PATH + "external_definition_path_definitions_not_in_root_with_caret_after.json",
        true);
    myFixture.checkResultByFile(
        "paths.json", FILES_PATH + "path_definitions_not_in_root_after.json", true);
  }
}
