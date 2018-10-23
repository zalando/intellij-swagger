package org.zalando.intellij.swagger.rename.yaml;

import com.intellij.openapi.vfs.VirtualFile;
import org.zalando.intellij.swagger.SwaggerLightCodeInsightFixtureTestCase;

public class RenameFileRefTest extends SwaggerLightCodeInsightFixtureTestCase {

  private static final String FILES_PATH = "rename/swagger/file/yaml/";

  public void testRenameFileReference() {
    myFixture.copyFileToProject(FILES_PATH + "empty.yaml", "definitions/pet.yaml");
    final VirtualFile swaggerFile =
        myFixture.copyFileToProject(FILES_PATH + "rename_file_reference.yaml", "swagger.yaml");
    myFixture.configureFromExistingVirtualFile(swaggerFile);
    myFixture.renameElementAtCaret("newName.yaml");
    myFixture.checkResultByFile(
        "swagger.yaml", FILES_PATH + "rename_file_reference_after.yaml", true);
    assertNotNull(myFixture.findFileInTempDir("definitions/newName.yaml"));
    assertNull(myFixture.findFileInTempDir("definitions/pet.yaml"));
  }

  public void testRenameRefInReferencedFile() {
    final VirtualFile definitionsFile =
        myFixture.copyFileToProject(
            FILES_PATH + "ref_in_referenced_file_with_caret.yaml", "definitions.yaml");
    final VirtualFile swaggerFile =
        myFixture.copyFileToProject(
            FILES_PATH + "rename_ref_in_referenced_file.yaml", "swagger.yaml");
    myFixture.configureFromExistingVirtualFile(swaggerFile);
    myFixture.configureFromExistingVirtualFile(definitionsFile);
    myFixture.renameElementAtCaret("newName");
    myFixture.checkResultByFile(
        "definitions.yaml", FILES_PATH + "ref_in_referenced_file_with_caret_after.yaml", true);
  }
}
