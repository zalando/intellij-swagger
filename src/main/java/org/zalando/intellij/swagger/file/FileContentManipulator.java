package org.zalando.intellij.swagger.file;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

public class FileContentManipulator {

    void setPlaceholderValue(final String placeholderName, final String value, final File file) {
        try {
            String content = new String(Files.readAllBytes(file.toPath()), StandardCharsets.UTF_8);
            content = content.replace(placeholderName, value);
            Files.write(file.toPath(), content.getBytes(StandardCharsets.UTF_8));
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
    }
}
