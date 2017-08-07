package org.zalando.intellij.swagger.index.openapi;

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
import org.zalando.intellij.swagger.file.OpenApiFileType;
import org.zalando.intellij.swagger.reference.OpenApiConstants;
import org.zalando.intellij.swagger.traversal.path.openapi.MainPathResolver;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

class OpenApiDataIndexer implements DataIndexer<String, Set<String>, FileContent> {

    static final String DELIMITER = "-";

    private final FileDetector fileDetector = new FileDetector();
    private final MainPathResolver mainPathResolver = new MainPathResolver();

    @NotNull
    @Override
    public Map<String, Set<String>> map(@NotNull FileContent inputData) {
        final Map<String, Set<String>> indexMap = new HashMap<>();

        if (inputData.getFileType().isBinary()) {
            return indexMap;
        }

        final PsiFile file = inputData.getPsiFile();

        if (fileDetector.isMainOpenApiFile(file)) {
            Set<String> partialOpenApiFileNames = getPartialOpenApiFileNames(file);

            indexMap.put(OpenApiFileIndex.PARTIAL_OPEN_API_FILES, partialOpenApiFileNames);
            indexMap.put(OpenApiFileIndex.MAIN_OPEN_API_FILE, ImmutableSet.of(file.getName() + DELIMITER + OpenApiFileType.MAIN));
        }
        return indexMap;
    }

    private Set<String> getPartialOpenApiFileNames(PsiFile file) {
        return isJsonFile(file)
                ? getPartialJsonOpenApiFileNames(file)
                : getPartialYamlOpenApiFileNames(file);
    }

    private boolean isJsonFile(PsiFile file) {
        return file instanceof JsonFile;
    }

    private Set<String> getPartialJsonOpenApiFileNames(final PsiFile file) {
        final Set<String> result = new HashSet<>();

        file.accept(new JsonRecursiveElementVisitor() {
            @Override
            public void visitProperty(@NotNull JsonProperty property) {
                if (OpenApiConstants.REF_KEY.equals(property.getName())) {
                    if (property.getValue() != null) {
                        final String refValue = StringUtils.removeAllQuotes(property.getValue().getText());
                        if (isJsonFile(refValue)) {
                            result.add(extractFileNameFromFileRefValue(refValue) + DELIMITER +
                                    getOpenApiFileTypeFromRefElement(property.getValue(), refValue));
                        }
                    }
                }
                super.visitProperty(property);
            }

            private boolean isJsonFile(final String refValue) {
                return refValue.contains(FileConstants.JSON_FILE_NAME_SUFFIX);
            }
        });

        return result;
    }

    private Set<String> getPartialYamlOpenApiFileNames(final PsiFile file) {
        final Set<String> result = new HashSet<>();

        file.accept(new PsiRecursiveElementVisitor() {
                        @Override
                        public void visitElement(final PsiElement element) {
                            if (element instanceof YAMLKeyValue) {
                                final YAMLKeyValue yamlKeyValue = (YAMLKeyValue) element;
                                if (OpenApiConstants.REF_KEY.equals(yamlKeyValue.getKeyText())) {
                                    final String refValue = StringUtils.removeAllQuotes(yamlKeyValue.getValueText());
                                    if (isYamlFile(refValue)) {
                                        result.add(extractFileNameFromFileRefValue(refValue) + DELIMITER +
                                                getOpenApiFileTypeFromRefElement(yamlKeyValue.getValue(), refValue));
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

    /*
     * Extracts a file name from a file reference. For example:
     *
     * definitions.json#/Pet -> definitions.json
     * definitions.json -> definitions.json
     */
    private String extractFileNameFromFileRefValue(final String fileRefValue) {
        return org.apache.commons.lang.StringUtils.substringBefore(fileRefValue, OpenApiConstants.REFERENCE_PREFIX);
    }

    @NotNull
    private OpenApiFileType getOpenApiFileTypeFromRefElement(final PsiElement psiElement, final String refValue) {
        if (mainPathResolver.isSchemaRefValue(psiElement)) {
            return getFileTypeFromRefValue(refValue,
                    OpenApiFileType.SCHEMAS,
                    OpenApiFileType.SCHEMAS_IN_ROOT);
        }

        return OpenApiFileType.UNDEFINED;
    }

    @NotNull
    private OpenApiFileType getFileTypeFromRefValue(final String refValue,
                                                    final OpenApiFileType singleDefinitionInFile,
                                                    final OpenApiFileType multipleDefinitionsInRootFile) {
        if (refValue.contains(OpenApiConstants.REFERENCE_PREFIX)) {
            return multipleDefinitionsInRootFile;
        }

        return singleDefinitionInFile;
    }

}
