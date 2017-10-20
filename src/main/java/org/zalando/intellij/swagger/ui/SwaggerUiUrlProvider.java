package org.zalando.intellij.swagger.ui;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.intellij.AppTopics;
import com.intellij.ide.browsers.OpenInBrowserRequest;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.fileEditor.FileDocumentManagerAdapter;
import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiDocumentManager;
import com.intellij.psi.PsiFile;
import com.intellij.util.LocalFileUrl;
import com.intellij.util.Url;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.builtInWebServer.BuiltInWebBrowserUrlProvider;
import org.zalando.intellij.swagger.file.FileContentManipulator;
import org.zalando.intellij.swagger.file.FileDetector;
import org.zalando.intellij.swagger.file.SwaggerUiCreator;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

public class SwaggerUiUrlProvider extends BuiltInWebBrowserUrlProvider implements DumbAware {

    private final FileDetector fileDetector;
    private final SwaggerUiCreator swaggerUiCreator;
    private LocalFileUrl swaggerUiIndexFile;

    public SwaggerUiUrlProvider() {
        this(new FileDetector(), new SwaggerUiCreator(new FileContentManipulator()));
    }

    private SwaggerUiUrlProvider(final FileDetector fileDetector,
                                 final SwaggerUiCreator swaggerUiCreator) {
        this.fileDetector = fileDetector;
        this.swaggerUiCreator = swaggerUiCreator;
        ApplicationManager.getApplication().getMessageBus().connect().subscribe(AppTopics.FILE_DOCUMENT_SYNC, new FileDocumentManagerAdapter() {
            @Override
            public void beforeDocumentSaving(@NotNull final Document document) {
                if (indexFileExists()) {
                    final Project[] openProjects = ProjectManager.getInstance().getOpenProjects();

                    if (openProjects.length > 0) {
                        final PsiFile psiFile = PsiDocumentManager.getInstance(openProjects[0]).getPsiFile(document);

                        if (psiFile != null) {
                            final boolean swaggerFile = fileDetector.isMainSwaggerFile(psiFile) || fileDetector.isMainOpenApiFile(psiFile);

                            if (swaggerFile) {
                                final String specificationContentAsJson = getSpecificationContentAsJson(psiFile);
                                swaggerUiCreator.updateSwaggerUiFile(swaggerUiIndexFile, specificationContentAsJson);
                            }
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
        final String specificationContentAsJson = getSpecificationContentAsJson(request.getFile());

        if (indexFileExists()) {
            swaggerUiCreator.updateSwaggerUiFile(swaggerUiIndexFile, specificationContentAsJson);
        } else {
            swaggerUiIndexFile = swaggerUiCreator.createSwaggerUiFiles(specificationContentAsJson)
                    .map(swaggerUiFolderPath ->
                            new LocalFileUrl(swaggerUiFolderPath + File.separator + "index.html"))
                    .orElse(null);
        }

        return swaggerUiIndexFile;
    }

    private boolean indexFileExists() {
        return swaggerUiIndexFile != null && Files.exists(Paths.get(swaggerUiIndexFile.getPath()));
    }

    private String getSpecificationContentAsJson(final @NotNull PsiFile psiFile) {
        final String content = psiFile.getText();

        return fileDetector.isMainSwaggerJsonFile(psiFile) ? content : yamlToJson(content);
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
