package org.zalando.intellij.swagger.completion.field.swagger.json;

import com.intellij.openapi.vfs.VirtualFile;
import org.zalando.intellij.swagger.SwaggerLightCodeInsightFixtureTestCase;
import org.zalando.intellij.swagger.assertion.AssertableList;

public class ResponsesFileCompletionTest extends SwaggerLightCodeInsightFixtureTestCase {

    private static final String PARTIAL_FILES_PATH = "completion/field/swagger/partial/json";

    public void testThatAutoCompletionWorksForFileWithSingleDefinition() {
        myFixture.copyFileToProject(PARTIAL_FILES_PATH + "/response.json", "response.json");
        final VirtualFile swaggerFile = myFixture.copyFileToProject(PARTIAL_FILES_PATH + "/swagger.json", "swagger.json");
        myFixture.configureFromExistingVirtualFile(swaggerFile);

        final AssertableList completions = new AssertableList(myFixture.getCompletionVariants("response.json"));

        completions.assertContains("description", "schema", "headers", "examples")
                .isOfSize(4);
    }

    public void testThatAutoCompletionWorksForFileWithMultipleResponseDefinitionsInRoot() {
        myFixture.copyFileToProject(PARTIAL_FILES_PATH + "/response_definitions_in_root.json", "response_definitions_in_root.json");
        final VirtualFile swaggerFile = myFixture.copyFileToProject(PARTIAL_FILES_PATH + "/swagger.json", "swagger.json");
        myFixture.configureFromExistingVirtualFile(swaggerFile);

        final AssertableList completions = new AssertableList(myFixture.getCompletionVariants("response_definitions_in_root.json"));

        completions.assertContains("description", "schema", "headers", "examples")
                .isOfSize(4);
    }

    public void testThatAutoCompletionWorksForFileWithMultipleResponseDefinitionsNotInRoot() {
        myFixture.copyFileToProject(PARTIAL_FILES_PATH + "/response_definitions_not_in_root.json", "response_definitions_not_in_root.json");
        final VirtualFile swaggerFile = myFixture.copyFileToProject(PARTIAL_FILES_PATH + "/swagger.json", "swagger.json");
        myFixture.configureFromExistingVirtualFile(swaggerFile);

        final AssertableList completions = new AssertableList(myFixture.getCompletionVariants("response_definitions_not_in_root.json"));

        completions.assertContains("description", "schema", "headers", "examples")
                .isOfSize(4);
    }
}
