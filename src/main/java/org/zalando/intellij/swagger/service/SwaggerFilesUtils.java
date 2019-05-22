package org.zalando.intellij.swagger.service;

import com.intellij.util.LocalFileUrl;
import com.intellij.util.Url;
import java.io.File;
import java.nio.file.Path;
import org.jetbrains.annotations.NotNull;

public class SwaggerFilesUtils {

  public static Url convertSwaggerLocationToUrl(@NotNull final Path swaggerHtmlDirectory) {
    return new LocalFileUrl(swaggerHtmlDirectory.toString() + File.separator + "index.html");
  }

  public static boolean isFileReference(final String text) {
    return text.endsWith(".json")
        || text.contains(".json#/")
        || text.endsWith(".yaml")
        || text.contains(".yaml#/")
        || text.endsWith(".yml")
        || text.contains(".yml#/");
  }
}
