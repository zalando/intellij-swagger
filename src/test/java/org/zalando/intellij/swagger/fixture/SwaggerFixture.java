package org.zalando.intellij.swagger.fixture;

import com.intellij.codeInsight.completion.CompletionType;
import com.intellij.codeInsight.lookup.LookupManager;
import com.intellij.testFramework.LightProjectDescriptor;
import com.intellij.testFramework.fixtures.CodeInsightTestFixture;
import com.intellij.testFramework.fixtures.IdeaProjectTestFixture;
import com.intellij.testFramework.fixtures.IdeaTestFixtureFactory;
import com.intellij.testFramework.fixtures.TestFixtureBuilder;
import com.intellij.testFramework.fixtures.impl.LightTempDirTestFixtureImpl;
import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;
import org.hamcrest.core.IsNull;
import org.jetbrains.annotations.NotNull;
import org.junit.Assert;
import org.zalando.intellij.swagger.assertion.AssertableList;

public class SwaggerFixture {

  private final CodeInsightTestFixture myCodeInsightFixture;

  @NotNull
  public static SwaggerFixture forResourceFolder(@NotNull String resourceBasedPath) {
    try {
      String absolutePath = toAbsolutePath(resourceBasedPath);
      return new SwaggerFixture(absolutePath);
    } catch (final Exception e) {
      throw new RuntimeException(e);
    }
  }

  private SwaggerFixture(@NotNull String folderAbsolutePath) throws Exception {
    IdeaTestFixtureFactory factory = IdeaTestFixtureFactory.getFixtureFactory();
    LightProjectDescriptor projectDescriptor = LightProjectDescriptor.EMPTY_PROJECT_DESCRIPTOR;
    TestFixtureBuilder<IdeaProjectTestFixture> fixtureBuilder =
        factory.createLightFixtureBuilder(projectDescriptor);
    IdeaProjectTestFixture fixture = fixtureBuilder.getFixture();

    myCodeInsightFixture =
        factory.createCodeInsightFixture(fixture, new LightTempDirTestFixtureImpl(true));
    myCodeInsightFixture.setUp();
    myCodeInsightFixture.setTestDataPath(folderAbsolutePath);
  }

  public void tearDown() throws Exception {
    myCodeInsightFixture.tearDown();
  }

  @NotNull
  private AssertableList getCompletions(@NotNull String caretFileName) {
    List<String> results = myCodeInsightFixture.getCompletionVariants(caretFileName);
    Assert.assertThat(results, IsNull.notNullValue());
    return new AssertableList(results);
  }

  @NotNull
  public AssertableList getCompletions(@NotNull String caretFileNoExt, @NotNull Format fileKind) {
    String fullName = fileKind.getFileNameWithExtension(caretFileNoExt);
    return getCompletions(fullName);
  }

  public void complete(@NotNull final String testFileNoExt, @NotNull final Format fileKind) {
    myCodeInsightFixture.configureByFile(fileKind.getFileNameWithExtension(testFileNoExt));
    myCodeInsightFixture.complete(CompletionType.BASIC, 2);
    if (LookupManager.getActiveLookup(myCodeInsightFixture.getEditor()) != null) {
      myCodeInsightFixture.type('\n');
    }
  }

  public void checkResultByFile(
      @NotNull final String testFileNoExt, @NotNull final Format fileKind) {
    myCodeInsightFixture.checkResultByFile(fileKind.getFileNameWithExtension(testFileNoExt), true);
  }

  @NotNull
  private static String toAbsolutePath(@NotNull String resourcesRelatedPath)
      throws URISyntaxException {
    URL url = ClassLoader.getSystemClassLoader().getResource(resourcesRelatedPath);
    assert url != null;
    return new File(url.toURI()).getAbsolutePath();
  }
}
