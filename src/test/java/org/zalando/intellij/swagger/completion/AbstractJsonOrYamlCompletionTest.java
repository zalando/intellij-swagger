package org.zalando.intellij.swagger.completion;

import org.jetbrains.annotations.NotNull;
import org.junit.After;
import org.zalando.intellij.swagger.assertion.AssertableList;
import org.zalando.intellij.swagger.fixture.SwaggerFixture;
import org.zalando.intellij.swagger.fixture.SwaggerFixture.JsonOrYaml;

public abstract class AbstractJsonOrYamlCompletionTest {
    private SwaggerFixture myFixture;
    private final JsonOrYaml myJsonOrYaml;

    protected AbstractJsonOrYamlCompletionTest(@NotNull JsonOrYaml jsonOrYaml) {
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
    protected final AssertableList getCaretCompletions(@NotNull String testFileNoExt) {
        return myFixture.getCompletions(testFileNoExt, myJsonOrYaml);
    }

    protected final void completeAndCheckResultsByFile(@NotNull String inputFileNoExt) {
        String afterFileName = getAfterFileName(inputFileNoExt);
        completeAndCheckResultsByFile(inputFileNoExt, afterFileName);
    }

    protected final void completeAndCheckResultsByFile(@NotNull String inputFileNoExt, @NotNull String afterFileNoExt) {
        myFixture.complete(inputFileNoExt, myJsonOrYaml);
        myFixture.checkResultByFile(afterFileNoExt, myJsonOrYaml);
    }

    @NotNull
    private String getAfterFileName(final @NotNull String beforeFile) {
        return beforeFile + "_after";
    }

}
