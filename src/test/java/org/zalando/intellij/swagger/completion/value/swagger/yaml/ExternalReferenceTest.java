package org.zalando.intellij.swagger.completion.value.swagger.yaml;

import com.intellij.openapi.vfs.VirtualFile;

import org.zalando.intellij.swagger.SwaggerLightCodeInsightFixtureTestCase;
import org.zalando.intellij.swagger.assertion.AssertableList;

public class ExternalReferenceTest extends SwaggerLightCodeInsightFixtureTestCase {

  private static final String PARTIAL_FILES_PATH = "completion/value/swagger/partial/yaml";

  public void testThatSingleDefinitionFileIsSuggested() {
    myFixture.copyFileToProject(PARTIAL_FILES_PATH + "/pet.yaml", "pet.yaml");
    final VirtualFile swaggerFile =
        myFixture.copyFileToProject(PARTIAL_FILES_PATH + "/swagger_pet.yaml", "swagger.yaml");
    myFixture.configureFromExistingVirtualFile(swaggerFile);

    final AssertableList completions =
        new AssertableList(myFixture.getCompletionVariants("swagger.yaml"));

    completions.assertContains("pet.yaml");
  }
}
