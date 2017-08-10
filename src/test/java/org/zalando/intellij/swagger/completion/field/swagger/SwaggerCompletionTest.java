package org.zalando.intellij.swagger.completion.field.swagger;

import com.intellij.util.net.HTTPMethod;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.zalando.intellij.swagger.completion.JsonAndYamlCompletionTest;
import org.zalando.intellij.swagger.fixture.Format;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

@RunWith(Parameterized.class)
public class SwaggerCompletionTest extends JsonAndYamlCompletionTest {

    public SwaggerCompletionTest(Format format) {
        super(format, "testing/completion/field/swagger/");
    }

    @Parameterized.Parameters(name = "inputKind: {0}")
    public static Object[] parameters() {
        return Format.values();
    }

    @Test
    public void thatRootKeyIsCompleted() {
        getCaretCompletions("root")
                .assertContains("basePath", "produces", "consumes", "schemes",
                        "paths", "tags", "parameters", "responses")
                .assertNotContains("head", "get", "post", "operationId");
    }

    @Test
    public void thatExistingKeysAreNotShown() {
        getCaretCompletions("root").assertNotContains("swagger", "host");
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
    public void thatInfoKeysAreSuggested() {
        getCaretCompletions("info")
                .assertContains("title", "description", "termsOfService", "contact", "license", "version")
                .isOfSize(6);
    }

    @Test
    public void thatContactKeysAreSuggested() {
        getCaretCompletions("contact")
                .assertContains("name", "url", "email")
                .isOfSize(3);
    }

    @Test
    public void thatLicenseKeysAreSuggested() {
        getCaretCompletions("license")
                .assertContains("name", "url")
                .isOfSize(2);
    }

    @Test
    public void thatPathKeysAreSuggested() {
        getCaretCompletions("path")
                .assertContains("$ref", "get", "put", "post", "delete", "options", "head", "patch", "parameters")
                .isOfSize(9);
    }

    @Test
    public void thatOperationKeysAreSuggested() {
        getCaretCompletions("operation")
                .assertContains("tags", "summary", "description", "externalDocs", "operationId", "consumes",
                        "produces", "parameters", "responses", "schemes", "deprecated", "security")
                .isOfSize(12);
    }

    @Test
    public void thatExternalDocsKeysAreSuggested() {
        getCaretCompletions("external_docs")
                .assertContains("description", "url")
                .isOfSize(2);
    }

    @Test
    public void thatExternalDocsKeysInSchemaAreSuggested() {
        getCaretCompletions("external_docs_in_schema")
                .assertContains("description", "url")
                .isOfSize(2);
    }

    @Test
    public void thatExternalDocsKeysInRootAreSuggested() {
        getCaretCompletions("external_docs_in_root")
                .assertContains("description", "url")
                .isOfSize(2);
    }

    @Test
    public void thatParametersKeysAreSuggested() {
        getCaretCompletions("parameters")
                .assertContains("$ref", "name", "in", "description", "required", "schema", "type", "format",
                        "allowEmptyValue", "items", "collectionFormat", "default", "maximum", "exclusiveMaximum",
                        "minimum", "exclusiveMinimum", "maxLength", "minLength", "pattern", "maxItems", "minItems",
                        "uniqueItems", "enum", "multipleOf")
                .isOfSize(24);
    }

    @Test
    public void thatItemsKeysInOperationParametersAreSuggested() {
        getCaretCompletions("items_in_operation_parameters")
                .assertContains("type", "format", "items", "collectionFormat", "default", "maximum",
                        "exclusiveMaximum", "minimum", "exclusiveMinimum", "maxLength", "minLength", "pattern",
                        "maxItems", "minItems", "uniqueItems", "multipleOf")
                .isOfSize(17);
    }

    @Test
    public void thatItemsKeysInPathParametersAreSuggested() {
        getCaretCompletions("items_in_path_parameters")
                .assertContains("type", "format", "items", "collectionFormat", "default", "maximum",
                        "exclusiveMaximum", "minimum", "exclusiveMinimum", "maxLength", "minLength", "pattern",
                        "maxItems", "minItems", "uniqueItems", "multipleOf")
                .isOfSize(17);
    }

    @Test
    public void thatItemsKeysInSchemaAreSuggested() {
        getCaretCompletions("items_in_schema")
                .assertContains("$ref", "format", "title", "description", "default", "multipleOf", "maximum",
                        "exclusiveMaximum", "minimum", "exclusiveMinimum", "maxLength", "minLength", "pattern",
                        "maxItems", "minItems", "uniqueItems", "maxProperties", "minProperties", "required", "enum",
                        "type", "items", "allOf", "properties", "additionalProperties", "discriminator", "readOnly",
                        "xml", "externalDocs", "example")
                .isOfSize(30);
    }

    @Test
    public void thatItemsKeysInHeaderAreSuggested() {
        getCaretCompletions("items_in_header")
                .assertContains("type", "format", "items", "collectionFormat", "default", "maximum",
                        "exclusiveMaximum", "minimum", "exclusiveMinimum", "maxLength", "minLength", "pattern",
                        "maxItems", "minItems", "uniqueItems", "multipleOf")
                .isOfSize(17);
    }

    @Test
    public void thatResponsesKeysAreSuggested() {
        getCaretCompletions("responses")
                .assertContains("default", "200", "201")
                .isOfSize(59);
    }

    @Test
    public void thatResponseKeysAreSuggested() {
        getCaretCompletions("response")
                .assertContains("$ref", "description", "schema", "headers", "examples")
                .isOfSize(5);
    }

    @Test
    public void thatHeaderKeysAreSuggested() {
        getCaretCompletions("header")
                .assertContains("description", "type", "format", "items", "collectionFormat", "default", "maximum",
                        "exclusiveMaximum", "minimum", "exclusiveMinimum", "maxLength", "minLength", "pattern",
                        "maxItems", "minItems", "uniqueItems", "enum", "multipleOf")
                .isOfSize(18);
    }

    @Test
    public void thatTagsKeysAreSuggested() {
        getCaretCompletions("tags")
                .assertContains("name", "description", "externalDocs")
                .isOfSize(3);
    }

    @Test
    public void thatSecurityDefinitionsKeysAreSuggested() {
        getCaretCompletions("security_definitions")
                .assertContains("type", "description", "name", "in", "flow", "authorizationUrl", "tokenUrl", "scopes")
                .isOfSize(8);
    }

    @Test
    public void thatSchemaKeysAreSuggested() {
        getCaretCompletions("schema")
                .assertContains("$ref", "format", "title", "description", "default", "multipleOf", "maximum",
                        "exclusiveMaximum", "minimum", "exclusiveMinimum", "maxLength", "minLength", "pattern",
                        "maxItems", "minItems", "uniqueItems", "maxProperties", "minProperties", "required", "enum",
                        "type", "items", "allOf", "properties", "additionalProperties", "discriminator", "readOnly",
                        "xml", "externalDocs", "example")
                .isOfSize(30);
    }

    @Test
    public void thatXmlKeysAreSuggested() {
        getCaretCompletions("xml")
                .assertContains("name", "namespace", "prefix", "attribute", "wrapped")
                .isOfSize(5);
    }

    @Test
    public void thatDefinitionsKeysAreSuggested() {
        getCaretCompletions("definitions")
                .assertContains("$ref", "format", "title", "description", "default", "multipleOf", "maximum",
                        "exclusiveMaximum", "minimum", "exclusiveMinimum", "maxLength", "minLength", "pattern",
                        "maxItems", "minItems", "uniqueItems", "maxProperties", "minProperties", "required", "enum",
                        "type", "items", "allOf", "properties", "additionalProperties", "discriminator", "readOnly",
                        "xml", "externalDocs", "example")
                .isOfSize(30);
    }

    @Test
    public void thatDefinitionsPropertiesKeysAreSuggested() {
        String[] expectedCompletion = {"$ref", "format", "title", "description", "default", "multipleOf", "maximum",
                "exclusiveMaximum", "minimum", "exclusiveMinimum", "maxLength", "minLength", "pattern",
                "maxItems", "minItems", "uniqueItems", "maxProperties", "minProperties", "required", "enum",
                "type", "items", "allOf", "properties", "additionalProperties", "discriminator", "readOnly",
                "xml", "externalDocs", "example"};

        getCaretCompletions("schema_properties")
                .assertContains(expectedCompletion)
                .isOfSize(30);
        getCaretCompletions("schema_additional_properties")
                .assertContains(expectedCompletion)
                .isOfSize(30);
    }

    @Test
    public void thatParameterDefinitionKeysAreSuggested() {
        getCaretCompletions("parameter_definition")
                .assertContains("name", "in", "description", "required", "schema", "type", "format",
                        "allowEmptyValue", "items", "collectionFormat", "default", "maximum", "exclusiveMaximum",
                        "minimum", "exclusiveMinimum", "maxLength", "minLength", "pattern", "maxItems", "minItems",
                        "uniqueItems", "enum", "multipleOf")
                .isOfSize(23);
    }

    @Test
    public void thatHeadersAreSuggested() {
        getCaretCompletions("headers")
                .assertContains("Accept", "Location")
                .isOfSize(55);
    }

    @Test
    public void thatResponseDefinitionKeysAreSuggested() {
        getCaretCompletions("response_definition")
                .assertContains("description", "schema", "headers", "examples")
                .isOfSize(4);
    }

    @Test
    public void thatSecurityFieldsInRootAreSuggested() {
        getCaretCompletions("security_in_root")
                .assertContains("githubAccessCode")
                .isOfSize(1);
    }

    @Test
    public void thatSecurityFieldsInOperationAreSuggested() {
        getCaretCompletions("security_in_operation")
                .assertContains("petstoreImplicit")
                .isOfSize(1);
    }

}
