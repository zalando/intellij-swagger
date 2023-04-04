package org.zalando.intellij.swagger.intention.json;

import com.intellij.codeInspection.LocalInspectionTool;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.zalando.intellij.swagger.inspection.reference.JsonReferenceInspection;
import org.zalando.intellij.swagger.intention.IntentionActionTest;

public class CreateReferenceIntentionActionTest extends IntentionActionTest {

  @NotNull
  @Override
  protected LocalInspectionTool[] configureLocalInspectionTools() {
    return new LocalInspectionTool[] {new JsonReferenceInspection()};
  }

  @Override
  @NonNls
  protected String getBasePath() {
    return "/intention/createreference/json";
  }
}
