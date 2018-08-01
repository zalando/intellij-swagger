package org.zalando.intellij.swagger.ui.provider;

import com.intellij.AppTopics;
import com.intellij.ide.browsers.OpenInBrowserRequest;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.fileEditor.FileDocumentManagerAdapter;
import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiDocumentManager;
import com.intellij.psi.PsiFile;
import com.intellij.util.Url;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.builtInWebServer.BuiltInWebBrowserUrlProvider;
import org.zalando.intellij.swagger.file.FileDetector;
import org.zalando.intellij.swagger.service.SwaggerFileService;
import org.zalando.intellij.swagger.service.SwaggerFilesUtils;

import java.nio.file.Path;
import java.util.Optional;

public class SwaggerUiUrlProvider extends BuiltInWebBrowserUrlProvider implements DumbAware {

    private final FileDetector fileDetector;

    public SwaggerUiUrlProvider() {
        this(new FileDetector());
    }

    private SwaggerUiUrlProvider(final FileDetector fileDetector) {
        this.fileDetector = fileDetector;

        ApplicationManager.getApplication().getMessageBus().connect().subscribe(AppTopics.FILE_DOCUMENT_SYNC, new FileDocumentManagerAdapter() {
            @Override
            public void beforeDocumentSaving(@NotNull final Document document) {
                SwaggerFileService swaggerFileService = ServiceManager.getService(SwaggerFileService.class);
                final Project[] openProjects = ProjectManager.getInstance().getOpenProjects();

                if (openProjects.length > 0) {
                    final PsiFile psiFile = PsiDocumentManager.getInstance(openProjects[0]).getPsiFile(document);

                    if (psiFile != null) {
                        final boolean swaggerFile = fileDetector.isMainSwaggerFile(psiFile) || fileDetector.isMainOpenApiFile(psiFile);

                        if (swaggerFile) {
                            swaggerFileService.convertSwaggerToHtmlAsync(psiFile);
                        }
                    }
                }
            }
            });
    }

    @Override
    public boolean canHandleElement(@NotNull OpenInBrowserRequest request) {
        final PsiFile file = request.getFile();
        return fileDetector.isMainSwaggerFile(file) || fileDetector.isMainOpenApiFile(file);
    }

    @Nullable
    @Override
    protected Url getUrl(@NotNull OpenInBrowserRequest request, @NotNull VirtualFile file) throws BrowserException {
        SwaggerFileService swaggerFileService = ServiceManager.getService(SwaggerFileService.class);
        Optional<Path> swaggerHTMLFolder = swaggerFileService.convertSwaggerToHtml(request.getFile());

        return swaggerHTMLFolder
                .map(SwaggerFilesUtils::convertSwaggerLocationToUrl)
                .orElse(null);
    }
}
