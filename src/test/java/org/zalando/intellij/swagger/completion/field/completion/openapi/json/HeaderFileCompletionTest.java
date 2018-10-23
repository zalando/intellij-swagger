package org.zalando.intellij.swagger.completion.field.completion.openapi.json;

import org.zalando.intellij.swagger.assertion.AssertableList;

public class HeaderFileCompletionTest extends PartialFileCompletionTest {

  public void testThatSingleHeaderFileIsAutoCompleted() {
    withSpecFiles("pet.json", "header.json");

    final AssertableList completions = new AssertableList(geCompletions("pet.json"));

    assertHeaderCompletions(completions);
  }

  public void testThatHeadersFileIsAutoCompleted() {
    withSpecFiles("components.json", "headers.json");

    final AssertableList completions = new AssertableList(geCompletions("components.json"));

    assertHeaderCompletions(completions);
  }

  private void assertHeaderCompletions(final AssertableList completions) {
    completions
        .assertContains(
            "$ref",
            "description",
            "required",
            "deprecated",
            "allowEmptyValue",
            "style",
            "explode",
            "allowReserved",
            "schema",
            "example",
            "examples",
            "content")
        .isOfSize(12);
  }
}
