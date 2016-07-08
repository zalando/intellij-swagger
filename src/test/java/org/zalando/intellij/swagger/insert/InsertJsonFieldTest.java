package org.zalando.intellij.swagger.insert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.zalando.intellij.swagger.completion.CompletionTest;
import org.zalando.intellij.swagger.fixture.Format;

@RunWith(Parameterized.class)
public class InsertJsonFieldTest extends CompletionTest {

    public InsertJsonFieldTest(Format format) {
        super(format, "testing/insert/field/json");
    }

    @Parameterized.Parameters(name = "inputKind: {0}")
    public static Object[] parameters() {
        return new Object[]{Format.JSON};
    }

    @Test
    public void thatKeyInsideQuotesIsHandled() {
        completeAndCheckResultsByFile("key_inside_quotes");
    }

}
