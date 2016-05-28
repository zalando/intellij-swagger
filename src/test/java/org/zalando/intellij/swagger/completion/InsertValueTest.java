package org.zalando.intellij.swagger.completion;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.zalando.intellij.swagger.fixture.SwaggerFixture;
import org.zalando.intellij.swagger.fixture.SwaggerFixture.JsonOrYaml;

@RunWith(Parameterized.class)
public class InsertValueTest {

    private SwaggerFixture myFixture;
    private final JsonOrYaml myJsonOrYaml;

    @Parameterized.Parameters(name = "inputKind: {0}")
    public static Object[] parameters() {
        return JsonOrYaml.values();
    }

    public InsertValueTest(JsonOrYaml jsonOrYaml) {
        myJsonOrYaml = jsonOrYaml;
    }

    @Before
    public void setUpBefore() throws Exception {
        myFixture = SwaggerFixture.forResourceFolder("testing/insert/value");
    }

    @After
    public void tearDownAfter() throws Exception {
        myFixture.tearDown();
    }

    @Test
    public void thatStringValuesAreHandled() {
        myFixture.complete("value_string_no_quotes", myJsonOrYaml);
        myFixture.checkResultByFile("value_string_no_quotes_after", myJsonOrYaml);

        myFixture.complete("value_string_in_quotes", myJsonOrYaml);
        myFixture.checkResultByFile("value_string_in_quotes_after", myJsonOrYaml);
    }

}
