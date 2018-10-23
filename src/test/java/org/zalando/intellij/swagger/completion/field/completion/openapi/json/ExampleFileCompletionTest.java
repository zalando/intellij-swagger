package org.zalando.intellij.swagger.completion.field.completion.openapi.json;

import org.zalando.intellij.swagger.assertion.AssertableList;

public class ExampleFileCompletionTest extends PartialFileCompletionTest {

  public void testThatExampleFileIsAutoCompleted() {
    withSpecFiles("pet.json", "example.json");

    final AssertableList completions = new AssertableList(geCompletions("pet.json"));

    assertExampleCompletions(completions);
  }

  public void testThatExamplesFileIsAutoCompleted() {
    withSpecFiles("components.json", "examples.json");

    final AssertableList completions = new AssertableList(geCompletions("components.json"));

    assertExampleCompletions(completions);
  }

  private void assertExampleCompletions(final AssertableList completions) {
    completions
        .assertContains("$ref", "summary", "description", "value", "externalValue")
        .isOfSize(5);
  }
}
