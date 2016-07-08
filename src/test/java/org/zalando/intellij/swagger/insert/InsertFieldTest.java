package org.zalando.intellij.swagger.insert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.zalando.intellij.swagger.completion.CompletionTest;
import org.zalando.intellij.swagger.fixture.Format;

@RunWith(Parameterized.class)
public class InsertFieldTest extends CompletionTest {

    public InsertFieldTest(Format format) {
        super(format, "testing/insert/field");
    }

    @Parameterized.Parameters(name = "inputKind: {0}")
    public static Object[] parameters() {
        return Format.values();
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

    @Test
    public void thatHeadersFieldIsPopulated() {
        completeAndCheckResultsByFile("headers");
    }

}
