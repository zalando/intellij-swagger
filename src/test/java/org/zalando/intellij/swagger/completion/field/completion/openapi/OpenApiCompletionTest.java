package org.zalando.intellij.swagger.completion.field.completion.openapi;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.zalando.intellij.swagger.completion.JsonAndYamlCompletionTest;
import org.zalando.intellij.swagger.fixture.Format;

@RunWith(Parameterized.class)
public class OpenApiCompletionTest extends JsonAndYamlCompletionTest {

  public OpenApiCompletionTest(Format format) {
    super(format, "testing/completion/field/openapi/");
  }

  @Parameterized.Parameters(name = "inputKind: {0}")
  public static Object[] parameters() {
    return Format.values();
  }

  @Test
  public void thatRootKeysAreSuggested() {
    getCaretCompletions("root")
        .assertContains(
            "info", "servers", "paths", "components", "security", "tags", "externalDocs")
        .isOfSize(7);
  }

  @Test
  public void thatInfoKeysAreSuggested() {
    getCaretCompletions("info")
        .assertContains("title", "description", "termsOfService", "contact", "license", "version")
        .isOfSize(6);
  }

  @Test
  public void thatServerKeysAreSuggested() {
    getCaretCompletions("servers").assertContains("url", "description", "variables").isOfSize(3);
  }

  @Test
  public void thatServerVariableKeysAreSuggested() {
    getCaretCompletions("server_variable")
        .assertContains("enum", "default", "description")
        .isOfSize(3);
  }

  @Test
  public void thatPathKeysAreSuggested() {
    getCaretCompletions("path")
        .assertContains(
            "$ref",
            "description",
            "summary",
            "get",
            "put",
            "post",
            "delete",
            "options",
            "head",
            "patch",
            "trace",
            "servers",
            "parameters")
        .isOfSize(13);
  }

  @Test
  public void thatOperationKeysAreSuggested() {
    getCaretCompletions("operation")
        .assertContains(
            "tags",
            "summary",
            "description",
            "externalDocs",
            "operationId",
            "parameters",
            "requestBody",
            "responses",
            "callbacks",
            "deprecated",
            "security",
            "servers")
        .isOfSize(12);
  }

