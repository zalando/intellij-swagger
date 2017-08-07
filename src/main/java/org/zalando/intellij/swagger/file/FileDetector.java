package org.zalando.intellij.swagger.file;

import com.google.common.collect.Lists;
import com.intellij.json.psi.JsonFile;
import com.intellij.json.psi.JsonProperty;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import org.apache.commons.io.FilenameUtils;
import org.jetbrains.yaml.psi.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class FileDetector {

    private static final String SWAGGER_KEY = "swagger";
    private static final String OPEN_API_KEY = "openapi";

    public boolean isMainSwaggerJsonFile(final PsiFile psiFile) {
        return hasJsonRootKey(psiFile, SWAGGER_KEY);
    }

    public boolean isMainSwaggerYamlFile(final PsiFile psiFile) {
        return hasYamlRootKey(psiFile, SWAGGER_KEY);
    }

    public boolean isMainOpenApiJsonFile(final PsiFile psiFile) {
        return hasJsonRootKey(psiFile, OPEN_API_KEY);
    }

    public boolean isMainOpenApiYamlFile(final PsiFile psiFile) {
        return hasYamlRootKey(psiFile, OPEN_API_KEY);
    }

    private boolean hasYamlRootKey(final PsiFile psiFile, final String lookupKey) {
        final List<YAMLPsiElement> children = Optional.of(psiFile)
                .filter(f -> f instanceof YAMLFile)
                .map(YAMLFile.class::cast)
                .map(YAMLFile::getDocuments)
                .map(yamlDocuments -> yamlDocuments.get(0))
                .map(YAMLDocument::getTopLevelValue)
                .map(YAMLValue::getYAMLElements)
                .orElse(Lists.newArrayList());

        return children.stream()
                .anyMatch(psiElement -> psiElement instanceof YAMLKeyValue
                        && lookupKey.equals(psiElement.getName()));
    }

    private boolean hasJsonRootKey(final PsiFile psiFile, final String lookupKey) {
        final List<PsiElement> children = Optional.of(psiFile)
                .filter(f -> f instanceof JsonFile)
                .map(JsonFile.class::cast)
                .map(JsonFile::getTopLevelValue)
                .map(jsonValue -> Arrays.asList(jsonValue.getChildren()))
                .orElse(Lists.newArrayList());

        return children.stream()
                .anyMatch(psiElement -> psiElement instanceof JsonProperty
                        && lookupKey.equals(((JsonProperty) psiElement).getName()));
    }

    public boolean isMainSwaggerFile(final PsiFile file) {
        return isMainSwaggerJsonFile(file) || isMainSwaggerYamlFile(file);
    }

    public boolean isMainOpenApiFile(final PsiFile file) {
        return isMainOpenApiJsonFile(file) || isMainOpenApiYamlFile(file);
    }

    public boolean isSwaggerContentCompatible(VirtualFile file) {
        return FilenameUtils.isExtension(file.getName(),
                new String[]{FileConstants.JSON_FILE_EXTENSION,
                        FileConstants.YAML_FILE_EXTENSION,
                        FileConstants.YML_FILE_EXTENSION});
    }
}
