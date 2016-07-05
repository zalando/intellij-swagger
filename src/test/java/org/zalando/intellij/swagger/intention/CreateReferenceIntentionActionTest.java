package org.zalando.intellij.swagger.intention;

public class CreateReferenceIntentionActionTest extends IntentionActionTest {

    public void test() throws Exception {
        doAllTests();
    }

    @Override
    protected String getBasePath() {
        return "/intention/createreference";
    }

}
