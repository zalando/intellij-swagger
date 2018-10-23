package org.zalando.intellij.swagger.validator.yaml;

import com.intellij.openapi.vfs.VirtualFile;
import org.zalando.intellij.swagger.SwaggerLightCodeInsightFixtureTestCase;

public class UnusedRefTest extends SwaggerLightCodeInsightFixtureTestCase {

  private void doTest(final String fileName) {
    myFixture.testHighlighting(true, false, true, "validator/field/" + fileName);
  }

  public void testUnusedDefinitionMainFile() {
    doTest("unused_definition/yaml/unused_definition_main_file.yaml");
  }

  public void testUnusedParameterMainFile() {
    doTest("unused_parameter/yaml/unused_parameter_main_file.yaml");
  }

  public void testUnusedResponseMainFile() {
    doTest("unused_response/yaml/unused_response_main_file.yaml");
  }

  public void testUnusedDefinitionWhereDefinitionsAreInRoot() {
    final VirtualFile virtualFile =
        myFixture.copyFileToProject(
            "validator/field/unused_definition/yaml/unused_definition_in_root.yaml",
            "definitions.yaml");
    final VirtualFile swaggerFile =
        myFixture.copyFileToProject(
            "validator/field/unused_definition/yaml/unused_definition_in_root_swagger.yaml",
            "swagger.yaml");
    myFixture.configureFromExistingVirtualFile(swaggerFile);
    myFixture.testHighlighting(true, false, true, virtualFile);
  }

  public void testUnusedParameterWhereParametersAreInRoot() {
    final VirtualFile virtualFile =
        myFixture.copyFileToProject(
            "validator/field/unused_parameter/yaml/unused_parameter_in_root.yaml",
            "parameters.yaml");
    final VirtualFile swaggerFile =
        myFixture.copyFileToProject(
            "validator/field/unused_parameter/yaml/unused_parameter_in_root_swagger.yaml",
            "swagger.yaml");
    myFixture.configureFromExistingVirtualFile(swaggerFile);
    myFixture.testHighlighting(true, false, true, virtualFile);
  }

  public void testUnusedResponseWhereResponsesAreInRoot() {
    final VirtualFile virtualFile =
        myFixture.copyFileToProject(
            "validator/field/unused_response/yaml/unused_response_in_root.yaml", "responses.yaml");
    final VirtualFile swaggerFile =
        myFixture.copyFileToProject(
            "validator/field/unused_response/yaml/unused_response_in_root_swagger.yaml",
            "swagger.yaml");
    myFixture.configureFromExistingVirtualFile(swaggerFile);
    myFixture.testHighlighting(true, false, true, virtualFile);
  }

  public void testUnusedDefinitionWhereDefinitionsAreNotInRoot() {
    final VirtualFile virtualFile =
        myFixture.copyFileToProject(
            "validator/field/unused_definition/yaml/unused_definition_not_in_root.yaml",
            "definitions.yaml");
    final VirtualFile swaggerFile =
        myFixture.copyFileToProject(
            "validator/field/unused_definition/yaml/unused_definition_not_in_root_swagger.yaml",
            "swagger.yaml");
    myFixture.configureFromExistingVirtualFile(swaggerFile);
    myFixture.testHighlighting(true, false, true, virtualFile);
  }

  public void testUnusedParameterWhereParametersAreNotInRoot() {
    final VirtualFile virtualFile =
        myFixture.copyFileToProject(
            "validator/field/unused_parameter/yaml/unused_parameter_not_in_root.yaml",
            "parameters.yaml");
    final VirtualFile swaggerFile =
        myFixture.copyFileToProject(
            "validator/field/unused_parameter/yaml/unused_parameter_not_in_root_swagger.yaml",
            "swagger.yaml");
    myFixture.configureFromExistingVirtualFile(swaggerFile);
    myFixture.testHighlighting(true, false, true, virtualFile);
  }

  public void testUnusedResponseWhereResponsesAreNotInRoot() {
    final VirtualFile virtualFile =
        myFixture.copyFileToProject(
            "validator/field/unused_response/yaml/unused_response_not_in_root.yaml",
            "responses.yaml");
    final VirtualFile swaggerFile =
        myFixture.copyFileToProject(
            "validator/field/unused_response/yaml/unused_response_not_in_root_swagger.yaml",
            "swagger.yaml");
    myFixture.configureFromExistingVirtualFile(swaggerFile);
    myFixture.testHighlighting(true, false, true, virtualFile);
  }
}
