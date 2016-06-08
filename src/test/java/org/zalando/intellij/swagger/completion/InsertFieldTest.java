package org.zalando.intellij.swagger.completion;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.zalando.intellij.swagger.fixture.SwaggerFixture.JsonOrYaml;

@RunWith(Parameterized.class)
public class InsertFieldTest extends AbstractJsonOrYamlCompletionTest {
    public InsertFieldTest(JsonOrYaml jsonOrYaml) {
        super(jsonOrYaml);
    }

    @Parameterized.Parameters(name = "inputKind: {0}")
    public static Object[] parameters() {
        return JsonOrYaml.values();
    }

    @Before
    public void setUpBefore() throws Exception {
        useResourceFolder("testing/insert/field");
    }

    @Test
    public void thatStringFieldIsHandled() {
        completeAndCheckResultsByFile("string");
    }

    @Test
    public void thatInfoFieldIsPopulated() {
        completeAndCheckResultsByFile("info");
    }

    @Test
    public void thatContactFieldIsPopulated() {
        completeAndCheckResultsByFile("contact");
    }

    @Test
    public void thatExternalDocsFieldIsPopulated() {
        completeAndCheckResultsByFile("external_docs");
    }

    @Test
    public void thatItemsFieldIsPopulated() {
        completeAndCheckResultsByFile("items");
    }

    @Test
    public void thatLicenseFieldIsPopulated() {
        completeAndCheckResultsByFile("license");
    }

    @Test
    public void thatOperationFieldIsPopulated() {
        completeAndCheckResultsByFile("operation");
    }

    @Test
    public void thatResponseFieldIsPopulated() {
        completeAndCheckResultsByFile("response");
    }

    @Test
    public void thatXmlFieldIsPopulated() {
        completeAndCheckResultsByFile("xml");
    }

    @Test
    public void thatArrayFieldIsHandled() {
        completeAndCheckResultsByFile("array");
    }

    @Test
    public void testCommaAfterMidwayObject() {
        completeAndCheckResultsByFile("object_midway");
    }

    @Test
    public void testCommaAfterMidwayArray() {
        completeAndCheckResultsByFile("array_midway");
    }

    @Test
    public void thatRefFieldValueIsQuoted() {
        completeAndCheckResultsByFile("ref");
    }
}
