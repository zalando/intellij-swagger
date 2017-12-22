package org.zalando.intellij.swagger.completion.field.completion.swagger.json;

import com.intellij.openapi.vfs.VirtualFile;
import org.zalando.intellij.swagger.SwaggerLightCodeInsightFixtureTestCase;
import org.zalando.intellij.swagger.assertion.AssertableList;

public class PathsFileCompletionTest extends SwaggerLightCodeInsightFixtureTestCase {

    private static final String PARTIAL_FILES_PATH = "completion/field/swagger/partial/json";

    public void testThatAutoCompletionWorksForFileWithSingleDefinition() {
        myFixture.copyFileToProject(PARTIAL_FILES_PATH + "/path.json", "path.json");
        final VirtualFile swaggerFile = myFixture.copyFileToProject(PARTIAL_FILES_PATH + "/swagger.json", "swagger.json");
        myFixture.configureFromExistingVirtualFile(swaggerFile);

        final AssertableList completions = new AssertableList(myFixture.getCompletionVariants("path.json"));

        completions.assertContains("$ref", "get", "put", "post", "delete", "options", "head", "patch", "parameters")
                .isOfSize(9);
    }

    public void testThatAutoCompletionWorksForFileWithMultiplePathDefinitionsInRoot() {
        myFixture.copyFileToProject(PARTIAL_FILES_PATH + "/path_definitions_in_root.json", "path_definitions_in_root.json");
        final VirtualFile swaggerFile = myFixture.copyFileToProject(PARTIAL_FILES_PATH + "/swagger.json", "swagger.json");
        myFixture.configureFromExistingVirtualFile(swaggerFile);

        final AssertableList completions = new AssertableList(myFixture.getCompletionVariants("path_definitions_in_root.json"));

        completions.assertContains("$ref", "get", "put", "post", "delete", "options", "head", "patch", "parameters")
                .isOfSize(9);
    }

    public void testThatAutoCompletionWorksForFileWithMultiplePathDefinitionsNotInRoot() {
        myFixture.copyFileToProject(PARTIAL_FILES_PATH + "/path_definitions_not_in_root.json", "path_definitions_not_in_root.json");
        final VirtualFile swaggerFile = myFixture.copyFileToProject(PARTIAL_FILES_PATH + "/swagger.json", "swagger.json");
        myFixture.configureFromExistingVirtualFile(swaggerFile);

        final AssertableList completions = new AssertableList(myFixture.getCompletionVariants("path_definitions_not_in_root.json"));

        completions.assertContains("$ref", "get", "put", "post", "delete", "options", "head", "patch", "parameters")
                .isOfSize(9);
    }
}
