package org.zalando.intellij.swagger.insert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.zalando.intellij.swagger.completion.JsonAndYamlCompletionTest;
import org.zalando.intellij.swagger.fixture.Format;

@RunWith(Parameterized.class)
public class InsertYamlValueTest extends JsonAndYamlCompletionTest {

    public InsertYamlValueTest(Format format) {
        super(format, "testing/insert/value/yaml");
    }

    @Parameterized.Parameters(name = "inputKind: {0}")
    public static Object[] parameters() {
        return new Object[]{Format.YAML};
    }

    @Test
    public void thatValueInsideDoubleQuotesIsHandled() {
        completeAndCheckResultsByFile("value_inside_double_quotes");
    }

    @Test
    public void thatValueStartingWithDoubleQuoteIsHandled() {
        completeAndCheckResultsByFile("value_starting_with_double_quote");
    }

    @Test
    public void thatValueEndingWithDoubleQuoteIsHandled() {
        completeAndCheckResultsByFile("value_ending_with_double_quote");
    }
}
