package org.zalando.intellij.swagger.completion.field.completion.openapi.json;

import org.zalando.intellij.swagger.assertion.AssertableList;

public class RequestBodyFileCompletionTest extends PartialFileCompletionTest {


    public void testThatSingleRequestBodyFileIsAutoCompleted() {
        withSpecFiles("pet.json", "request_body.json");

        final AssertableList completions = new AssertableList(geCompletions("pet.json"));

        assertRequestBodyCompletions(completions);
    }

    public void testThatRequestBodiesFileIsAutoCompleted() {
        withSpecFiles("components.json", "request_bodies.json");

        final AssertableList completions = new AssertableList(geCompletions("components.json"));

        assertRequestBodyCompletions(completions);
    }

    private void assertRequestBodyCompletions(final AssertableList completions) {
        completions
                .assertContains("$ref", "description", "content", "required")
                .isOfSize(4);
    }
}
