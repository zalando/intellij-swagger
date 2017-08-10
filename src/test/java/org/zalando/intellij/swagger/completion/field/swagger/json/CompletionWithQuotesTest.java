package org.zalando.intellij.swagger.completion.field.swagger.json;

import com.google.common.collect.Lists;
import org.jetbrains.annotations.NotNull;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.zalando.intellij.swagger.completion.JsonAndYamlCompletionTest;
import org.zalando.intellij.swagger.fixture.Format;

import java.util.List;

@RunWith(Parameterized.class)
public class CompletionWithQuotesTest extends JsonAndYamlCompletionTest {

    public CompletionWithQuotesTest(@NotNull final Format format) {
        super(format, "testing/completion/field/swagger/json");
    }

    @Parameterized.Parameters(name = "inputKind: {0}")
    public static Object[] parameters() {
        return new Object[]{Format.JSON};
    }

    @Test
    public void thatJsonKeyInsideQuotesIsCompleted() {
        getCaretCompletions("key_inside_quotes")
                .assertContains(getExpectedElements())
                .assertNotContains(getExistingElements());
    }

    @Test
    public void thatJsonKeyWithNoQuotesIsCompleted() {
        getCaretCompletions("key_with_no_quotes")
                .assertContains(getExpectedElements())
                .assertNotContains(getExistingElements());
    }

    @Test
    public void thatJsonKeyWithStartingQuoteIsCompleted() {
        getCaretCompletions("key_with_starting_quote")
                .assertContains(getExpectedElements())
                .assertNotContains(getExistingElements());
    }

    private List<String> getExistingElements() {
        return Lists.newArrayList("swagger", "host");
    }

    private List<String> getExpectedElements() {
        return Lists.newArrayList("basePath", "produces", "consumes", "schemes",
                "paths", "tags", "parameters", "responses");
    }

}
