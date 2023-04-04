package org.zalando.intellij.swagger.file;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import org.apache.commons.io.FileUtils;
import org.jetbrains.annotations.NotNull;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

public class FileContentManipulatorTest {
  private FileContentManipulator fileContentManipulator;

  @Rule public final TemporaryFolder tempFolder = new TemporaryFolder();

  @Before
  public void setUp() {
    fileContentManipulator = new FileContentManipulator();
  }

  @Test
  public void itCanSetPlaceholderValueInANewFile() throws Exception {
    File targetFile = copyResourceTo("with_placeholder.html", "index.html");
    File expectedOutputFile = copyResourceTo("with_placeholder_replaced.html", "expected.html");

    fileContentManipulator.setJsonToIndexFile("{}", targetFile);

    assertTrue("The files differ!", FileUtils.contentEquals(targetFile, expectedOutputFile));
  }

  @NotNull
  private File copyResourceTo(String resourceName, String fileName) throws IOException {
    URL inputUrl = getClass().getResource("/testing/file/" + resourceName);
    File targetFile = tempFolder.newFile(fileName);
    FileUtils.copyURLToFile(inputUrl, targetFile);
    return targetFile;
  }
}
