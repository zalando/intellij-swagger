package org.zalando.intellij.swagger.documentation.swagger;

import org.zalando.intellij.swagger.documentation.DocumentationTest;

public class SwaggerDocumentationTest extends DocumentationTest {

  public SwaggerDocumentationTest() {
    super("documentation/swagger");
  }

  public void testYamlSchema() {
    final String expected = "<div>Schema: object<br/>Description</div>";
    testQuickDocumentation("/yaml/schema_ref.yaml", expected);
  }

  public void testJsonSchema() {
    final String expected = "<div>Schema: object<br/>Description</div>";
    testQuickDocumentation("/json/schema_ref.json", expected);
  }

  public void testYamlParameter() {
    final String expected = "<div>name: parameter_name<br/>in: query<br/>Description</div>";
    testQuickDocumentation("/yaml/parameter_ref.yaml", expected);
  }

  public void testJsonParameter() {
    final String expected = "<div>name: parameter_name<br/>in: query<br/>Description</div>";
    testQuickDocumentation("/json/parameter_ref.json", expected);
  }

  public void testYamlResponse() {
    final String expected = "<div>description</div>";
    testQuickDocumentation("/yaml/response_ref.yaml", expected);
  }

  public void testJsonResponse() {
    final String expected = "<div>description</div>";
    testQuickDocumentation("/json/response_ref.json", expected);
  }
}
