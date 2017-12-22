package org.zalando.intellij.swagger.service;

import com.intellij.util.LocalFileUrl;
import com.intellij.util.Url;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.nio.file.Path;

public class SwaggerFilesUtils {

    public static Url convertSwaggerLocationToUrl(@NotNull Path swaggerHtmlDirectory) {
        return new LocalFileUrl(swaggerHtmlDirectory.toString() + File.separator + "index.html");
    }
}
