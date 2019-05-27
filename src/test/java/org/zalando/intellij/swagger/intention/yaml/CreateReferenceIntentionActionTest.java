package org.zalando.intellij.swagger.intention.yaml;

import com.intellij.codeInspection.LocalInspectionTool;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.zalando.intellij.swagger.index.IndexFacade;
import org.zalando.intellij.swagger.index.openapi.OpenApiIndexService;
import org.zalando.intellij.swagger.index.swagger.SwaggerIndexService;
import org.zalando.intellij.swagger.inspection.reference.YamlReferenceInspection;
import org.zalando.intellij.swagger.intention.IntentionActionTest;
import org.zalando.intellij.swagger.service.intellij.DumbService;

public class CreateReferenceIntentionActionTest extends IntentionActionTest {

  private final IndexFacade indexFacade =
      new IndexFacade(new OpenApiIndexService(), new SwaggerIndexService(), new DumbService());

  @NotNull
  @Override
  protected LocalInspectionTool[] configureLocalInspectionTools() {
    return new LocalInspectionTool[] {new YamlReferenceInspection(indexFacade)};
  }

  public void test() {
    doAllTests();
  }

  @Override
  @NonNls
  protected String getBasePath() {
    return "/intention/createreference/yaml";
  }
}
