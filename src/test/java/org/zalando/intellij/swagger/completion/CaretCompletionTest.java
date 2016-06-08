package org.zalando.intellij.swagger.completion;

import com.intellij.util.net.HTTPMethod;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.zalando.intellij.swagger.fixture.SwaggerFixture.JsonOrYaml;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

@RunWith(Parameterized.class)
public class CaretCompletionTest extends AbstractJsonOrYamlCompletionTest {
    public CaretCompletionTest(JsonOrYaml jsonOrYaml) {
        super(jsonOrYaml);
    }

    @Parameterized.Parameters(name = "inputKind: {0}")
    public static Object[] parameters() {
        return JsonOrYaml.values();
    }

    @Before
    public void setUpBefore() throws Exception {
        useResourceFolder("testing/completion");
    }

    @Test
    public void thatRootKeyIsCompleted() {
        getCaretCompletions("top_level")
                .assertContains("basePath", "produces", "consumes", "schemes",
                        "paths", "tags", "parameters", "responses")
                .assertNotContains("head", "get", "post", "operationId");
    }

    @Test
    public void thatExistingKeysAreNotShown() {
        getCaretCompletions("top_level").assertNotContains("swagger", "host");
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
                .assertNotContains("consumes", "produces", "paths")
                .assertContains("application/xml", "image/*", "text/plain");
    }

    @Test
    public void testHttpOperations() {
        //see swagger spec, all but 'trace' operations are supported
        Collection<String> allButTrace = Arrays.stream(HTTPMethod.values())
                .filter(m -> m != HTTPMethod.TRACE)
                .map(HTTPMethod::name)
                .map(String::toLowerCase)
                .sorted()
                .collect(Collectors.toList());

        getCaretCompletions("http_operations")
                .assertContains(allButTrace)
                .assertContains("$ref")
                .assertNotContains("trace");
    }

    @Test
    public void testDefinitionRefValue() throws Exception {
        getCaretCompletions("definition_ref_value")
                .assertContains("#/definitions/Pets", "#/definitions/Error")
                .isOfSize(2);
    }

    @Test
    public void testParameterRefValue() throws Exception {
        getCaretCompletions("parameter_ref_value")
                .assertContains("#/parameters/Dog")
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
        getCaretCompletions("boolean_parameters_required")
                .assertContains("true", "false");
    }

    @Test
    public void thatBooleanValuesAreNotSuggestedForRequiredKeyInSchema() {
        getCaretCompletions("required_key_in_schema")
                .assertNotContains("true", "false");
    }

    @Test
    public void thatInfoKeysAreSuggested() {
        getCaretCompletions("field/info")
                .assertContains("title", "description", "termsOfService", "contact", "license", "version")
                .isOfSize(6);
    }

    @Test
    public void thatContactKeysAreSuggested() {
        getCaretCompletions("field/contact")
                .assertContains("name", "url", "email")
                .isOfSize(3);
    }

    @Test
    public void thatLicenseKeysAreSuggested() {
        getCaretCompletions("field/license")
                .assertContains("name", "url")
                .isOfSize(2);
    }

    @Test
    public void thatPathKeysAreSuggested() {
        getCaretCompletions("field/path")
                .assertContains("$ref", "get", "put", "post", "delete", "options", "head", "patch", "parameters")
                .isOfSize(9);
    }

    @Test
    public void thatOperationKeysAreSuggested() {
        getCaretCompletions("field/operation")
                .assertContains("tags", "summary", "description", "externalDocs", "operationId", "consumes",
                        "produces", "parameters", "responses", "schemes", "deprecated", "security")
                .isOfSize(12);
    }

    @Test
    public void thatExternalDocsKeysAreSuggested() {
        getCaretCompletions("field/external_docs")
                .assertContains("description", "url")
                .isOfSize(2);
    }

