package org.zalando.intellij.swagger.completion.field.completion.openapi.json;

import org.zalando.intellij.swagger.assertion.AssertableList;

public class LinkFileCompletionTest extends PartialFileCompletionTest {

  public void testThatSingleLinkFileIsAutoCompleted() {
    withSpecFiles("pet.json", "link.json");

    final AssertableList completions = new AssertableList(geCompletions("pet.json"));

    assertLinkCompletions(completions);
  }

  public void testThatLinksFileIsAutoCompleted() {
    withSpecFiles("components.json", "links.json");

    final AssertableList completions = new AssertableList(geCompletions("components.json"));

    assertLinkCompletions(completions);
  }

  private void assertLinkCompletions(final AssertableList completions) {
    completions
        .assertContains(
            "$ref",
            "operationRef",
            "operationId",
            "parameters",
            "requestBody",
            "description",
            "server")
        .isOfSize(7);
  }
}
