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
    public void thatDefinitionRefValuesAreCompleted() {
        completeAndCheckResultsByFile("definition_ref_in_quotes", "definition_ref_after");
        completeAndCheckResultsByFile("definition_ref_in_quotes_with_prefix", "definition_ref_after");
        completeAndCheckResultsByFile("definition_ref_in_quotes_with_prefix2", "definition_ref_after");
    }

    @Test
    public void thatParameterRefValuesAreCompleted() {
        completeAndCheckResultsByFile("parameter_ref_in_quotes", "parameter_ref_after");
        completeAndCheckResultsByFile("parameter_ref_in_quotes_with_prefix", "parameter_ref_after");
        completeAndCheckResultsByFile("parameter_ref_in_quotes_with_prefix2", "parameter_ref_after");
    }

}
