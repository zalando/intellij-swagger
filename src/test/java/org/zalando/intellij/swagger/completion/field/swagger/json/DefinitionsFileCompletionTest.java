package org.zalando.intellij.swagger.completion.field.swagger.json;

import com.intellij.openapi.vfs.VirtualFile;
import org.zalando.intellij.swagger.SwaggerLightCodeInsightFixtureTestCase;
import org.zalando.intellij.swagger.assertion.AssertableList;

public class DefinitionsFileCompletionTest extends SwaggerLightCodeInsightFixtureTestCase {

    private static final String PARTIAL_FILES_PATH = "completion/field/swagger/partial/json";

    public void testThatAutoCompletionWorksForFileWithSingleDefinition() {
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

    public void testThatAutoCompletionWorksForFileWithMultipleDefinitionsInRoot() {
        myFixture.copyFileToProject(PARTIAL_FILES_PATH + "/definitions_in_root.json", "definitions_in_root.json");
        final VirtualFile swaggerFile = myFixture.copyFileToProject(PARTIAL_FILES_PATH + "/swagger.json", "swagger.json");
        myFixture.configureFromExistingVirtualFile(swaggerFile);

        final AssertableList completions = new AssertableList(myFixture.getCompletionVariants("definitions_in_root.json"));

        completions.assertContains("$ref", "format", "title", "description", "default", "multipleOf", "maximum",
                "exclusiveMaximum", "minimum", "exclusiveMinimum", "maxLength", "minLength", "pattern",
                "maxItems", "minItems", "uniqueItems", "maxProperties", "minProperties", "required", "enum",
                "type", "items", "allOf", "properties", "additionalProperties", "discriminator", "readOnly",
                "xml", "externalDocs", "example")
                .isOfSize(30);

    }

    public void testThatAutoCompletionWorksForFileWithMultipleDefinitionsNotInRoot() {
        myFixture.copyFileToProject(PARTIAL_FILES_PATH + "/definitions_not_in_root.json", "definitions_not_in_root.json");
        final VirtualFile swaggerFile = myFixture.copyFileToProject(PARTIAL_FILES_PATH + "/swagger.json", "swagger.json");
        myFixture.configureFromExistingVirtualFile(swaggerFile);

        final AssertableList completions = new AssertableList(myFixture.getCompletionVariants("definitions_not_in_root.json"));

        completions.assertContains("$ref", "format", "title", "description", "default", "multipleOf", "maximum",
                "exclusiveMaximum", "minimum", "exclusiveMinimum", "maxLength", "minLength", "pattern",
                "maxItems", "minItems", "uniqueItems", "maxProperties", "minProperties", "required", "enum",
                "type", "items", "allOf", "properties", "additionalProperties", "discriminator", "readOnly",
                "xml", "externalDocs", "example")
                .isOfSize(30);

    }

}
