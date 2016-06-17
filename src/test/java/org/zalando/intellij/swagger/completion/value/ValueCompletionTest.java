package org.zalando.intellij.swagger.completion.value;

import org.junit.Before;
import org.junit.Ignore;
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
    public void testJsonSchemaPrimitiveTypes() {
        getCaretCompletions("types_all")
                .assertContains("integer", "number", "string", "boolean", "array")
                .isOfSize(5);
    }

    @Ignore("See issue 13")
    @Test
    public void testSwaggerSpecificTypes() {
        // swagger specific type 'file' is available only for some specific places,
        // so I am not sure that this test is correct and it is allowed *here*
        getCaretCompletions("types_all")
                .assertContains("file");
    }

    @Ignore("See issue 13")
    @Test
    public void testJsonSchemaCompositeTypes() {
        getCaretCompletions("types_all")
                .assertContains("object")
                .assertContains("array");
    }

    @Ignore("See issue 13")
    @Test
    public void testUnrestrictedFormats() {
        getCaretCompletions("formats_all")
                .assertContains("int32", "int64") // for type: integer
                .assertContains("byte", "binary", "date", "date-time", "password") // for type: string
                .assertContains("float", "double"); // for type: number
    }

    @Ignore("See issue 13")
    @Test
    public void testIntegerFormats() {
        getCaretCompletions("formats_integer")
                .assertContains("int32", "int64")
                .assertNotContains("byte", "binary", "date", "date-time", "password")
                .assertNotContains("float", "double");
    }

    @Ignore("See issue 13")
    @Test
    public void testStringFormats() {
        getCaretCompletions("formats_string")
                .assertNotContains("int32", "int64")
                .assertContains("byte", "binary", "date", "date-time", "password")
                .assertNotContains("float", "double");
    }

    @Ignore("See issue 13")
    @Test
    public void testNumberFormats() {
        getCaretCompletions("formats_number")
                .assertNotContains("int32", "int64")
                .assertNotContains("byte", "binary", "date", "date-time", "password")
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

}
