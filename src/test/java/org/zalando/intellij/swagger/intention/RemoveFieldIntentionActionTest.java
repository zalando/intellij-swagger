package org.zalando.intellij.swagger.intention;

public class RemoveFieldIntentionActionTest extends IntentionActionTest {

  public void test() throws Exception {
    doAllTests();
  }

  @Override
  protected String getBasePath() {
    return "/intention/removefield";
  }
}
