package org.zalando.intellij.swagger.completion.field;

import com.intellij.openapi.vfs.VirtualFile;
import org.zalando.intellij.swagger.SwaggerLightCodeInsightFixtureTestCase;
import org.zalando.intellij.swagger.assertion.AssertableList;

public class ReferencedDefinitionsFileFieldCompletionTest extends SwaggerLightCodeInsightFixtureTestCase {

    private static final String PARTIAL_FILES_PATH = "completion/field/partial";

    public void testThatDefinitionFieldsAreCompletedForPartialJsonSwaggerFile() {
        myFixture.copyFileToProject(PARTIAL_FILES_PATH + "/pet.json", "pet.json");
        final VirtualFile swaggerFile = myFixture.copyFileToProject(PARTIAL_FILES_PATH + "/swagger.json", "swagger.json");
        myFixture.configureFromExistingVirtualFile(swaggerFile);

        final AssertableList completions = new AssertableList(myFixture.getCompletionVariants("pet.json"));

        completions.assertContains("$ref", "format", "title", "description", "default", "multipleOf", "maximum",
                "exclusiveMaximum", "minimum", "exclusiveMinimum", "maxLength", "minLength", "pattern",
                "maxItems", "minItems", "uniqueItems", "maxProperties", "minProperties", "required", "enum",
                "type", "items", "allOf", "properties", "additionalProperties", "discriminator", "readOnly",
                "xml", "externalDocs", "example")
                .isOfSize(30);

    }

    public void testThatDefinitionFieldsAreCompletedForPartialJsonSwaggerFileWithDefinitionsInRoot() {
        myFixture.copyFileToProject(PARTIAL_FILES_PATH + "/common.json", "common.json");
        final VirtualFile swaggerFile = myFixture.copyFileToProject(PARTIAL_FILES_PATH + "/swagger.json", "swagger.json");
        myFixture.configureFromExistingVirtualFile(swaggerFile);

        final AssertableList completions = new AssertableList(myFixture.getCompletionVariants("common.json"));

        completions.assertContains("$ref", "format", "title", "description", "default", "multipleOf", "maximum",
                "exclusiveMaximum", "minimum", "exclusiveMinimum", "maxLength", "minLength", "pattern",
                "maxItems", "minItems", "uniqueItems", "maxProperties", "minProperties", "required", "enum",
                "type", "items", "allOf", "properties", "additionalProperties", "discriminator", "readOnly",
                "xml", "externalDocs", "example")
                .isOfSize(30);

    }

    public void testThatDefinitionFieldsAreCompletedForPartialJsonSwaggerFileWithDefinitionsNotInRoot() {
        myFixture.copyFileToProject(PARTIAL_FILES_PATH + "/common2.json", "common2.json");
        final VirtualFile swaggerFile = myFixture.copyFileToProject(PARTIAL_FILES_PATH + "/swagger.json", "swagger.json");
        myFixture.configureFromExistingVirtualFile(swaggerFile);

        final AssertableList completions = new AssertableList(myFixture.getCompletionVariants("common2.json"));

        completions.assertContains("$ref", "format", "title", "description", "default", "multipleOf", "maximum",
                "exclusiveMaximum", "minimum", "exclusiveMinimum", "maxLength", "minLength", "pattern",
                "maxItems", "minItems", "uniqueItems", "maxProperties", "minProperties", "required", "enum",
                "type", "items", "allOf", "properties", "additionalProperties", "discriminator", "readOnly",
                "xml", "externalDocs", "example")
                .isOfSize(30);

    }

    public void testThatDefinitionFieldsAreCompletedForPartialYamlSwaggerFile() {
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

    public void testThatDefinitionFieldsAreCompletedForPartialYamlSwaggerFileWithYmlExtension() {
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
}
