package org.zalando.intellij.swagger.completion.field.completion.openapi.json;

import org.zalando.intellij.swagger.assertion.AssertableList;

public class ResponseFileCompletionTest extends PartialFileCompletionTest {

  public void testThatSingleResponseFileIsAutoCompleted() {
    withSpecFiles("pet.json", "response.json");

    final AssertableList completions = new AssertableList(geCompletions("pet.json"));

    assertResponseCompletions(completions);
  }

  public void testThatResponsesFileIsAutoCompleted() {
    withSpecFiles("components.json", "responses.json");

    final AssertableList completions = new AssertableList(geCompletions("components.json"));

    assertResponseCompletions(completions);
  }

  private void assertResponseCompletions(final AssertableList completions) {
    completions.assertContains("$ref", "description", "headers", "content", "links").isOfSize(5);
  }
}
