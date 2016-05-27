package org.zalando.intellij.swagger.completion;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.zalando.intellij.swagger.fixture.SwaggerFixture;
import org.zalando.intellij.swagger.fixture.SwaggerFixture.JsonOrYaml;

@RunWith(Parameterized.class)
public class InsertHandlerTest {

    private SwaggerFixture myFixture;
    private final JsonOrYaml myJsonOrYaml;

    @Parameterized.Parameters(name = "inputKind: {0}")
    public static Object[] parameters() {
        return JsonOrYaml.values();
    }

    public InsertHandlerTest(JsonOrYaml jsonOrYaml) {
        myJsonOrYaml = jsonOrYaml;
    }

    @Before
    public void setUpBefore() throws Exception {
        myFixture = SwaggerFixture.forResourceFolder("testing/insert");
    }

    @After
    public void tearDownAfter() throws Exception {
        myFixture.tearDown();
    }

    @Test
    public void thatStringFieldIsHandled() {
        myFixture.complete("field_string", myJsonOrYaml);
        myFixture.checkResultByFile("field_string_after", myJsonOrYaml);
    }

    @Test
    public void thatObjectFieldIsHandled() {
        myFixture.complete("field_object", myJsonOrYaml);
        myFixture.checkResultByFile("field_object_after", myJsonOrYaml);
    }

    @Test
    public void thatArrayFieldIsHandled() {
        myFixture.complete("field_array", myJsonOrYaml);
        myFixture.checkResultByFile("field_array_after", myJsonOrYaml);
    }

    @Test
    public void thatStringValuesAreHandled() {
        myFixture.complete("value_string_no_quotes", myJsonOrYaml);
        myFixture.checkResultByFile("value_string_no_quotes_after", myJsonOrYaml);

        myFixture.complete("value_string_in_quotes", myJsonOrYaml);
        myFixture.checkResultByFile("value_string_in_quotes_after", myJsonOrYaml);
    }

    @Test
    public void testCommaAfterMidwayObject() {
        myFixture.complete("field_object_midway", myJsonOrYaml);
        myFixture.checkResultByFile("field_object_midway_after", myJsonOrYaml);
    }

    @Test
    public void testCommaAfterMidwayArray() {
        myFixture.complete("field_array_midway", myJsonOrYaml);
        myFixture.checkResultByFile("field_array_midway_after", myJsonOrYaml);
    }
}
