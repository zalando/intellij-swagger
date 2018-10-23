package org.zalando.intellij.swagger.rename.json;

import com.intellij.openapi.vfs.VirtualFile;
import org.zalando.intellij.swagger.SwaggerLightCodeInsightFixtureTestCase;

public class RenameFileRefTest extends SwaggerLightCodeInsightFixtureTestCase {

  private static final String FILES_PATH = "rename/swagger/file/json/";

  public void testRenameJsonFileReference() {
    myFixture.copyFileToProject(FILES_PATH + "empty.json", "definitions/pet.json");
    final VirtualFile swaggerFile =
        myFixture.copyFileToProject(FILES_PATH + "rename_file_reference.json", "swagger.json");
    myFixture.configureFromExistingVirtualFile(swaggerFile);
    myFixture.renameElementAtCaret("newName.json");
    myFixture.checkResultByFile(
        "swagger.json", FILES_PATH + "rename_file_reference_after.json", true);
    assertNotNull(myFixture.findFileInTempDir("definitions/newName.json"));
    assertNull(myFixture.findFileInTempDir("definitions/pet.json"));
  }

  public void testRenameRefInReferencedFile() {
    final VirtualFile definitionsFile =
        myFixture.copyFileToProject(
            FILES_PATH + "ref_in_referenced_file_with_caret.json", "definitions.json");
    final VirtualFile swaggerFile =
        myFixture.copyFileToProject(
            FILES_PATH + "rename_ref_in_referenced_file.json", "swagger.json");
    myFixture.configureFromExistingVirtualFile(swaggerFile);
    myFixture.configureFromExistingVirtualFile(definitionsFile);
    myFixture.renameElementAtCaret("newName");
    myFixture.checkResultByFile(
        "definitions.json", FILES_PATH + "ref_in_referenced_file_with_caret_after.json", true);
  }
}
