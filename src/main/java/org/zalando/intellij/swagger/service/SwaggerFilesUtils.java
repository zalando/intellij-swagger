package org.zalando.intellij.swagger.service;

import com.intellij.util.Url;
import com.intellij.util.Urls;
import java.io.File;
import java.nio.file.Path;
import org.jetbrains.annotations.NotNull;

public class SwaggerFilesUtils {

  public static Url convertSwaggerLocationToUrl(@NotNull final Path swaggerHtmlDirectory) {
    return Urls.newUri("file", swaggerHtmlDirectory + File.separator + "index.html");
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
