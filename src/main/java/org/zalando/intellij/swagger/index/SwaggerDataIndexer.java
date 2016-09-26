package org.zalando.intellij.swagger.index;

import com.google.common.collect.ImmutableSet;
import com.intellij.json.psi.JsonFile;
import com.intellij.json.psi.JsonProperty;
import com.intellij.json.psi.impl.JsonRecursiveElementVisitor;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiRecursiveElementVisitor;
import com.intellij.util.containers.HashSet;
import com.intellij.util.indexing.DataIndexer;
import com.intellij.util.indexing.FileContent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.yaml.psi.YAMLKeyValue;
import org.zalando.intellij.swagger.completion.StringUtils;
import org.zalando.intellij.swagger.file.FileDetector;
import org.zalando.intellij.swagger.file.SwaggerFileType;
import org.zalando.intellij.swagger.traversal.MainPathResolver;
import org.zalando.intellij.swagger.traversal.PathResolver;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

class SwaggerDataIndexer implements DataIndexer<String, Set<String>, FileContent> {

    private final FileDetector fileDetector = new FileDetector();
    private final PathResolver pathResolver = new MainPathResolver();

    @NotNull
    @Override
    public Map<String, Set<String>> map(@NotNull FileContent inputData) {
        final Map<String, Set<String>> indexMap = new HashMap<>();
        final PsiFile file = inputData.getPsiFile();

        if (fileDetector.isMainSwaggerJsonFile(file) || fileDetector.isMainSwaggerYamlFile(file)) {
            Set<String> partialSwaggerFileNames;

            if (file instanceof JsonFile) {
                partialSwaggerFileNames = getPartialJsonSwaggerFileNames(file);
            } else {
                partialSwaggerFileNames = getPartialYamlSwaggerFileNames(file);
            }

            indexMap.put(SwaggerFileIndex.PARTIAL_SWAGGER_FILES, partialSwaggerFileNames);
            indexMap.put(SwaggerFileIndex.MAIN_SWAGGER_FILE, ImmutableSet.of(file.getName() + "-" + SwaggerFileType.MAIN));
        }
        return indexMap;
    }

    private Set<String> getPartialJsonSwaggerFileNames(final PsiFile file) {
        final Set<String> result = new HashSet<>();

        file.accept(new JsonRecursiveElementVisitor() {
            @Override
            public void visitProperty(@NotNull JsonProperty property) {
                if ("$ref".equals(property.getName())) {
                    if (property.getValue() != null) {
                        final String refValue = StringUtils.removeAllQuotes(property.getValue().getText());
                        if (refValue.endsWith(".json")) {
                            result.add(refValue + "-" + getSwaggerFileType(property.getValue()));
                        }
                    }
                }
                super.visitProperty(property);
            }
        });

        return result;
    }

    private Set<String> getPartialYamlSwaggerFileNames(final PsiFile file) {
        final Set<String> result = new HashSet<>();

        file.accept(new PsiRecursiveElementVisitor() {
                        @Override
                        public void visitElement(final PsiElement element) {
                            if (element instanceof YAMLKeyValue) {
                                final YAMLKeyValue yamlKeyValue = (YAMLKeyValue) element;
                                if ("$ref".equals(yamlKeyValue.getKeyText())) {
                                    final String refValue = StringUtils.removeAllQuotes(yamlKeyValue.getValueText());
                                    if (refValue.endsWith(".yaml") || refValue.endsWith(".yml")) {
                                        result.add(refValue + "-" + getSwaggerFileType(yamlKeyValue.getValue()));
                                    }
                                }
                            }
                            super.visitElement(element);
                        }
                    }
        );

        return result;
    }

    @NotNull
    private SwaggerFileType getSwaggerFileType(final PsiElement psiElement) {
        if (pathResolver.isDefinitionRefValue(psiElement)) {
            return SwaggerFileType.DEFINITIONS;
        } else if (pathResolver.isParameterRefValue(psiElement)) {
            return SwaggerFileType.PARAMETERS;
        }
        return SwaggerFileType.UNDEFINED;
    }
}
