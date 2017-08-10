package org.zalando.intellij.swagger.completion.field.swagger.yaml;

import com.intellij.openapi.vfs.VirtualFile;
import org.zalando.intellij.swagger.SwaggerLightCodeInsightFixtureTestCase;
import org.zalando.intellij.swagger.assertion.AssertableList;

public class ResponsesFileCompletionTest extends SwaggerLightCodeInsightFixtureTestCase {

    private static final String PARTIAL_FILES_PATH = "completion/field/swagger/partial/yaml";

    public void testThatAutoCompletionWorksForFileWithSingleDefinitionForYaml() {
        myFixture.copyFileToProject(PARTIAL_FILES_PATH + "/response.yaml", "response.yaml");
        final VirtualFile swaggerFile = myFixture.copyFileToProject(PARTIAL_FILES_PATH + "/swagger.yaml", "swagger.yaml");
        myFixture.configureFromExistingVirtualFile(swaggerFile);

        final AssertableList completions = new AssertableList(myFixture.getCompletionVariants("response.yaml"));

        completions.assertContains("description", "schema", "headers", "examples")
                .isOfSize(4);
    }

    public void testThatAutoCompletionWorksForFileWithMultipleResponseDefinitionsInRootForYaml() {
        myFixture.copyFileToProject(PARTIAL_FILES_PATH + "/response_definitions_in_root.yaml", "response_definitions_in_root.yaml");
        final VirtualFile swaggerFile = myFixture.copyFileToProject(PARTIAL_FILES_PATH + "/swagger.yaml", "swagger.yaml");
        myFixture.configureFromExistingVirtualFile(swaggerFile);

        final AssertableList completions = new AssertableList(myFixture.getCompletionVariants("response_definitions_in_root.yaml"));

        completions.assertContains("description", "schema", "headers", "examples")
                .isOfSize(4);
    }

    public void testThatAutoCompletionWorksForFileWithMultipleResponseDefinitionsNotInRootForYaml() {
        myFixture.copyFileToProject(PARTIAL_FILES_PATH + "/response_definitions_not_in_root.yaml", "response_definitions_not_in_root.yaml");
        final VirtualFile swaggerFile = myFixture.copyFileToProject(PARTIAL_FILES_PATH + "/swagger.yaml", "swagger.yaml");
        myFixture.configureFromExistingVirtualFile(swaggerFile);

        final AssertableList completions = new AssertableList(myFixture.getCompletionVariants("response_definitions_not_in_root.yaml"));

        completions.assertContains("description", "schema", "headers", "examples")
                .isOfSize(4);
    }

    public void testThatAutoCompletionWorksForFileWithSingleDefinitionForYml() {
        myFixture.copyFileToProject(PARTIAL_FILES_PATH + "/response.yaml", "response.yml");
        final VirtualFile swaggerFile = myFixture.copyFileToProject(PARTIAL_FILES_PATH + "/swagger.yml", "swagger.yml");
        myFixture.configureFromExistingVirtualFile(swaggerFile);

        final AssertableList completions = new AssertableList(myFixture.getCompletionVariants("response.yml"));

        completions.assertContains("description", "schema", "headers", "examples")
                .isOfSize(4);
    }

    public void testThatAutoCompletionWorksForFileWithMultipleResponseDefinitionsInRootForYml() {
        myFixture.copyFileToProject(PARTIAL_FILES_PATH + "/response_definitions_in_root.yaml", "response_definitions_in_root.yml");
        final VirtualFile swaggerFile = myFixture.copyFileToProject(PARTIAL_FILES_PATH + "/swagger.yml", "swagger.yml");
        myFixture.configureFromExistingVirtualFile(swaggerFile);

        final AssertableList completions = new AssertableList(myFixture.getCompletionVariants("response_definitions_in_root.yml"));

        completions.assertContains("description", "schema", "headers", "examples")
                .isOfSize(4);
    }

    public void testThatAutoCompletionWorksForFileWithMultipleResponseDefinitionsNotInRootForYml() {
        myFixture.copyFileToProject(PARTIAL_FILES_PATH + "/response_definitions_not_in_root.yaml", "response_definitions_not_in_root.yml");
        final VirtualFile swaggerFile = myFixture.copyFileToProject(PARTIAL_FILES_PATH + "/swagger.yml", "swagger.yml");
        myFixture.configureFromExistingVirtualFile(swaggerFile);

        final AssertableList completions = new AssertableList(myFixture.getCompletionVariants("response_definitions_not_in_root.yml"));

        completions.assertContains("description", "schema", "headers", "examples")
                .isOfSize(4);
    }
}
