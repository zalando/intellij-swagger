package org.zalando.intellij.swagger.completion;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.zalando.intellij.swagger.fixture.SwaggerFixture.JsonOrYaml;

@RunWith(Parameterized.class)
public class InsertValueTest extends AbstractJsonOrYamlCompletionTest {
    public InsertValueTest(JsonOrYaml jsonOrYaml) {
        super(jsonOrYaml);
    }

    @Parameterized.Parameters(name = "inputKind: {0}")
    public static Object[] parameters() {
        return JsonOrYaml.values();
    }

    @Before
    public void setUpBefore() throws Exception {
        useResourceFolder("testing/insert/value");
    }

    @Test
    public void thatStringValuesAreHandled() {
        completeAndCheckResultsByFile("string_no_quotes");
        completeAndCheckResultsByFile("string_in_quotes");
    }

}
