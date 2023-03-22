package org.zalando.intellij.swagger.file;

import com.intellij.openapi.util.io.FileUtil;
import com.intellij.util.LocalFileUrl;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Collections;
import java.util.Objects;
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
    final File tempSwaggerUiDir = FileUtil.createTempDirectory(SWAGGER_UI_FOLDER_NAME, "", true);

    copyFromJar(Paths.get(tempSwaggerUiDir.toURI()));

    return tempSwaggerUiDir;
  }

  private void setSwaggerConfigurationValues(
      final File indexFile, final String specificationContent) {
    fileContentManipulator.setJsonToIndexFile(specificationContent, indexFile);
  }

  private void copyFromJar(final Path target) throws URISyntaxException, IOException {
    String resourcePath = "/" + SWAGGER_UI_FOLDER_NAME;
    URI resource = Objects.requireNonNull(getClass().getResource(resourcePath), "resource").toURI();
    FileSystem fileSystem =
        FileSystems.newFileSystem(resource, Collections.<String, String>emptyMap());

    final Path jarPath = fileSystem.getPath(resourcePath);

    Files.walkFileTree(
        jarPath,
        new SimpleFileVisitor<>() {

          @Override
          public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs)
              throws IOException {
            final Path currentTarget = target.resolve(jarPath.relativize(dir).toString());
            Files.createDirectories(currentTarget);
            return FileVisitResult.CONTINUE;
          }

          @Override
          public FileVisitResult visitFile(Path file, BasicFileAttributes attrs)
              throws IOException {
            Files.copy(
                file,
                target.resolve(jarPath.relativize(file).toString()),
                StandardCopyOption.REPLACE_EXISTING);
            return FileVisitResult.CONTINUE;
          }
        });

    fileSystem.close();
  }
}
