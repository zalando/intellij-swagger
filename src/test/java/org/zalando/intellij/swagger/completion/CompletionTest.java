package org.zalando.intellij.swagger.completion;

import org.jetbrains.annotations.NotNull;
import org.junit.After;
import org.zalando.intellij.swagger.assertion.AssertableList;
import org.zalando.intellij.swagger.fixture.Format;
import org.zalando.intellij.swagger.fixture.SwaggerFixture;

public abstract class CompletionTest {

    private final SwaggerFixture myFixture;
    private final Format myFormat;

    protected CompletionTest(@NotNull Format format, @NotNull String testDataFolder) {
        myFormat = format;
        myFixture = SwaggerFixture.forResourceFolder(testDataFolder);
    }

    @After
    public void tearDownAfter() throws Exception {
        myFixture.tearDown();
    }

    @NotNull
    protected final AssertableList getCaretCompletions(@NotNull String testFileNoExt) {
        return myFixture.getCompletions(testFileNoExt, myFormat);
    }

    protected final void completeAndCheckResultsByFile(@NotNull String inputFileNoExt) {
        String afterFileName = getAfterFileName(inputFileNoExt);
        completeAndCheckResultsByFile(inputFileNoExt, afterFileName);
    }

    protected final void completeAndCheckResultsByFile(@NotNull String inputFileNoExt, @NotNull String afterFileNoExt) {
        myFixture.complete(inputFileNoExt, myFormat);
        myFixture.checkResultByFile(afterFileNoExt, myFormat);
    }

    @NotNull
    private String getAfterFileName(final @NotNull String beforeFile) {
        return beforeFile + "_after";
    }

}
