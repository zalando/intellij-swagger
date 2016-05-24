package org.zalando.intellij.swagger.completion;

import com.intellij.codeInsight.completion.CompletionType;
import com.intellij.codeInsight.lookup.LookupManager;
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
}
