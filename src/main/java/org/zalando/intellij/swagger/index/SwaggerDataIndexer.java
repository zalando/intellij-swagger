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
import org.zalando.intellij.swagger.file.FileConstants;
import org.zalando.intellij.swagger.file.FileDetector;
import org.zalando.intellij.swagger.file.SwaggerFileType;
import org.zalando.intellij.swagger.reference.SwaggerConstants;
import org.zalando.intellij.swagger.traversal.MainPathResolver;
import org.zalando.intellij.swagger.traversal.PathResolver;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

class SwaggerDataIndexer implements DataIndexer<String, Set<String>, FileContent> {

    public static final String DELIMITER = "-";

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
            indexMap.put(SwaggerFileIndex.MAIN_SWAGGER_FILE, ImmutableSet.of(file.getName() + DELIMITER + SwaggerFileType.MAIN));
        }
        return indexMap;
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
                                    if (refValue.contains(FileConstants.YAML_FILE_NAME_SUFFIX) ||
                                            refValue.contains(FileConstants.YML_FILE_NAME_SUFFIX)) {
                                        result.add(extractFileNameFromFileRefValue(refValue) + DELIMITER +
                                                getSwaggerFileType(yamlKeyValue.getValue(), refValue));
                                    }
                                }
                            }
                            super.visitElement(element);
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
            if (refValue.contains(SwaggerConstants.REFERENCE_PREFIX)) {
                final String definitionPath =
                        org.apache.commons.lang.StringUtils.substringAfterLast(refValue, SwaggerConstants.HASH);
                int slashCount = org.apache.commons.lang.StringUtils.countMatches(definitionPath, SwaggerConstants.SLASH);

                /*
                * If slash count is one, the referenced file contains definitions in root. For example:
                * $ref: "definitions.json#/Security" would point to file "definitions.json" with content:
                *
                * {
                *   "Security": {}
                * }
                *
                */
                if (slashCount == 1) {
                    return SwaggerFileType.DEFINITIONS_MULTIPLE_IN_ROOT;
                }
                /*
                * If slash count is other than one, the referenced file contains definitions wrapped in root object.
                * For example:
                *
                * $ref: "definitions.json#/models/Security" would point to file "definitions.json" with content:
                *
                * {
                *   "models" {
                *       "Security": {}
                *   }
                * }
                *
                */
                else {
                    return SwaggerFileType.DEFINITIONS_MULTIPLE_NOT_IN_ROOT;
                }
            }
            return SwaggerFileType.DEFINITIONS;
        } else if (pathResolver.isParameterRefValue(psiElement)) {
            if (refValue.contains(SwaggerConstants.REFERENCE_PREFIX)) {
                final String definitionPath =
                        org.apache.commons.lang.StringUtils.substringAfterLast(refValue, SwaggerConstants.HASH);
                int slashCount = org.apache.commons.lang.StringUtils.countMatches(definitionPath, SwaggerConstants.SLASH);
                if (slashCount == 1) {
                    return SwaggerFileType.PARAMETERS_MULTIPLE_IN_ROOT;
                } else {
                    return SwaggerFileType.PARAMETERS_MULTIPLE_NOT_IN_ROOT;
                }
            }
            return SwaggerFileType.PARAMETERS;
        }
        return SwaggerFileType.UNDEFINED;
    }
}
