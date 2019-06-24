package org.zalando.intellij.swagger.completion.field.completion.swagger.yaml;

import com.intellij.openapi.vfs.VirtualFile;
import org.zalando.intellij.swagger.SwaggerLightCodeInsightFixtureTestCase;
import org.zalando.intellij.swagger.assertion.AssertableList;

public class DefinitionsFileCompletionTest extends SwaggerLightCodeInsightFixtureTestCase {

  private static final String PARTIAL_FILES_PATH = "completion/field/swagger/partial/yaml";

  public void testThatAutoCompletionWorksForFileWithSingleDefinitionForYaml() {
    myFixture.copyFileToProject(PARTIAL_FILES_PATH + "/pet.yaml", "pet.yaml");
    final VirtualFile swaggerFile =
        myFixture.copyFileToProject(PARTIAL_FILES_PATH + "/swagger.yaml", "swagger.yaml");
    myFixture.configureFromExistingVirtualFile(swaggerFile);

    final AssertableList completions =
        new AssertableList(myFixture.getCompletionVariants("pet.yaml"));

    assertSchemaCompletions(completions);
  }

  public void testThatAutoCompletionWorksForFileWithSingleDefinitionForYml() {
    myFixture.copyFileToProject(PARTIAL_FILES_PATH + "/pet.yaml", "pet.yml");
    final VirtualFile swaggerFile =
        myFixture.copyFileToProject(PARTIAL_FILES_PATH + "/swagger.yml", "swagger.yml");
    myFixture.configureFromExistingVirtualFile(swaggerFile);

    final AssertableList completions =
        new AssertableList(myFixture.getCompletionVariants("pet.yml"));

    assertSchemaCompletions(completions);
  }

  public void testThatAutoCompletionWorksForFileWithMultipleDefinitionsInRootForYamlFile() {
    myFixture.copyFileToProject(
        PARTIAL_FILES_PATH + "/definitions_in_root.yaml", "definitions_in_root.yaml");
    final VirtualFile swaggerFile =
        myFixture.copyFileToProject(PARTIAL_FILES_PATH + "/swagger.yaml", "swagger.yaml");
    myFixture.configureFromExistingVirtualFile(swaggerFile);

    final AssertableList completions =
        new AssertableList(myFixture.getCompletionVariants("definitions_in_root.yaml"));

    assertSchemaCompletions(completions);
  }

  public void testThatAutoCompletionWorksForFileWithMultipleDefinitionsNotInRootForYaml() {
    myFixture.copyFileToProject(
        PARTIAL_FILES_PATH + "/definitions_not_in_root.yaml", "definitions_not_in_root.yaml");
    final VirtualFile swaggerFile =
        myFixture.copyFileToProject(PARTIAL_FILES_PATH + "/swagger.yaml", "swagger.yaml");
    myFixture.configureFromExistingVirtualFile(swaggerFile);

    final AssertableList completions =
        new AssertableList(myFixture.getCompletionVariants("definitions_not_in_root.yaml"));

    assertSchemaCompletions(completions);
  }

  public void testThatAutoCompletionWorksForFileWithMultipleDefinitionsInRootForYmlFile() {
    myFixture.copyFileToProject(
        PARTIAL_FILES_PATH + "/definitions_in_root.yaml", "definitions_in_root.yml");
    final VirtualFile swaggerFile =
        myFixture.copyFileToProject(PARTIAL_FILES_PATH + "/swagger.yml", "swagger.yml");
    myFixture.configureFromExistingVirtualFile(swaggerFile);

    final AssertableList completions =
        new AssertableList(myFixture.getCompletionVariants("definitions_in_root.yml"));

    assertSchemaCompletions(completions);
  }

  public void testThatAutoCompletionWorksForFileWithMultipleDefinitionsNotInRootForYml() {
    myFixture.copyFileToProject(
        PARTIAL_FILES_PATH + "/definitions_not_in_root.yaml", "definitions_not_in_root.yml");
    final VirtualFile swaggerFile =
        myFixture.copyFileToProject(PARTIAL_FILES_PATH + "/swagger.yml", "swagger.yml");
    myFixture.configureFromExistingVirtualFile(swaggerFile);

    final AssertableList completions =
        new AssertableList(myFixture.getCompletionVariants("definitions_not_in_root.yml"));

    assertSchemaCompletions(completions);
  }

  public void testThatAutoCompletionWorksForReferencedJsonFile() {
    myFixture.copyFileToProject(PARTIAL_FILES_PATH + "/pet.json", "pet.json");
    final VirtualFile swaggerFile =
        myFixture.copyFileToProject(PARTIAL_FILES_PATH + "/swagger.yaml", "swagger.yaml");
    myFixture.configureFromExistingVirtualFile(swaggerFile);

    final AssertableList completions =
        new AssertableList(myFixture.getCompletionVariants("pet.json"));

    assertSchemaCompletions(completions);
  }

  private void assertSchemaCompletions(final AssertableList completions) {
    completions
        .assertContains(
            "$ref",
            "format",
            "title",
            "description",
            "default",
            "multipleOf",
            "maximum",
            "exclusiveMaximum",
            "minimum",
            "exclusiveMinimum",
            "maxLength",
            "minLength",
            "pattern",
            "maxItems",
            "minItems",
            "uniqueItems",
            "maxProperties",
            "minProperties",
            "required",
            "enum",
            "type",
            "items",
            "allOf",
            "properties",
            "additionalProperties",
            "discriminator",
            "readOnly",
            "xml",
            "externalDocs",
            "example")
        .isOfSize(30);
  }
}
