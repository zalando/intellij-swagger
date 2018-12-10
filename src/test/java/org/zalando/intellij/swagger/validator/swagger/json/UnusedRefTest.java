package org.zalando.intellij.swagger.validator.swagger.json;

import com.intellij.openapi.vfs.VirtualFile;
import org.zalando.intellij.swagger.SwaggerLightCodeInsightFixtureTestCase;

public class UnusedRefTest extends SwaggerLightCodeInsightFixtureTestCase {

  private void doTest(final String fileName) {
    myFixture.testHighlighting(true, false, true, "validator/field/" + fileName);
  }

  public void testUnusedDefinitionMainFile() {
    doTest("unused_definition/json/unused_definition_main_file.json");
  }

  public void testUnusedParameterMainFile() {
    doTest("unused_parameter/json/unused_parameter_main_file.json");
  }

  public void testUnusedResponseMainFile() {
    doTest("unused_response/json/unused_response_main_file.json");
  }

  public void testUnusedDefinitionWhereDefinitionsAreInRoot() {
    final VirtualFile virtualFile =
        myFixture.copyFileToProject(
            "validator/field/unused_definition/json/unused_definition_in_root.json",
            "definitions.json");
    final VirtualFile swaggerFile =
        myFixture.copyFileToProject(
            "validator/field/unused_definition/json/unused_definition_in_root_swagger.json",
            "swagger.json");
    myFixture.configureFromExistingVirtualFile(swaggerFile);
    myFixture.testHighlighting(true, false, true, virtualFile);
  }

  public void testUnusedParameterWhereParametersAreInRoot() {
    final VirtualFile virtualFile =
        myFixture.copyFileToProject(
            "validator/field/unused_parameter/json/unused_parameter_in_root.json",
            "parameters.json");
    final VirtualFile swaggerFile =
        myFixture.copyFileToProject(
            "validator/field/unused_parameter/json/unused_parameter_in_root_swagger.json",
            "swagger.json");
    myFixture.configureFromExistingVirtualFile(swaggerFile);
    myFixture.testHighlighting(true, false, true, virtualFile);
  }

  public void testUnusedResponseWhereResponsesAreInRoot() {
    final VirtualFile virtualFile =
        myFixture.copyFileToProject(
            "validator/field/unused_response/json/unused_response_in_root.json", "responses.json");
    final VirtualFile swaggerFile =
        myFixture.copyFileToProject(
            "validator/field/unused_response/json/unused_response_in_root_swagger.json",
            "swagger.json");
    myFixture.configureFromExistingVirtualFile(swaggerFile);
    myFixture.testHighlighting(true, false, true, virtualFile);
  }

  public void testUnusedDefinitionWhereDefinitionsAreNotInRoot() {
    final VirtualFile virtualFile =
        myFixture.copyFileToProject(
            "validator/field/unused_definition/json/unused_definition_not_in_root.json",
            "definitions.json");
    final VirtualFile swaggerFile =
        myFixture.copyFileToProject(
            "validator/field/unused_definition/json/unused_definition_not_in_root_swagger.json",
            "swagger.json");
    myFixture.configureFromExistingVirtualFile(swaggerFile);
    myFixture.testHighlighting(true, false, true, virtualFile);
  }

  public void testUnusedParameterWhereParametersAreNotInRoot() {
    final VirtualFile virtualFile =
        myFixture.copyFileToProject(
            "validator/field/unused_parameter/json/unused_parameter_not_in_root.json",
            "parameters.json");
    final VirtualFile swaggerFile =
        myFixture.copyFileToProject(
            "validator/field/unused_parameter/json/unused_parameter_not_in_root_swagger.json",
            "swagger.json");
    myFixture.configureFromExistingVirtualFile(swaggerFile);
    myFixture.testHighlighting(true, false, true, virtualFile);
  }

  public void testUnusedResponseWhereResponsesAreNotInRoot() {
    final VirtualFile virtualFile =
        myFixture.copyFileToProject(
            "validator/field/unused_response/json/unused_response_not_in_root.json",
            "responses.json");
    final VirtualFile swaggerFile =
        myFixture.copyFileToProject(
            "validator/field/unused_response/json/unused_response_not_in_root_swagger.json",
            "swagger.json");
    myFixture.configureFromExistingVirtualFile(swaggerFile);
    myFixture.testHighlighting(true, false, true, virtualFile);
  }
}
