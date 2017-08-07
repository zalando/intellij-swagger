package org.zalando.intellij.swagger.index.swagger;

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
import org.zalando.intellij.swagger.StringUtils;
import org.zalando.intellij.swagger.file.FileConstants;
import org.zalando.intellij.swagger.file.FileDetector;
import org.zalando.intellij.swagger.file.SwaggerFileType;
import org.zalando.intellij.swagger.reference.SwaggerConstants;
import org.zalando.intellij.swagger.traversal.path.swagger.MainPathResolver;
import org.zalando.intellij.swagger.traversal.path.swagger.PathResolver;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

class SwaggerDataIndexer implements DataIndexer<String, Set<String>, FileContent> {

    static final String DELIMITER = "-";

    private final FileDetector fileDetector = new FileDetector();
    private final PathResolver pathResolver = new MainPathResolver();

    @NotNull
    @Override
    public Map<String, Set<String>> map(@NotNull FileContent inputData) {
        final Map<String, Set<String>> indexMap = new HashMap<>();

        if (inputData.getFileType().isBinary()) {
            return indexMap;
        }

        final PsiFile file = inputData.getPsiFile();

        if (fileDetector.isMainSwaggerFile(file)) {
            Set<String> partialSwaggerFileNames = getPartialSwaggerFileNames(file);

            indexMap.put(SwaggerFileIndex.PARTIAL_SWAGGER_FILES, partialSwaggerFileNames);
            indexMap.put(SwaggerFileIndex.MAIN_SWAGGER_FILE, ImmutableSet.of(file.getName() + DELIMITER + SwaggerFileType.MAIN));
        }
        return indexMap;
    }

    private Set<String> getPartialSwaggerFileNames(PsiFile file) {
        return isJsonFile(file)
                ? getPartialJsonSwaggerFileNames(file)
                : getPartialYamlSwaggerFileNames(file);
    }

    private boolean isJsonFile(PsiFile file) {
        return file instanceof JsonFile;
    }

    private Set<String> getPartialJsonSwaggerFileNames(final PsiFile file) {
        final Set<String> result = new HashSet<>();

        file.accept(new JsonRecursiveElementVisitor() {
            @Override
            public void visitProperty(@NotNull JsonProperty property) {
                if (SwaggerConstants.REF_KEY.equals(property.getName())) {
                    if (property.getValue() != null) {
                        final String refValue = StringUtils.removeAllQuotes(property.getValue().getText());
                        if (refValue.contains(FileConstants.JSON_FILE_NAME_SUFFIX)) {
                            result.add(extractFileNameFromFileRefValue(refValue) + DELIMITER +
                                    getSwaggerFileType(property.getValue(), refValue));
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
                                if (SwaggerConstants.REF_KEY.equals(yamlKeyValue.getKeyText())) {
                                    final String refValue = StringUtils.removeAllQuotes(yamlKeyValue.getValueText());
                                    if (isYamlFile(refValue)) {
                                        result.add(extractFileNameFromFileRefValue(refValue) + DELIMITER +
                                                getSwaggerFileType(yamlKeyValue.getValue(), refValue));
                                    }
                                }
                            }
                            super.visitElement(element);
                        }

                        private boolean isYamlFile(String refValue) {
                            return refValue.contains(FileConstants.YAML_FILE_NAME_SUFFIX) ||
                                    refValue.contains(FileConstants.YML_FILE_NAME_SUFFIX);
                        }
                    }
        );

        return result;
    }

    private String extractFileNameFromFileRefValue(final String fileRefValue) {
        return org.apache.commons.lang.StringUtils.substringBefore(fileRefValue, SwaggerConstants.REFERENCE_PREFIX);
    }

    @NotNull
    private SwaggerFileType getSwaggerFileType(final PsiElement psiElement, final String refValue) {
        if (pathResolver.isDefinitionRefValue(psiElement)) {
            return getFileTypeFromRefValue(refValue,
                    SwaggerFileType.DEFINITIONS,
                    SwaggerFileType.DEFINITIONS_MULTIPLE_IN_ROOT,
                    SwaggerFileType.DEFINITIONS_MULTIPLE_NOT_IN_ROOT);
        } else if (pathResolver.isParameterRefValue(psiElement)) {
            return getFileTypeFromRefValue(refValue,
                    SwaggerFileType.PARAMETERS,
                    SwaggerFileType.PARAMETERS_MULTIPLE_IN_ROOT,
                    SwaggerFileType.PARAMETERS_MULTIPLE_NOT_IN_ROOT);
        } else if (pathResolver.isResponseRefValue(psiElement)) {
            return getFileTypeFromRefValue(refValue,
                    SwaggerFileType.RESPONSES,
                    SwaggerFileType.RESPONSES_MULTIPLE_IN_ROOT,
                    SwaggerFileType.RESPONSES_MULTIPLE_NOT_IN_ROOT);
        }
        return SwaggerFileType.UNDEFINED;
    }

    @NotNull
    private SwaggerFileType getFileTypeFromRefValue(final String refValue,
                                                    final SwaggerFileType singleDefinitionInFile,
                                                    final SwaggerFileType multipleDefinitionsInRootFile,
                                                    final SwaggerFileType multipleDefinitionsNotInRootFile) {
        if (refValue.contains(SwaggerConstants.REFERENCE_PREFIX)) {
            final String definitionPath = org.apache.commons.lang.StringUtils.substringAfterLast(refValue, SwaggerConstants.HASH);
            int slashCount = org.apache.commons.lang.StringUtils.countMatches(definitionPath, SwaggerConstants.SLASH);

            return slashCount == 1 ? multipleDefinitionsInRootFile : multipleDefinitionsNotInRootFile;
        }

        return singleDefinitionInFile;
    }

}
