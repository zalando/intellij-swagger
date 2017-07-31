package org.zalando.intellij.swagger.file;

import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.util.io.FileUtil;
import com.intellij.util.LocalFileUrl;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.Optional;

public class SwaggerUiCreator {

    private static final Logger LOG = Logger.getInstance("#org.zalando.intellij.swagger.file.SwaggerUiCreator");

    private static final String SWAGGER_UI_FOLDER_NAME = "swagger-ui";

    private final FileContentManipulator fileContentManipulator;

    public SwaggerUiCreator(final FileContentManipulator fileContentManipulator) {
        this.fileContentManipulator = fileContentManipulator;
    }

    public Optional<String> createSwaggerUiFiles(final String specificationContent) {
        try {
            return tryToCreateSwaggerUiFiles(specificationContent);
        } catch (final Exception e) {
            LOG.error(e);
            return Optional.empty();
        }
    }

    public void updateSwaggerUiFile(final LocalFileUrl indexFileUrl, final String specificationContent) {
        try {
            tryToUpdateSwaggerUiFiles(indexFileUrl, specificationContent);
        } catch (final Exception e) {
            LOG.error(e);
        }
    }

    private void tryToUpdateSwaggerUiFiles(final LocalFileUrl indexFileUrl, final String specificationContent) {
        final File indexFile = new File(Paths.get(indexFileUrl.getPath()).toUri());

        setSwaggerConfigurationValues(indexFile, specificationContent);
    }

    private Optional<String> tryToCreateSwaggerUiFiles(final String specificationContent)
            throws IOException, URISyntaxException {
        final File tempSwaggerUiDir = copySwaggerUiToTempDir();

        setSwaggerConfigurationValues(new File(tempSwaggerUiDir, "index.html"), specificationContent);

        return Optional.of(tempSwaggerUiDir.getAbsolutePath());
    }

    @NotNull
    private File copySwaggerUiToTempDir() throws IOException, URISyntaxException {
        final ClassLoader classLoader = getClass().getClassLoader();
        final File file = new File(classLoader.getResource(SWAGGER_UI_FOLDER_NAME).toURI());
        final File tempSwaggerUiDir = FileUtil.createTempDirectory(SWAGGER_UI_FOLDER_NAME, "", true);

        FileUtil.copyDirContent(file, tempSwaggerUiDir);

        return tempSwaggerUiDir;
    }

    private void setSwaggerConfigurationValues(final File indexFile, final String specificationContent) {
        fileContentManipulator.setJsonToIndexFile(specificationContent, indexFile);
    }

}
