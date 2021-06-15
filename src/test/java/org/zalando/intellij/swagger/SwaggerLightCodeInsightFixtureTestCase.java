package org.zalando.intellij.swagger;

import com.google.common.base.Preconditions;
import com.intellij.testFramework.fixtures.LightJavaCodeInsightFixtureTestCase;
import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import org.jetbrains.annotations.NotNull;

public abstract class SwaggerLightCodeInsightFixtureTestCase
    extends LightJavaCodeInsightFixtureTestCase {

  @NotNull
  @Override
  protected String getTestDataPath() {
    try {
      return tryToGetAbsolutePath();
    } catch (URISyntaxException e) {
      throw new RuntimeException(e);
    }
  }

  @NotNull
  private String tryToGetAbsolutePath() throws URISyntaxException {
    final URL url = ClassLoader.getSystemClassLoader().getResource("testing/");
    Preconditions.checkNotNull(url);
    return new File(url.toURI()).getAbsolutePath();
  }
}
