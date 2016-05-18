package org.zalando.intellij.swagger.completion;

import org.junit.Ignore;
import org.zalando.intellij.swagger.SwaggerCodeInsightFixtureTestCase;

import java.util.List;

public abstract class CaretCompletionTestBase extends SwaggerCodeInsightFixtureTestCase {

    protected CaretCompletionTestBase(boolean yamlNotJson) {
        super(yamlNotJson);
    }

    protected String getTestDataFolder() {
        return TEST_DATA_ROOT + "/completion";
    }

    @Ignore("Expected Failure. Note: @Ignore does not affect junit3, so it is additionally renamed to _test*")
    public void _testEnum_parameters_in() {
        getCaretCompletions().assertContains("path", "header", "query", "formData", "body");
    }

    public void testMediaType_produces() {
        getCaretCompletions()
                .assertNotContains("consumes", "produces")
                .assertNotContains("paths")
                .assertContains("application/xml", "image/*", "text/plain");
    }

    public void testMediaType_consumes() {
        getCaretCompletions()
                .assertNotContains("consumes", "produces")
                .assertNotContains("paths")
                .assertContains("application/xml", "image/*", "text/plain");
    }

    public void testTopLevel() {
        getCaretCompletions()
                .assertContains("basePath", "produces", "consumes", "schemes", "host", "paths")
                .assertContains("tags", "parameters", "responses")
                .assertNotContains("head", "get", "post")
                .assertNotContains("operationId");
    }

    private AssertableList<String> getCaretCompletions() {
        String testDataFile = getTestDataFileName();
        return getCaretCompletions(testDataFile);
    }

    private AssertableList<String> getCaretCompletions(String testDataFile) {
        List<String> variants = myFixture.getCompletionVariants(testDataFile);
        assertNotNull(variants);
        return new AssertableList<>(variants);
    }

}
