package org.zalando.intellij.swagger.insert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.zalando.intellij.swagger.completion.AbstractJsonOrYamlCompletionTest;
import org.zalando.intellij.swagger.fixture.SwaggerFixture.JsonOrYaml;

@RunWith(Parameterized.class)
public class InsertJsonFieldTest extends AbstractJsonOrYamlCompletionTest {
    public InsertJsonFieldTest(JsonOrYaml jsonOrYaml) {
        super(jsonOrYaml);
    }

    @Parameterized.Parameters(name = "inputKind: {0}")
    public static Object[] parameters() {
        return new Object[]{JsonOrYaml.JSON};
    }

    @Before
    public void setUpBefore() throws Exception {
        useResourceFolder("testing/insert/field/json");
    }

    @Test
    public void thatKeyInsideQuotesIsHandled() {
        completeAndCheckResultsByFile("key_inside_quotes");
    }

}
