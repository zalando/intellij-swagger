package org.zalando.intellij.swagger.completion.field.swagger.yaml;

import com.intellij.openapi.vfs.VirtualFile;
import org.zalando.intellij.swagger.SwaggerLightCodeInsightFixtureTestCase;
import org.zalando.intellij.swagger.assertion.AssertableList;

public class ParametersFileCompletionTest extends SwaggerLightCodeInsightFixtureTestCase {

    private static final String PARTIAL_FILES_PATH = "completion/field/swagger/partial/yaml";

    public void testThatAutoCompletionWorksForFileWithSingleParameterDefinitionForYaml() {
        myFixture.copyFileToProject(PARTIAL_FILES_PATH + "/parameter.yaml", "parameter.yaml");
        final VirtualFile swaggerFile = myFixture.copyFileToProject(PARTIAL_FILES_PATH + "/swagger.yaml", "swagger.yaml");
        myFixture.configureFromExistingVirtualFile(swaggerFile);

        final AssertableList completions = new AssertableList(myFixture.getCompletionVariants("parameter.yaml"));

        completions.assertContains("name", "in", "description", "required", "schema", "type", "format",
                "allowEmptyValue", "items", "collectionFormat", "default", "maximum", "exclusiveMaximum",
                "minimum", "exclusiveMinimum", "maxLength", "minLength", "pattern", "maxItems", "minItems",
                "uniqueItems", "enum", "multipleOf")
                .isOfSize(23);
    }

    public void testThatAutoCompletionWorksForFileWithMultipleParameterDefinitionsInRootForYaml() {
        myFixture.copyFileToProject(PARTIAL_FILES_PATH + "/parameter_definitions_in_root.yaml", "parameter_definitions_in_root.yaml");
        final VirtualFile swaggerFile = myFixture.copyFileToProject(PARTIAL_FILES_PATH + "/swagger.yaml", "swagger.yaml");
        myFixture.configureFromExistingVirtualFile(swaggerFile);

        final AssertableList completions = new AssertableList(myFixture.getCompletionVariants("parameter_definitions_in_root.yaml"));

        completions.assertContains("name", "in", "description", "required", "schema", "type", "format",
                "allowEmptyValue", "items", "collectionFormat", "default", "maximum", "exclusiveMaximum",
                "minimum", "exclusiveMinimum", "maxLength", "minLength", "pattern", "maxItems", "minItems",
                "uniqueItems", "enum", "multipleOf")
                .isOfSize(23);
    }

    public void testThatAutoCompletionWorksForFileWithMultipleParameterDefinitionsNotInRootForYaml() {
        myFixture.copyFileToProject(PARTIAL_FILES_PATH + "/parameter_definitions_not_in_root.yaml", "parameter_definitions_not_in_root.yaml");
        final VirtualFile swaggerFile = myFixture.copyFileToProject(PARTIAL_FILES_PATH + "/swagger.yaml", "swagger.yaml");
        myFixture.configureFromExistingVirtualFile(swaggerFile);

        final AssertableList completions = new AssertableList(myFixture.getCompletionVariants("parameter_definitions_not_in_root.yaml"));

        completions.assertContains("name", "in", "description", "required", "schema", "type", "format",
                "allowEmptyValue", "items", "collectionFormat", "default", "maximum", "exclusiveMaximum",
                "minimum", "exclusiveMinimum", "maxLength", "minLength", "pattern", "maxItems", "minItems",
                "uniqueItems", "enum", "multipleOf")
                .isOfSize(23);
    }

    public void testThatAutoCompletionWorksForFileWithMultipleParameterDefinitionsInRootForYml() {
        myFixture.copyFileToProject(PARTIAL_FILES_PATH + "/parameter_definitions_in_root.yaml", "parameter_definitions_in_root.yml");
        final VirtualFile swaggerFile = myFixture.copyFileToProject(PARTIAL_FILES_PATH + "/swagger.yml", "swagger.yml");
        myFixture.configureFromExistingVirtualFile(swaggerFile);

        final AssertableList completions = new AssertableList(myFixture.getCompletionVariants("parameter_definitions_in_root.yml"));

        completions.assertContains("name", "in", "description", "required", "schema", "type", "format",
                "allowEmptyValue", "items", "collectionFormat", "default", "maximum", "exclusiveMaximum",
                "minimum", "exclusiveMinimum", "maxLength", "minLength", "pattern", "maxItems", "minItems",
                "uniqueItems", "enum", "multipleOf")
                .isOfSize(23);
    }

    public void testThatAutoCompletionWorksForFileWithMultipleParameterDefinitionsNotInRootForYml() {
        myFixture.copyFileToProject(PARTIAL_FILES_PATH + "/parameter_definitions_not_in_root.yaml", "parameter_definitions_not_in_root.yml");
        final VirtualFile swaggerFile = myFixture.copyFileToProject(PARTIAL_FILES_PATH + "/swagger.yml", "swagger.yml");
        myFixture.configureFromExistingVirtualFile(swaggerFile);

        final AssertableList completions = new AssertableList(myFixture.getCompletionVariants("parameter_definitions_not_in_root.yml"));

        completions.assertContains("name", "in", "description", "required", "schema", "type", "format",
                "allowEmptyValue", "items", "collectionFormat", "default", "maximum", "exclusiveMaximum",
                "minimum", "exclusiveMinimum", "maxLength", "minLength", "pattern", "maxItems", "minItems",
                "uniqueItems", "enum", "multipleOf")
                .isOfSize(23);
    }
}
