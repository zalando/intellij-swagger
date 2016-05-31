package org.zalando.intellij.swagger.completion;

import org.junit.Before;
import org.junit.Ignore;
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
    public void thatRefValuesAreCompleted() {
        completeAndCheckResultsByFile("ref_no_quotes");
    }

    @Test
    public void thatRefValuesCompletion_RespectQuotes() {
        completeAndCheckResultsByFile("ref_in_quotes");
    }

    @Test
    public void thatRefValuesCompletion_RespectEnteredPrefix() {
        completeAndCheckResultsByFile("ref_in_quotes_with_prefix");
        completeAndCheckResultsByFile("ref_in_quotes_with_prefix2", "ref_in_quotes_with_prefix_after");
    }

    @Test
    public void thatRefValuesCompletion_WorksForUnquotedJsonPointerStart() {
        completeAndCheckResultsByFile("ref_no_quotes_pointer");
    }

    @Test
    public void thatRefValuesCompletion_WorksForUnquotedJsonPointerStartWithSlash() {
        completeAndCheckResultsByFile("ref_no_quotes_pointer_with_slash");
    }

}
