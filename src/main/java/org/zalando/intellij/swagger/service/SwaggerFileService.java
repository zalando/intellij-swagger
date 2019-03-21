package org.zalando.intellij.swagger.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.intellij.notification.Notification;
import com.intellij.notification.NotificationType;
import com.intellij.notification.Notifications;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.fileEditor.impl.LoadTextUtil;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.util.LocalFileUrl;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.jetbrains.annotations.NotNull;
import org.zalando.intellij.swagger.file.FileContentManipulator;
import org.zalando.intellij.swagger.file.SwaggerUiCreator;

public class SwaggerFileService {

  private final ConcurrentHashMap<String, Path> convertedSwaggerDocuments =
      new ConcurrentHashMap<>();
  private final SwaggerUiCreator swaggerUiCreator =
      new SwaggerUiCreator(new FileContentManipulator());
  private final ExecutorService executorService = Executors.newSingleThreadExecutor();
  private final JsonBuilderService jsonBuilderService = new JsonBuilderService();
  private final ObjectMapper mapper = new ObjectMapper(new YAMLFactory());

  public Optional<Path> convertSwaggerToHtml(@NotNull final VirtualFile virtualFile) {
    try {
      return convertSwaggerToHtmlWithoutCache(virtualFile, convertToJsonIfNecessary(virtualFile));
    } catch (Exception e) {
      notifyFailure(e);
      return Optional.empty();
    }
  }

  public void convertSwaggerToHtmlAsync(@NotNull final VirtualFile virtualFile) {
    executorService.submit(
        () ->
            ApplicationManager.getApplication()
                .runReadAction(
                    () -> {
                      try {
                        convertSwaggerToHtmlWithCache(
                            virtualFile, convertToJsonIfNecessary(virtualFile));
                      } catch (Exception e) {
                        // This is a no-op; we don't want to notify the user if the
                        // Swagger UI generation fails on file save. A user may
                        // edit a spec file that is invalid on multiple saves, and
                        // this would result in a large number of notifications.
                      }
                    }));
  }

  private void notifyFailure(final Exception exception) {
    Notification notification =
        new Notification(
            "Swagger UI",
            "Could not generate Swagger UI",
            exception.getMessage(),
            NotificationType.WARNING);

    Notifications.Bus.notify(notification);
  }

  private Optional<Path> convertSwaggerToHtmlWithoutCache(
      final VirtualFile virtualFile, final String contentAsJson) throws Exception {
    final Path path = swaggerUiCreator.createSwaggerUiFiles(contentAsJson);

    convertedSwaggerDocuments.put(virtualFile.getPath(), path);

    return Optional.of(path);
  }

  private void convertSwaggerToHtmlWithCache(final VirtualFile file, final String contentAsJson) {
    Optional.ofNullable(convertedSwaggerDocuments.get(file.getPath()))
        .ifPresent(
            dir -> {
              LocalFileUrl indexPath =
                  new LocalFileUrl(Paths.get(dir.toString(), "index.html").toString());
              swaggerUiCreator.updateSwaggerUiFile(indexPath, contentAsJson);
            });
  }

  private String convertToJsonIfNecessary(@NotNull final VirtualFile virtualFile) throws Exception {
    final JsonNode root = mapper.readTree(LoadTextUtil.loadText(virtualFile).toString());
    final JsonNode result = jsonBuilderService.buildWithResolvedReferences(root, virtualFile);

    return new ObjectMapper().writeValueAsString(result);
  }
}
