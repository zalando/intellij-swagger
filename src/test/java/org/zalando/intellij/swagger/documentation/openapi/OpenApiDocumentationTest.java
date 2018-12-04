package org.zalando.intellij.swagger.documentation.openapi;

import org.zalando.intellij.swagger.documentation.DocumentationTest;

public class OpenApiDocumentationTest extends DocumentationTest {

  public OpenApiDocumentationTest() {
    super("documentation/openapi");
  }

  public void testYamlSchema() {
    final String expected = "<div>Example: object</div>";
    testQuickDocumentation("/yaml/schema_ref.yaml", expected);
  }

  public void testJsonSchema() {
    final String expected = "<div>Example: object</div>";
    testQuickDocumentation("/json/schema_ref.json", expected);
  }

  public void testYamlSchemaWithDescription() {
    final String expected = "<div>Example: object<br/>some description</div>";
    testQuickDocumentation("/yaml/schema_ref_with_description.yaml", expected);
  }

  public void testJsonSchemaWithDescription() {
    final String expected = "<div>Example: object<br/>some description</div>";
    testQuickDocumentation("/json/schema_ref_with_description.json", expected);
  }

  public void testYamlSchemaWithoutType() {
    final String expected = "<div>Example: undefined type</div>";
    testQuickDocumentation("/yaml/schema_ref_without_type.yaml", expected);
  }

  public void testJsonSchemaWithoutType() {
    final String expected = "<div>Example: undefined type</div>";
    testQuickDocumentation("/json/schema_ref_without_type.json", expected);
  }

  public void testYamlResponse() {
    final String expected = "<div>Description</div>";
    testQuickDocumentation("/yaml/response_ref.yaml", expected);
  }

  public void testJsonResponse() {
    final String expected = "<div>Description</div>";
    testQuickDocumentation("/json/response_ref.json", expected);
  }

  public void testYamlParameter() {
    final String expected = "<div>name: parameter_name<br/>in: query<br/>Description</div>";
    testQuickDocumentation("/yaml/parameter_ref.yaml", expected);
  }

  public void testJsonParameter() {
    final String expected = "<div>name: parameter_name<br/>in: query<br/>Description</div>";
    testQuickDocumentation("/json/parameter_ref.json", expected);
  }

  public void testYamlParameterWithoutIn() {
    final String expected =
        "<div>name: parameter_name<br/>in: undefined location<br/>Description</div>";
    testQuickDocumentation("/yaml/parameter_without_in_ref.yaml", expected);
  }

  public void testJsonParameterWithoutIn() {
    final String expected =
        "<div>name: parameter_name<br/>in: undefined location<br/>Description</div>";
    testQuickDocumentation("/json/parameter_without_in_ref.json", expected);
  }

  public void testYamlExample() {
    final String expected = "<div>Summary<br/>Description</div>";
    testQuickDocumentation("/yaml/example_ref.yaml", expected);
  }

  public void testJsonExample() {
    final String expected = "<div>Summary<br/>Description</div>";
    testQuickDocumentation("/json/example_ref.json", expected);
  }

  public void testYamlRequestBody() {
    final String expected = "<div>Description</div>";
    testQuickDocumentation("/yaml/request_body_ref.yaml", expected);
  }

  public void testJsonRequestBody() {
    final String expected = "<div>Description</div>";
    testQuickDocumentation("/json/request_body_ref.json", expected);
  }

  public void testYamlHeader() {
    final String expected = "<div>Description</div>";
    testQuickDocumentation("/yaml/header_ref.yaml", expected);
  }

  public void testJsonHeader() {
    final String expected = "<div>Description</div>";
    testQuickDocumentation("/json/header_ref.json", expected);
  }

  public void testYamlLink() {
    final String expected = "<div>Description</div>";
    testQuickDocumentation("/yaml/link_ref.yaml", expected);
  }

  public void testJsonLink() {
    final String expected = "<div>Description</div>";
    testQuickDocumentation("/json/link_ref.json", expected);
  }
}
