package org.zalando.intellij.swagger.completion.value.openapi;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.zalando.intellij.swagger.completion.JsonAndYamlCompletionTest;
import org.zalando.intellij.swagger.fixture.Format;

@RunWith(Parameterized.class)
public class CompletionTest extends JsonAndYamlCompletionTest {

  public CompletionTest(Format format) {
    super(format, "testing/completion/value/openapi");
  }

  @Parameterized.Parameters(name = "inputKind: {0}")
  public static Object[] parameters() {
    return Format.values();
  }

  @Test
  public void thatSchemaRefValueIsSuggested() {
    getCaretCompletions("schema_ref").assertContains("#/components/schemas/Pet").isOfSize(1);
  }

  @Test
  public void thatCallbackRefValueIsSuggested() {
    getCaretCompletions("callback_ref")
        .assertContains("#/components/callbacks/Callback")
        .isOfSize(1);
  }

  @Test
  public void thatExampleRefValueIsSuggested() {
    getCaretCompletions("example_ref").assertContains("#/components/examples/Example").isOfSize(1);
  }

  @Test
  public void thatHeaderRefValueIsSuggested() {
    getCaretCompletions("header_ref").assertContains("#/components/headers/Header").isOfSize(1);
  }

  @Test
  public void thatLinkRefValueIsSuggested() {
    getCaretCompletions("link_ref").assertContains("#/components/links/Link").isOfSize(1);
  }

  @Test
  public void thatParameterRefValueIsSuggested() {
    getCaretCompletions("parameter_ref")
        .assertContains("#/components/parameters/Parameter")
        .isOfSize(1);
  }

  @Test
  public void thatRequestBodyRefValueIsSuggested() {
    getCaretCompletions("request_body_ref")
        .assertContains("#/components/requestBodies/Foo")
        .isOfSize(1);
  }

  @Test
  public void thatResponseRefValueIsSuggested() {
    getCaretCompletions("response_ref")
        .assertContains("#/components/responses/NotFound")
        .isOfSize(1);
  }

  @Test
  public void thatBooleanValuesAreSuggested() {
    getCaretCompletions("boolean").assertContains("true", "false");
  }

  @Test
  public void thatTypeValuesAreSuggested() {
    getCaretCompletions("type")
        .assertContains("object", "integer", "number", "string", "boolean", "array")
        .isOfSize(6);
  }

  @Test
  public void thatInValuesAreSuggested() {
    getCaretCompletions("in").assertContains("query", "header", "path", "cookie").isOfSize(4);
  }

  @Test
  public void thatFormatValuesAreSuggested() {
    getCaretCompletions("format")
        .assertContains(
            "int32",
            "int64",
            "float",
            "double",
            "byte",
            "binary",
            "date",
            "date-time",
            "password",
            "email",
            "uuid")
        .isOfSize(11);
  }

  @Test
  public void thatStyleValuesAreSuggested() {
    getCaretCompletions("style")
        .assertContains(
            "matrix", "label", "form", "simple", "spaceDelimited", "pipeDelimited", "deepObject")
        .isOfSize(7);
  }
}
