package org.zalando.intellij.swagger.file;

import com.intellij.openapi.util.io.FileUtil;
import com.intellij.util.LocalFileUrl;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.jetbrains.annotations.NotNull;

public class SwaggerUiCreator {

  private static final String SWAGGER_UI_FOLDER_NAME = "swagger-ui";

  private final FileContentManipulator fileContentManipulator;

  public SwaggerUiCreator(final FileContentManipulator fileContentManipulator) {
    this.fileContentManipulator = fileContentManipulator;
  }

  public Path createSwaggerUiFiles(final String specificationContent) throws Exception {
    final File tempSwaggerUiDir = copySwaggerUiToTempDir();

    setSwaggerConfigurationValues(new File(tempSwaggerUiDir, "index.html"), specificationContent);

    return Paths.get(tempSwaggerUiDir.getAbsolutePath());
  }

  public void updateSwaggerUiFile(
      final LocalFileUrl indexFileUrl, final String specificationContent) {
    final File indexFile = new File(Paths.get(indexFileUrl.getPath()).toUri());

    setSwaggerConfigurationValues(indexFile, specificationContent);
  }

  @NotNull
  private File copySwaggerUiToTempDir() throws IOException, URISyntaxException {
    final ClassLoader classLoader = getClass().getClassLoader();
    final File file = new File(classLoader.getResource(SWAGGER_UI_FOLDER_NAME).toURI());
    final File tempSwaggerUiDir = FileUtil.createTempDirectory(SWAGGER_UI_FOLDER_NAME, "", true);

    FileUtil.copyDirContent(file, tempSwaggerUiDir);

    return tempSwaggerUiDir;
  }

  private void setSwaggerConfigurationValues(
      final File indexFile, final String specificationContent) {
    fileContentManipulator.setJsonToIndexFile(specificationContent, indexFile);
  }
}