    @Test
    public void thatParametersKeysAreSuggested() {
        getCaretCompletions("field/parameters")
                .assertContains("name", "in", "description", "required", "schema", "type", "format",
                        "allowEmptyValue", "items", "collectionFormat", "default", "maximum", "exclusiveMaximum",
                        "minimum", "exclusiveMinimum", "maxLength", "minLength", "pattern", "maxItems", "minItems",
                        "uniqueItems", "enum", "multipleOf")
                .isOfSize(23);
    }

    @Test
    public void thatItemsKeysAreSuggested() {
        getCaretCompletions("field/items")
                .assertContains("type", "format", "items", "collectionFormat", "default", "maximum",
                        "exclusiveMaximum", "minimum", "exclusiveMinimum", "maxLength", "minLength", "pattern",
                        "maxItems", "minItems", "uniqueItems", "multipleOf")
                .isOfSize(17);
    }

    @Test
    public void thatResponsesKeysAreSuggested() {
        getCaretCompletions("field/responses")
                .assertContains("default", "200", "201")
                .isOfSize(59);
    }

    @Test
    public void thatResponseKeysAreSuggested() {
        getCaretCompletions("field/response")
                .assertContains("description", "schema", "headers", "examples")
                .isOfSize(4);
    }

    @Test
    public void thatHeadersKeysAreSuggested() {
        getCaretCompletions("field/headers")
                .assertContains("description", "type", "format", "items", "collectionFormat", "default", "maximum",
                        "exclusiveMaximum", "minimum", "exclusiveMinimum", "maxLength", "minLength", "pattern",
                        "maxItems", "minItems", "uniqueItems", "enum", "multipleOf")
                .isOfSize(18);
    }

    @Test
    public void thatTagsKeysAreSuggested() {
        getCaretCompletions("field/tags")
                .assertContains("name", "description", "externalDocs")
                .isOfSize(3);
    }

    @Test
    public void thatSecurityDefinitionsKeysAreSuggested() {
        getCaretCompletions("field/security_definitions")
                .assertContains("type", "description", "name", "in", "flow", "authorizationUrl", "tokenUrl", "scopes")
                .isOfSize(8);
    }

    @Test
    public void thatSchemaKeysAreSuggested() {
        getCaretCompletions("field/schema")
                .assertContains("$ref", "format", "title", "description", "default", "multipleOf", "maximum",
                        "exclusiveMaximum", "minimum", "exclusiveMinimum", "maxLength", "minLength", "pattern",
                        "maxItems", "minItems", "uniqueItems", "maxProperties", "minProperties", "required", "enum",
                        "type", "items", "allOf", "properties", "additionalProperties", "discriminator", "readOnly",
                        "xml", "externalDocs", "example")
                .isOfSize(30);
    }

    @Test
    public void thatXmlKeysAreSuggested() {
        getCaretCompletions("field/xml")
                .assertContains("name", "namespace", "prefix", "attribute", "wrapped")
                .isOfSize(5);
    }

    @Test
    public void thatDefinitionsKeysAreSuggested() {
        getCaretCompletions("field/definitions")
                .assertContains("$ref", "format", "title", "description", "default", "multipleOf", "maximum",
                        "exclusiveMaximum", "minimum", "exclusiveMinimum", "maxLength", "minLength", "pattern",
                        "maxItems", "minItems", "uniqueItems", "maxProperties", "minProperties", "required", "enum",
                        "type", "items", "allOf", "properties", "additionalProperties", "discriminator", "readOnly",
                        "xml", "externalDocs", "example")
                .isOfSize(30);
    }

    @Test
    public void thatParameterDefinitionKeysAreSuggested() {
        getCaretCompletions("field/parameter_definition")
                .assertContains("name", "in", "description", "required", "schema", "type", "format",
                        "allowEmptyValue", "items", "collectionFormat", "default", "maximum", "exclusiveMaximum",
                        "minimum", "exclusiveMinimum", "maxLength", "minLength", "pattern", "maxItems", "minItems",
                        "uniqueItems", "enum", "multipleOf")
                .isOfSize(23);
    }

}
