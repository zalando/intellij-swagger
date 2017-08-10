package org.zalando.intellij.swagger.completion.field.swagger.yaml;

import com.intellij.openapi.vfs.VirtualFile;
import org.zalando.intellij.swagger.SwaggerLightCodeInsightFixtureTestCase;
import org.zalando.intellij.swagger.assertion.AssertableList;

public class DefinitionsFileCompletionTest extends SwaggerLightCodeInsightFixtureTestCase {

    private static final String PARTIAL_FILES_PATH = "completion/field/swagger/partial/yaml";

    public void testThatAutoCompletionWorksForFileWithSingleDefinitionForYaml() {
        myFixture.copyFileToProject(PARTIAL_FILES_PATH + "/pet.yaml", "pet.yaml");
        final VirtualFile swaggerFile = myFixture.copyFileToProject(PARTIAL_FILES_PATH + "/swagger.yaml", "swagger.yaml");
        myFixture.configureFromExistingVirtualFile(swaggerFile);

        final AssertableList completions = new AssertableList(myFixture.getCompletionVariants("pet.yaml"));

        completions.assertContains("$ref", "format", "title", "description", "default", "multipleOf", "maximum",
                "exclusiveMaximum", "minimum", "exclusiveMinimum", "maxLength", "minLength", "pattern",
                "maxItems", "minItems", "uniqueItems", "maxProperties", "minProperties", "required", "enum",
                "type", "items", "allOf", "properties", "additionalProperties", "discriminator", "readOnly",
                "xml", "externalDocs", "example")
                .isOfSize(30);

    }

    public void testThatAutoCompletionWorksForFileWithSingleDefinitionForYml() {
        myFixture.copyFileToProject(PARTIAL_FILES_PATH + "/pet.yaml", "pet.yml");
        final VirtualFile swaggerFile = myFixture.copyFileToProject(PARTIAL_FILES_PATH + "/swagger.yml", "swagger.yml");
        myFixture.configureFromExistingVirtualFile(swaggerFile);

        final AssertableList completions = new AssertableList(myFixture.getCompletionVariants("pet.yml"));

        completions.assertContains("$ref", "format", "title", "description", "default", "multipleOf", "maximum",
                "exclusiveMaximum", "minimum", "exclusiveMinimum", "maxLength", "minLength", "pattern",
                "maxItems", "minItems", "uniqueItems", "maxProperties", "minProperties", "required", "enum",
                "type", "items", "allOf", "properties", "additionalProperties", "discriminator", "readOnly",
                "xml", "externalDocs", "example")
                .isOfSize(30);
    }

    public void testThatAutoCompletionWorksForFileWithMultipleDefinitionsInRootForYamlFile() {
        myFixture.copyFileToProject(PARTIAL_FILES_PATH + "/definitions_in_root.yaml", "definitions_in_root.yaml");
        final VirtualFile swaggerFile = myFixture.copyFileToProject(PARTIAL_FILES_PATH + "/swagger.yaml", "swagger.yaml");
        myFixture.configureFromExistingVirtualFile(swaggerFile);

        final AssertableList completions = new AssertableList(myFixture.getCompletionVariants("definitions_in_root.yaml"));

        completions.assertContains("$ref", "format", "title", "description", "default", "multipleOf", "maximum",
                "exclusiveMaximum", "minimum", "exclusiveMinimum", "maxLength", "minLength", "pattern",
                "maxItems", "minItems", "uniqueItems", "maxProperties", "minProperties", "required", "enum",
                "type", "items", "allOf", "properties", "additionalProperties", "discriminator", "readOnly",
                "xml", "externalDocs", "example")
                .isOfSize(30);
    }

    public void testThatAutoCompletionWorksForFileWithMultipleDefinitionsNotInRootForYaml() {
        myFixture.copyFileToProject(PARTIAL_FILES_PATH + "/definitions_not_in_root.yaml", "definitions_not_in_root.yaml");
        final VirtualFile swaggerFile = myFixture.copyFileToProject(PARTIAL_FILES_PATH + "/swagger.yaml", "swagger.yaml");
        myFixture.configureFromExistingVirtualFile(swaggerFile);

        final AssertableList completions = new AssertableList(myFixture.getCompletionVariants("definitions_not_in_root.yaml"));

        completions.assertContains("$ref", "format", "title", "description", "default", "multipleOf", "maximum",
                "exclusiveMaximum", "minimum", "exclusiveMinimum", "maxLength", "minLength", "pattern",
                "maxItems", "minItems", "uniqueItems", "maxProperties", "minProperties", "required", "enum",
                "type", "items", "allOf", "properties", "additionalProperties", "discriminator", "readOnly",
                "xml", "externalDocs", "example")
                .isOfSize(30);
    }

    public void testThatAutoCompletionWorksForFileWithMultipleDefinitionsInRootForYmlFile() {
        myFixture.copyFileToProject(PARTIAL_FILES_PATH + "/definitions_in_root.yaml", "definitions_in_root.yml");
        final VirtualFile swaggerFile = myFixture.copyFileToProject(PARTIAL_FILES_PATH + "/swagger.yml", "swagger.yml");
        myFixture.configureFromExistingVirtualFile(swaggerFile);

        final AssertableList completions = new AssertableList(myFixture.getCompletionVariants("definitions_in_root.yml"));

        completions.assertContains("$ref", "format", "title", "description", "default", "multipleOf", "maximum",
                "exclusiveMaximum", "minimum", "exclusiveMinimum", "maxLength", "minLength", "pattern",
                "maxItems", "minItems", "uniqueItems", "maxProperties", "minProperties", "required", "enum",
                "type", "items", "allOf", "properties", "additionalProperties", "discriminator", "readOnly",
                "xml", "externalDocs", "example")
                .isOfSize(30);
    }

    public void testThatAutoCompletionWorksForFileWithMultipleDefinitionsNotInRootForYml() {
        myFixture.copyFileToProject(PARTIAL_FILES_PATH + "/definitions_not_in_root.yaml", "definitions_not_in_root.yml");
        final VirtualFile swaggerFile = myFixture.copyFileToProject(PARTIAL_FILES_PATH + "/swagger.yml", "swagger.yml");
        myFixture.configureFromExistingVirtualFile(swaggerFile);

        final AssertableList completions = new AssertableList(myFixture.getCompletionVariants("definitions_not_in_root.yml"));

        completions.assertContains("$ref", "format", "title", "description", "default", "multipleOf", "maximum",
                "exclusiveMaximum", "minimum", "exclusiveMinimum", "maxLength", "minLength", "pattern",
                "maxItems", "minItems", "uniqueItems", "maxProperties", "minProperties", "required", "enum",
                "type", "items", "allOf", "properties", "additionalProperties", "discriminator", "readOnly",
                "xml", "externalDocs", "example")
                .isOfSize(30);
    }
}
