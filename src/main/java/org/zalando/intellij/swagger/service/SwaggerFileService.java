package org.zalando.intellij.swagger.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.intellij.psi.PsiFile;
import com.intellij.util.LocalFileUrl;
import org.jetbrains.annotations.NotNull;
import org.zalando.intellij.swagger.file.FileContentManipulator;
import org.zalando.intellij.swagger.file.FileDetector;
import org.zalando.intellij.swagger.file.SwaggerUiCreator;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SwaggerFileService {

    final private ConcurrentHashMap<String, Path> convertedSwaggerDocuments = new ConcurrentHashMap<>();
    final private SwaggerUiCreator swaggerUiCreator = new SwaggerUiCreator(new FileContentManipulator());
    final private FileDetector fileDetector = new FileDetector();
    final private ExecutorService executorService = Executors.newSingleThreadExecutor();

    public Optional<Path> convertSwaggerToHtml(@NotNull final PsiFile file) {
        if (fileDetector.isMainSwaggerJsonFile(file) || fileDetector.isMainOpenApiJsonFile(file)) {
            return convertSwaggerToHtml(file, file.getText());
        } else {
            return convertSwaggerToHtml(file, yamlToJson(file.getText()));
        }
    }

    public void convertSwaggerToHtmlAsync(@NotNull final PsiFile file) {
        if (fileDetector.isMainSwaggerJsonFile(file) || fileDetector.isMainOpenApiJsonFile(file)) {
            executorService.submit(() -> {
                convertSwaggerToHtml(file, file.getText());
            });
        } else {
            executorService.submit(() -> {
                final String contentAsJson = yamlToJson(file.getText());
                convertSwaggerToHtml(file, contentAsJson);
            });
        }
    }

    private Optional<Path> convertSwaggerToHtml(final PsiFile file, final String contentAsJson) {
        Path htmlSwaggerContentsDirectory = convertedSwaggerDocuments.get(file.getVirtualFile().getPath());

        if (htmlSwaggerContentsDirectory != null) {
            LocalFileUrl indexPath = new LocalFileUrl(Paths.get(htmlSwaggerContentsDirectory.toString(), "index.html").toString());
            swaggerUiCreator.updateSwaggerUiFile(indexPath, contentAsJson);
        } else {
            htmlSwaggerContentsDirectory = swaggerUiCreator.createSwaggerUiFiles(contentAsJson)
                    .map(Paths::get)
                    .orElse(null);
            if (htmlSwaggerContentsDirectory != null) {
                convertedSwaggerDocuments.putIfAbsent(file.getVirtualFile().getPath(), htmlSwaggerContentsDirectory);
            }
        }

        return Optional.ofNullable(htmlSwaggerContentsDirectory);
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
