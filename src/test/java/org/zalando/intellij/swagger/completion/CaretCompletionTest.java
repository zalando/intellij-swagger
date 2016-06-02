package org.zalando.intellij.swagger.completion;

import com.intellij.util.net.HTTPMethod;
import org.junit.Before;
import org.junit.Ignore;
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

    @Ignore("See issue #30")
    @Test
    public void enumValuesCompletion_ParametersIn() {
        getCaretCompletions("enum_parameters_in")
                .assertContains("path", "header", "query", "formData", "body");
    }

    @Ignore("See issue #21")
    @Test
    public void booleanValuesCompletion_ParametersRequired() {
        getCaretCompletions("boolean_parameters_required")
                .assertContains("true", "false");
    }

}
