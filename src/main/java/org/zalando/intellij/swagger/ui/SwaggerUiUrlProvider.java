package org.zalando.intellij.swagger.ui;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.intellij.ide.browsers.OpenInBrowserRequest;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.util.LocalFileUrl;
import com.intellij.util.Url;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.builtInWebServer.BuiltInWebBrowserUrlProvider;
import org.zalando.intellij.swagger.file.FileContentManipulator;
import org.zalando.intellij.swagger.file.FileDetector;
import org.zalando.intellij.swagger.file.SwaggerUiCreator;

import java.io.File;
import java.io.IOException;

public class SwaggerUiUrlProvider extends BuiltInWebBrowserUrlProvider implements DumbAware {

    private static final Logger LOG = Logger.getInstance("#org.zalando.intellij.swagger.ui.SwaggerUiUrlProvider");

    private final FileDetector fileDetector;
    private final SwaggerUiCreator swaggerUiCreator;

    public SwaggerUiUrlProvider() {
        this(new FileDetector(), new SwaggerUiCreator(new FileContentManipulator()));
    }

    private SwaggerUiUrlProvider(final FileDetector fileDetector,
                                 final SwaggerUiCreator swaggerUiCreator) {
        this.fileDetector = fileDetector;
        this.swaggerUiCreator = swaggerUiCreator;
    }

    @Override
    public boolean canHandleElement(@NotNull OpenInBrowserRequest request) {
        return fileDetector.isSwaggerFile(request.getFile());
    }

    @Nullable
    @Override
    protected Url getUrl(@NotNull OpenInBrowserRequest request, @NotNull VirtualFile file) throws BrowserException {
        return swaggerUiCreator.createSwaggerUiFiles(getSpecificationContentAsJson(request))
                .map(swaggerUiFolderPath ->
                        new LocalFileUrl(swaggerUiFolderPath + File.separator + "index.html"))
                .orElse(null);
    }

    private String getSpecificationContentAsJson(final @NotNull OpenInBrowserRequest request) {
        final String content = request.getFile().getText();

        return fileDetector.isMainSwaggerJsonFile(request.getFile()) ? content : yamlToJson(content);
    }

    private String yamlToJson(final String content) {
        try {
            final ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
            final JsonNode jsonNode = mapper.readTree(content);
            return new ObjectMapper().writeValueAsString(jsonNode);
        } catch (final Exception e) {
            return "{}";
        }
    }

}
