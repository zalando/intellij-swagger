package org.zalando.intellij.swagger.completion;

import org.jetbrains.annotations.NotNull;
import org.junit.After;
import org.zalando.intellij.swagger.fixture.SwaggerFixture;
import org.zalando.intellij.swagger.fixture.SwaggerFixture.JsonOrYaml;

public abstract class AbstractJsonOrYamlCompletionTest {
    private SwaggerFixture myFixture;
    private final JsonOrYaml myJsonOrYaml;

    public AbstractJsonOrYamlCompletionTest(@NotNull JsonOrYaml jsonOrYaml) {
        myJsonOrYaml = jsonOrYaml;
    }

    protected void useResourceFolder(@NotNull String testDataFolder) throws Exception {
        myFixture = SwaggerFixture.forResourceFolder(testDataFolder);
    }

    @After
    public void tearDownAfter() throws Exception {
        if (myFixture != null) {
            myFixture.tearDown();
        }
    }

    @NotNull
    final SwaggerFixture.AssertableList getCaretCompletions(@NotNull String testFileNoExt) {
        return myFixture.getCompletions(testFileNoExt, myJsonOrYaml);
    }

    /**
     * This helper will invoke completion based on &lt;caret&gt; in the input file and
     * will check the results against the file named "&lt;input file name&gt;_after.*"
     */
    final void completeAndCheckResultsByFile(@NotNull String inputFileNoExt) {
        String afterFileName = getAfterFileName(inputFileNoExt);
        completeAndCheckResultsByFile(inputFileNoExt, afterFileName);
    }

    final void completeAndCheckResultsByFile(@NotNull String inputFileNoExt, @NotNull String afterFileNoExt) {
        myFixture.complete(inputFileNoExt, myJsonOrYaml);
        myFixture.checkResultByFile(afterFileNoExt, myJsonOrYaml);
    }

    protected void renameAtCaretAndCheckResultsByFile(final String newName,
                                                      @NotNull String beforeFile) {
        myFixture.rename(newName, beforeFile, myJsonOrYaml);
        myFixture.checkResultByFile(getAfterFileName(beforeFile), myJsonOrYaml);
    }

    @NotNull
    private String getAfterFileName(final @NotNull String beforeFile) {
        return beforeFile + "_after";
    }

}
