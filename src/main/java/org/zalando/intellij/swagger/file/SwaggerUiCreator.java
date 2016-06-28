package org.zalando.intellij.swagger.file;

import com.intellij.openapi.util.io.FileUtil;
import com.intellij.util.Url;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

public class SwaggerUiCreator {

    private static final String SWAGGER_UI_FOLDER_NAME = "swagger-ui";
    private static final String SPECIFICATION_URL_PLACEHOLDER = "${specificationUrl}";

    private final FileContentManipulator fileContentManipulator;

    public SwaggerUiCreator(final FileContentManipulator fileContentManipulator) {
        this.fileContentManipulator = fileContentManipulator;
    }

    public Optional<String> createSwaggerUiFiles(final Url specificationUrl) {
        try {
            return tryToCreateSwaggerUiFiles(specificationUrl);
        } catch (IOException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    private Optional<String> tryToCreateSwaggerUiFiles(final Url specificationUrl) throws IOException {
        final File tempSwaggerUiDir = copySwaggerUiToTempDir();

        setSwaggerConfigurationValues(tempSwaggerUiDir, specificationUrl);

        return Optional.of(tempSwaggerUiDir.getAbsolutePath());
    }

    @NotNull
    private File copySwaggerUiToTempDir() throws IOException {
        final ClassLoader classLoader = getClass().getClassLoader();
        final File file = new File(classLoader.getResource(SWAGGER_UI_FOLDER_NAME).getFile());
        final File tempSwaggerUiDir = FileUtil.createTempDirectory(SWAGGER_UI_FOLDER_NAME, "", true);

        FileUtil.copyDirContent(file, tempSwaggerUiDir);

        return tempSwaggerUiDir;
    }

    private void setSwaggerConfigurationValues(final File swaggerDir, final Url specificationUrl) {
        fileContentManipulator.setPlaceholderValue(SPECIFICATION_URL_PLACEHOLDER, specificationUrl.toString(),
                new File(swaggerDir, "index.html"));
    }

}