  @Test
  public void thatParameterKeysAreSuggested() {
    getCaretCompletions("parameter")
        .assertContains(
            "$ref",
            "name",
            "in",
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
        .isOfSize(14);
  }

  @Test
  public void thatComponentKeysAreSuggested() {
    getCaretCompletions("component")
        .assertContains(
            "schemas",
            "responses",
            "parameters",
            "examples",
            "requestBodies",
            "headers",
            "securitySchemes",
            "links",
            "callbacks")
        .isOfSize(9);
  }

  @Test
  public void thatTagKeysAreSuggested() {
    getCaretCompletions("tags").assertContains("name", "description", "externalDocs").isOfSize(3);
  }

  @Test
  public void thatContactKeysAreSuggested() {
    getCaretCompletions("contact").assertContains("name", "url", "email").isOfSize(3);
  }

  @Test
  public void thatLicenseKeysAreSuggested() {
    getCaretCompletions("license").assertContains("name", "url").isOfSize(2);
  }

  @Test
  public void thatRequestBodyKeysAreSuggested() {
    getCaretCompletions("request_body")
        .assertContains("$ref", "description", "content", "required")
        .isOfSize(4);
  }

  @Test
  public void thatRequestBodyMediaTypeKeysAreSuggested() {
    getCaretCompletions("request_body_media_type")
        .assertContains("schema", "example", "examples", "encoding")
        .isOfSize(4);
  }

  @Test
  public void thatMediaTypeSchemaKeysAreSuggested() {
    verifySchemaIsSuggested("media_type_schema");
  }

  @Test
  public void thatExamplesKeysAreSuggested() {
    getCaretCompletions("media_type_example")
        .assertContains("$ref", "summary", "description", "value", "externalValue")
        .isOfSize(5);
  }

  @Test
  public void thatEncodingKeysAreSuggested() {
    getCaretCompletions("media_type_encoding")
        .assertContains("contentType", "headers", "style", "explode", "allowReserved")
        .isOfSize(5);
  }

  @Test
  public void thatResponseKeysAreSuggested() {
    getCaretCompletions("response")
        .assertContains("$ref", "description", "headers", "content", "links")
        .isOfSize(5);
  }

  @Test
  public void thatResponsesKeysAreSuggested() {
    getCaretCompletions("responses").assertContains("default", "200", "201").isOfSize(62);
  }

  @Test
  public void thatHeaderKeysAreSuggested() {
    getCaretCompletions("header")
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

  @Test
  public void thatResponseBodyMediaTypeKeysAreSuggested() {
    getCaretCompletions("response_media_type")
        .assertContains("schema", "example", "examples", "encoding")
        .isOfSize(4);
  }

  @Test
  public void thatLinkKeysAreSuggested() {
    getCaretCompletions("link_server")
        .assertContains("url", "description", "variables")
        .isOfSize(3);
  }

  @Test
  public void thatCallbackKeysAreSuggested() {
    getCaretCompletions("callback")
        .assertContains(
            "$ref",
            "description",
            "summary",
            "get",
            "put",
            "post",
            "delete",
            "options",
            "head",
            "patch",
            "trace",
            "servers",
            "parameters")
        .isOfSize(13);
  }

  @Test
  public void thatSchemaDefinitionKeysAreSuggested() {
    verifySchemaIsSuggested("component_schema");
  }

  @Test
  public void thatResponseDefinitionKeysAreSuggested() {
    getCaretCompletions("component_response")
        .assertContains("$ref", "description", "headers", "content", "links")
        .isOfSize(5);
  }

  @Test
  public void thatParameterDefinitionKeysAreSuggested() {
    getCaretCompletions("component_parameter")
        .assertContains(
            "$ref",
            "name",
            "in",
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
        .isOfSize(14);
  }

  @Test
  public void thatExampleDefinitionKeysAreSuggested() {
    getCaretCompletions("component_example")
        .assertContains("$ref", "summary", "description", "value", "externalValue")
        .isOfSize(5);
  }

  @Test
  public void thatRequestBodyDefinitionKeysAreSuggested() {
    getCaretCompletions("component_request_body")
        .assertContains("$ref", "description", "content", "required")
        .isOfSize(4);
  }

  @Test
  public void thatHeaderDefinitionKeysAreSuggested() {
    getCaretCompletions("component_header")
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

  @Test
  public void thatSecuritySchemeDefinitionKeysAreSuggested() {
    getCaretCompletions("component_security_scheme")
        .assertContains(
            "$ref",
            "type",
            "description",
            "name",
            "in",
            "scheme",
            "bearerFormat",
            "flows",
            "openIdConnectUrl")
        .isOfSize(9);
  }

  @Test
  public void thatLinkDefinitionKeysAreSuggested() {
    getCaretCompletions("component_link")
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

  @Test
  public void thatCallbackDefinitionKeysAreSuggested() {
    getCaretCompletions("component_callback")
        .assertContains(
            "$ref",
            "description",
            "summary",
            "get",
            "put",
            "post",
            "delete",
            "options",
            "head",
            "patch",
            "trace",
            "servers",
            "parameters")
        .isOfSize(13);
  }

  @Test
  public void thatExternalDocsKeysAreSuggested() {
    getCaretCompletions("external_docs").assertContains("description", "url").isOfSize(2);
  }

  @Test
  public void thatResponseContentKeysAreSuggested() {
    getCaretCompletions("response_content").assertContainsOne("application/json").isOfSize(77);
  }

  @Test
  public void thatExampleKeysAreSuggested() {
    getCaretCompletions("example")
        .assertContains("$ref", "summary", "description", "value", "externalValue")
        .isOfSize(5);
  }

  @Test
  public void thatAllOfSchemaIsSuggested() {
    verifySchemaIsSuggested("schema_all_of");
  }

  @Test
  public void thatAnyOfSchemaIsSuggested() {
    verifySchemaIsSuggested("schema_any_of");
  }

  @Test
  public void thatItemsSchemaIsSuggested() {
    verifySchemaIsSuggested("schema_items");
  }

  @Test
  public void thatNotSchemaIsSuggested() {
    verifySchemaIsSuggested("schema_not");
  }

  @Test
  public void thatOneOfSchemaIsSuggested() {
    verifySchemaIsSuggested("schema_one_of");
  }

  @Test
  public void thatPropertiesSchemaIsSuggested() {
    verifySchemaIsSuggested("schema_properties");
  }

  @Test
  public void thatDiscriminatorKeysAreSuggested() {
    getCaretCompletions("discriminator").assertContains("propertyName", "mapping").isOfSize(2);
  }

  private void verifySchemaIsSuggested(String file) {
    getCaretCompletions(file)
        .assertContains(
            "title",
            "multipleOf",
            "maximum",
            "exclusiveMaximum",
            "minimum",
            "exclusiveMinimum",
            "maxLength",
            "minLength",
            "pattern",
            "maxItems",
            "minItems",
            "uniqueItems",
            "maxProperties",
            "minProperties",
            "required",
            "enum",
            "type",
            "allOf",
            "oneOf",
            "anyOf",
            "not",
            "items",
            "properties",
            "additionalProperties",
            "description",
            "format",
            "default",
            "$ref",
            "nullable",
            "discriminator",
            "readOnly",
            "writeOnly",
            "xml",
            "externalDocs",
            "example",
            "deprecated")
        .isOfSize(36);
  }
}
