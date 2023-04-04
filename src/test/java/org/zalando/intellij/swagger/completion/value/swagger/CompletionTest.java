package org.zalando.intellij.swagger.completion.value.swagger;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.zalando.intellij.swagger.completion.JsonAndYamlCompletionTest;
import org.zalando.intellij.swagger.fixture.Format;

@RunWith(Parameterized.class)
public class CompletionTest extends JsonAndYamlCompletionTest {

  public CompletionTest(Format format) {
    super(format, "testing/completion/value/swagger");
  }

  @Parameterized.Parameters(name = "inputKind: {0}")
  public static Object[] parameters() {
    return Format.values();
  }

  @Test
  public void thatTypesAreSuggested() {
    getCaretCompletions("types_all")
        .assertContains("object", "integer", "number", "string", "boolean", "array")
        .isOfSize(6);
  }

  @Test
  public void thatFormatsAreSuggested() {
    getCaretCompletions("formats_all")
        .assertContains("int32", "int64")
        .assertContains("byte", "binary", "date", "date-time", "password")
        .assertContains("float", "double");
  }

  @Test
  public void testGlobalConsumes() {
    getCaretCompletions("media_type_consumes")
        .assertContains("application/xml", "image/*", "text/plain")
        .assertNotContains("consumes", "produces", "paths");
  }

  @Test
  public void testGlobalProduces() {
    getCaretCompletions("media_type_produces")
        .assertContains("application/json", "image/*", "text/plain")
        .assertNotContains("consumes", "produces", "paths", "application/xml");
  }

  @Test
  public void testDefinitionRefValueInSchema() {
    getCaretCompletions("definition_ref_value_in_schema")
        .assertContains("#/definitions/Pets", "#/definitions/Error")
        .isOfSize(2);
  }

  @Test
  public void testDefinitionRefValueInItems() {
    getCaretCompletions("definition_ref_value_in_items")
        .assertContains("#/definitions/Pets", "#/definitions/Error")
        .isOfSize(2);
  }

  @Test
  public void testDefinitionRefValueInDefinition() {
    getCaretCompletions("definition_ref_value_in_definition")
        .assertContains("#/definitions/Bar")
        .isOfSize(2);
  }

  @Test
  public void testParameterRefValue() {
    getCaretCompletions("parameter_ref_value").assertContains("#/parameters/Dog").isOfSize(1);
  }

  @Test
  public void testParameterRefValueChildOfPath() {
    getCaretCompletions("parameter_ref_value_child_of_path")
        .assertContains("#/parameters/Dog")
        .isOfSize(1);
  }

  @Test
  public void testResponseRefValue() {
    getCaretCompletions("response_ref_value")
        .assertContains("#/responses/responseName")
        .isOfSize(1);
  }

  @Test
  public void thatInValuesAreSuggested() {
    getCaretCompletions("in")
        .assertContains("path", "header", "query", "formData", "body")
        .isOfSize(5);
  }

  @Test
  public void thatBooleanValuesAreSuggested() {
    getCaretCompletions("boolean_parameters_required").assertContains("true", "false");
  }

  @Test
  public void thatRootSecurityScopeNameFieldsAreSuggested() {
    getCaretCompletions("security_value_in_root").assertContains("admin:public_key").isOfSize(1);
  }

  @Test
  public void thatOperationSecurityScopeNameFieldsAreSuggested() {
    getCaretCompletions("security_value_in_operation")
        .assertContains("admin:public_key")
        .isOfSize(1);
  }

  @Test
  public void thatParametersCollectionFormatValuesAreSuggested() {
    getCaretCompletions("parameters_collection_format")
        .assertContains("csv", "ssv", "tsv", "pipes", "multi")
        .isOfSize(5);
  }

  @Test
  public void thatHeadersCollectionFormatValuesAreSuggested() {
    getCaretCompletions("headers_collection_format")
        .assertContains("csv", "ssv", "tsv", "pipes")
        .isOfSize(4);
  }

  @Test
  public void thatItemsCollectionFormatValuesAreSuggested() {
    getCaretCompletions("items_collection_format")
        .assertContains("csv", "ssv", "tsv", "pipes")
        .isOfSize(4);
  }

  @Test
  public void thatSchemesAreSuggested() {
    getCaretCompletions("schemes").assertContains("http", "https", "ws", "wss").isOfSize(4);
  }

  @Test
  public void thatOnlyUniqueArrayStringValuesAreSuggested() {
    getCaretCompletions("unique_strings_in_array").assertContains("https", "ws", "wss").isOfSize(3);
  }

  @Test
  public void thatTagValuesAreSuggested() {
    getCaretCompletions("tags").assertContains("tag1", "tag2").isOfSize(2);
  }

  @Test
  public void thatDefinitionsAreSuggestedWithLowercasePrefix() {
    getCaretCompletions("ref_lowercase").assertContains("#/definitions/Pets").isOfSize(1);
  }

  @Test
  public void thatDefinitionsAreSuggestedWithUppercasePrefix() {
    getCaretCompletions("ref_uppercase").assertContains("#/definitions/Pets").isOfSize(1);
  }
}
