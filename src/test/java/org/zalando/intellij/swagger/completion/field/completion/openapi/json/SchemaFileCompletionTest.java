package org.zalando.intellij.swagger.completion.field.completion.openapi.json;

import org.zalando.intellij.swagger.assertion.AssertableList;

public class SchemaFileCompletionTest extends PartialFileCompletionTest {

  public void testThatSingleSchemaFileIsAutoCompleted() {
    withSpecFiles("pet.json", "schema.json");

    final AssertableList completions = new AssertableList(geCompletions("pet.json"));

    assertSchemaCompletions(completions);
  }

  public void testThatSchemasFileIsAutoCompleted() {
    withSpecFiles("components.json", "schemas.json");

    final AssertableList completions = new AssertableList(geCompletions("components.json"));

    assertSchemaCompletions(completions);
  }

  public void testThatReferencedYamlFileIsAutoCompleted() {
    withSpecFiles("pet.yaml", "yaml_reference.json");

    final AssertableList completions = new AssertableList(geCompletions("pet.yaml"));

    assertSchemaCompletions(completions);
  }

  public void testThatReferencedJsonFileIsAutoCompleted() {
    withSpecFiles("pet.json", "json_reference.yaml");

    final AssertableList completions = new AssertableList(geCompletions("pet.json"));

    assertSchemaCompletions(completions);
  }

  private void assertSchemaCompletions(final AssertableList completions) {
    completions
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
