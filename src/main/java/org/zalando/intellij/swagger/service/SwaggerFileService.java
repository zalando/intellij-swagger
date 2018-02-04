package org.zalando.intellij.swagger.service;

import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.dataformat.yaml.*;
import com.intellij.openapi.application.*;
import com.intellij.openapi.vfs.*;
import com.intellij.psi.*;
import com.intellij.util.*;
import org.jetbrains.annotations.*;
import org.zalando.intellij.swagger.file.*;
import org.zalando.intellij.swagger.ui.actions.*;

import java.nio.file.*;
import java.util.*;

public class SwaggerFileService {

    final private Map<PsiFile, Path> convertedSwaggerDocuments = new HashMap<>();
    final private SwaggerUiCreator swaggerUiCreator = new SwaggerUiCreator(new FileContentManipulator());
    final private FileDetector fileDetector = new FileDetector();

    public Optional<Path> convertSwaggerToHtml(@NotNull final PsiFile swaggerFile) {
        Optional<Path> swaggerUiDirectory = createOrUpdateSwaggerUiFiles(swaggerFile);

        swaggerUiDirectory.ifPresent((path -> publishChanges(swaggerFile.getVirtualFile(), path)));

        return swaggerUiDirectory;
    }

    public boolean swaggerContentExistsFor(final PsiFile file) {
        return convertedSwaggerDocuments.containsKey(file);
    }

    private Optional<Path> createOrUpdateSwaggerUiFiles(@NotNull final PsiFile swaggerFile) {
        final String specificationContentAsJson = getSpecificationContentAsJson(swaggerFile);
        Path htmlSwaggerContentsDirectory = convertedSwaggerDocuments.get(swaggerFile);

        if (htmlSwaggerContentsDirectory != null) {
            LocalFileUrl indexPath = new LocalFileUrl(Paths.get(htmlSwaggerContentsDirectory.toString(), "index.html").toString());
            swaggerUiCreator.updateSwaggerUiFile(indexPath, specificationContentAsJson);
        } else {
            htmlSwaggerContentsDirectory = swaggerUiCreator.createSwaggerUiFiles(specificationContentAsJson)
                    .map(Paths::get)
                    .orElse(null);
            if (htmlSwaggerContentsDirectory != null) {
                convertedSwaggerDocuments.put(swaggerFile, htmlSwaggerContentsDirectory);
            }
        }

        return Optional.ofNullable(htmlSwaggerContentsDirectory);
    }

    private void publishChanges(final VirtualFile swaggerFile, final Path swaggerUiDirectory) {
        ApplicationManager
                .getApplication()
                .getMessageBus()
                .syncPublisher(SwaggerUIFilesChangeNotifier.SWAGGER_UI_FILES_CHANGED)
                .swaggerHTMLFilesChanged(swaggerFile, SwaggerFilesUtils.convertSwaggerLocationToUrl(swaggerUiDirectory));
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
