package org.zalando.intellij.swagger.completion.field.completion.openapi.json;

import org.zalando.intellij.swagger.assertion.AssertableList;

public class ParameterFileCompletionTest extends PartialFileCompletionTest {

    public void testThatSingleParameterFileIsAutoCompleted() {
        withSpecFiles("pet.json", "parameter.json");

        final AssertableList completions = new AssertableList(geCompletions("pet.json"));

        assertParameterCompletions(completions);
    }

    public void testThatParametersFileIsAutoCompleted() {
        withSpecFiles("components.json", "parameters.json");

        final AssertableList completions = new AssertableList(geCompletions("components.json"));

        assertParameterCompletions(completions);
    }

    private void assertParameterCompletions(final AssertableList completions) {
        completions
                .assertContains("$ref", "name", "in", "description", "required", "deprecated", "allowEmptyValue",
                        "style", "explode", "allowReserved", "schema", "example", "examples", "content")
                .isOfSize(14);
    }
}
