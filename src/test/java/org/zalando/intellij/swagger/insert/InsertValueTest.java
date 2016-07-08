package org.zalando.intellij.swagger.insert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.zalando.intellij.swagger.completion.CompletionTest;
import org.zalando.intellij.swagger.fixture.Format;

@RunWith(Parameterized.class)
public class InsertValueTest extends CompletionTest {

    public InsertValueTest(Format format) {
        super(format, "testing/insert/value");
    }

    @Parameterized.Parameters(name = "inputKind: {0}")
    public static Object[] parameters() {
        return Format.values();
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

    @Test
    public void thatValueWithoutQuotesIshHandled() {
        completeAndCheckResultsByFile("value_without_quotes", "value_without_quotes_after");
    }

    @Test
    public void thatValueInsideQuotesIsHandled() {
        completeAndCheckResultsByFile("value_inside_quotes", "value_inside_quotes_after");
    }

    @Test
    public void thatBooleanValueIsNotQuoted() {
        completeAndCheckResultsByFile("value_boolean", "value_boolean_after");
    }
}
