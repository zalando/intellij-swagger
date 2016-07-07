package org.zalando.intellij.swagger.completion.value;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.zalando.intellij.swagger.completion.AbstractJsonOrYamlCompletionTest;
import org.zalando.intellij.swagger.fixture.SwaggerFixture.JsonOrYaml;

@RunWith(Parameterized.class)
public class ValueCompletionTest extends AbstractJsonOrYamlCompletionTest {

    @Parameterized.Parameters(name = "inputKind: {0}")
    public static Object[] parameters() {
        return JsonOrYaml.values();
    }

    public ValueCompletionTest(JsonOrYaml jsonOrYaml) {
        super(jsonOrYaml);
    }

    @Before
    public void setUpBefore() throws Exception {
        useResourceFolder("testing/completion/value");
    }

    @Test
    public void thatTypesAreSuggested() {
        getCaretCompletions("types_all")
                .assertContains("integer", "number", "string", "boolean", "array")
                .isOfSize(5);
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
    public void testDefinitionRefValueInSchema() throws Exception {
        getCaretCompletions("definition_ref_value_in_schema")
                .assertContains("#/definitions/Pets", "#/definitions/Error")
                .isOfSize(2);
    }

    @Test
    public void testDefinitionRefValueInItems() throws Exception {
        getCaretCompletions("definition_ref_value_in_items")
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
    public void testResponseRefValue() throws Exception {
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
        getCaretCompletions("boolean_parameters_required")
                .assertContains("true", "false");
    }

    @Test
    public void thatBooleanValuesAreNotSuggestedForRequiredKeyInSchema() {
        getCaretCompletions("required_key_in_schema")
                .assertNotContains("true", "false");
    }

    @Test
    public void thatRootSecurityScopeNameFieldsAreSuggested() {
        getCaretCompletions("security_value_in_root")
                .assertContains("admin:public_key")
                .isOfSize(1);
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
        getCaretCompletions("schemes")
                .assertContains("http", "https", "ws", "wss")
                .isOfSize(4);
    }

    @Test
    public void thatOnlyUniqueArrayStringValuesAreSuggested() {
        getCaretCompletions("unique_strings_in_array")
                .assertContains("https", "ws", "wss")
                .isOfSize(3);
    }

    @Test
    public void thatTagValuesAreSuggested() {
        getCaretCompletions("tags")
                .assertContains("tag1", "tag2")
                .isOfSize(2);
    }
}
