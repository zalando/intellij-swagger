package org.zalando.intellij.swagger.completion.field.completion.swagger.yaml;

import com.intellij.openapi.vfs.VirtualFile;
import org.zalando.intellij.swagger.SwaggerLightCodeInsightFixtureTestCase;
import org.zalando.intellij.swagger.assertion.AssertableList;

public class PathsFileCompletionTest extends SwaggerLightCodeInsightFixtureTestCase {

  private static final String PARTIAL_FILES_PATH = "completion/field/swagger/partial/yaml";

  public void testThatAutoCompletionWorksForFileWithSingleDefinitionForYaml() {
    myFixture.copyFileToProject(PARTIAL_FILES_PATH + "/path.yaml", "path.yaml");
    final VirtualFile swaggerFile =
        myFixture.copyFileToProject(PARTIAL_FILES_PATH + "/swagger.yaml", "swagger.yaml");
    myFixture.configureFromExistingVirtualFile(swaggerFile);

    final AssertableList completions =
        new AssertableList(myFixture.getCompletionVariants("path.yaml"));

    completions
        .assertContains(
            "$ref", "get", "put", "post", "delete", "options", "head", "patch", "parameters")
        .isOfSize(9);
  }

  public void testThatAutoCompletionWorksForFileWithMultiplepathDefinitionsInRootForYaml() {
    myFixture.copyFileToProject(
        PARTIAL_FILES_PATH + "/path_definitions_in_root.yaml", "path_definitions_in_root.yaml");
    final VirtualFile swaggerFile =
        myFixture.copyFileToProject(PARTIAL_FILES_PATH + "/swagger.yaml", "swagger.yaml");
    myFixture.configureFromExistingVirtualFile(swaggerFile);

    final AssertableList completions =
        new AssertableList(myFixture.getCompletionVariants("path_definitions_in_root.yaml"));

    completions
        .assertContains(
            "$ref", "get", "put", "post", "delete", "options", "head", "patch", "parameters")
        .isOfSize(9);
  }

  public void testThatAutoCompletionWorksForFileWithMultiplepathDefinitionsNotInRootForYaml() {
    myFixture.copyFileToProject(
        PARTIAL_FILES_PATH + "/path_definitions_not_in_root.yaml",
        "path_definitions_not_in_root.yaml");
    final VirtualFile swaggerFile =
        myFixture.copyFileToProject(PARTIAL_FILES_PATH + "/swagger.yaml", "swagger.yaml");
    myFixture.configureFromExistingVirtualFile(swaggerFile);

    final AssertableList completions =
        new AssertableList(myFixture.getCompletionVariants("path_definitions_not_in_root.yaml"));

    completions
        .assertContains(
            "$ref", "get", "put", "post", "delete", "options", "head", "patch", "parameters")
        .isOfSize(9);
  }
}
