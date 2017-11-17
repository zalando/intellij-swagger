package org.zalando.intellij.swagger.completion.field.completion.openapi.json;

import org.zalando.intellij.swagger.assertion.AssertableList;

public class CallbackFileCompletionTest extends PartialFileCompletionTest {


    public void testThatSingleSchemaFileIsAutoCompleted() {
        withSpecFiles("pet.json", "callback.json");

        final AssertableList completions = new AssertableList(geCompletions("pet.json"));

        assertCallbackCompletions(completions);
    }

    public void testThatSchemasFileIsAutoCompleted() {
        withSpecFiles("components.json", "callbacks.json");

        final AssertableList completions = new AssertableList(geCompletions("components.json"));

        assertCallbackCompletions(completions);
    }

    private void assertCallbackCompletions(final AssertableList completions) {
        completions.assertContains("$ref", "description", "summary", "get", "put", "post", "delete", "options", "head",
                "patch", "trace", "servers", "parameters")
                .isOfSize(13);
    }
}
