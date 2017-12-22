package org.zalando.intellij.swagger.service;

import com.intellij.util.Url;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class SwaggerFilesUtilsTest {

    @Test
    public void test() throws IOException {
        Path path = Files.createTempDirectory("");
        Url url = SwaggerFilesUtils.convertSwaggerLocationToUrl(path);

        Assert.assertEquals(url.getPath(), path.toString() + File.separator + "index.html");
    }
}
