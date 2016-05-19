package org.zalando.intellij.swagger.completion;

import org.jetbrains.annotations.NotNull;
import org.junit.*;
import org.junit.rules.TestName;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.zalando.intellij.swagger.completion.SwaggerFixture.JsonOrYaml;

@RunWith(Parameterized.class)
public class CaretCompletionTest {
    @Rule
    public TestName testName = new TestName();

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
        myFixture = SwaggerFixture.forResourceFolder("testData/completion");
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
    public void testTopLevelConsumes() {
        getCaretCompletions("mediaType_consumes")
                .assertNotContains("consumes", "produces")
                .assertNotContains("paths")
                .assertContains("application/xml", "image/*", "text/plain");
    }

    @Test
    public void testTopLevelProduces() {
        getCaretCompletions("mediaType_produces")
                .assertNotContains("consumes", "produces")
                .assertNotContains("paths")
                .assertContains("application/xml", "image/*", "text/plain");
    }

    @Ignore("Expected, not yet implemented")
    @Test
    public void enumValuesCompletion_ParametersIn() {
        getCaretCompletions("enum_parameters_in")
                .assertContains("path", "header", "query", "formData", "body");
    }

    @NotNull
    private SwaggerFixture.AssertableList<String> getCaretCompletions(@NotNull String testFileNoExt) {
        return myFixture.getCompletions(testFileNoExt, myJsonOrYaml);
    }

}
