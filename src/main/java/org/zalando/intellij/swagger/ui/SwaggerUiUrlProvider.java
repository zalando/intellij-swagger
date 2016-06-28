package org.zalando.intellij.swagger.ui;

import com.intellij.ide.browsers.OpenInBrowserRequest;
import com.intellij.ide.browsers.WebBrowserUrlProvider;
import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.util.Url;
import com.intellij.util.Urls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.builtInWebServer.BuiltInServerOptions;
import org.zalando.intellij.swagger.file.FileContentManipulator;
import org.zalando.intellij.swagger.file.FileDetector;
import org.zalando.intellij.swagger.file.SwaggerUiCreator;

import java.util.Optional;

public class SwaggerUiUrlProvider extends WebBrowserUrlProvider implements DumbAware {

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
        final Url specificationUrl = getUrl(file, request.getProject());

        return swaggerUiCreator.createSwaggerUiFiles(specificationUrl)
                .map(swaggerUiFolderPath ->
                        Urls.parseEncoded("file://" + swaggerUiFolderPath + "/index.html"))
                .orElse(null);
    }

    @NotNull
    private Url getUrl(@NotNull VirtualFile file, @NotNull Project project) {
        final int port = BuiltInServerOptions.getInstance().getEffectiveBuiltInServerPort();
        final String path = getSpecificationPath(file, project);
        final String authority = "localhost:" + port;

        return Urls.newHttpUrl(authority, '/' + project.getName() + path);
    }

    @NotNull
    private String getSpecificationPath(final @NotNull VirtualFile file, @NotNull Project project) {
        return Optional.ofNullable(project.getBasePath())
                .map(projectBasePath -> file.getPath().replace(projectBasePath, ""))
                .orElse("");
    }

}
