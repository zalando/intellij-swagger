package org.zalando.intellij.swagger.completion;

import com.intellij.util.net.HTTPMethod;
import org.jetbrains.annotations.NotNull;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.zalando.intellij.swagger.completion.SwaggerFixture.JsonOrYaml;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

@RunWith(Parameterized.class)
public class CaretCompletionTest {
    private SwaggerFixture myFixture;
    private final JsonOrYaml myJsonOrYaml;

    @Parameterized.Parameters(name = "inputKind: {0}")
    public static Object[] parameters() {
        return JsonOrYaml.values();
    }

    public CaretCompletionTest(JsonOrYaml jsonOrYaml) {
        myJsonOrYaml = jsonOrYaml;
    }

    @Before
    public void setUpBefore() throws Exception {
        myFixture = SwaggerFixture.forResourceFolder("testing/completion");
    }

    @After
    public void tearDownAfter() throws Exception {
        myFixture.tearDown();
        myFixture = null;
    }

    @Test
    public void testTopLevelCompletion() {
        getCaretCompletions("topLevel")
                .assertContains("basePath", "produces", "consumes", "schemes", "host", "paths")
                .assertContains("tags", "parameters", "responses")
                .assertNotContains("head", "get", "post")
                .assertNotContains("operationId");
    }

    @Test
    public void testGlobalConsumes() {
        getCaretCompletions("media_type_consumes")
                .assertNotContains("consumes", "produces")
                .assertNotContains("paths")
                .assertContains("application/xml", "image/*", "text/plain");
    }

    @Test
    public void testGlobalProduces() {
        getCaretCompletions("media_type_produces")
                .assertNotContains("consumes", "produces")
                .assertNotContains("paths")
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

    @Ignore("Expected, not yet implemented")
    @Test
    public void enumValuesCompletion_ParametersIn() {
        getCaretCompletions("enum_parameters_in")
                .assertContains("path", "header", "query", "formData", "body");
    }

    @NotNull
    private SwaggerFixture.AssertableList getCaretCompletions(@NotNull String testFileNoExt) {
        return myFixture.getCompletions(testFileNoExt, myJsonOrYaml);
    }

}
