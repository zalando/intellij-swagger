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
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.yaml.psi.YAMLKeyValue;
import org.zalando.intellij.swagger.StringUtils;
import org.zalando.intellij.swagger.file.FileDetector;
import org.zalando.intellij.swagger.file.OpenApiFileType;
import org.zalando.intellij.swagger.reference.OpenApiConstants;
import org.zalando.intellij.swagger.traversal.path.openapi.MainPathResolver;

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
      indexMap.put(
          OpenApiFileIndex.MAIN_OPEN_API_FILE,
          ImmutableSet.of(file.getName() + DELIMITER + OpenApiFileType.MAIN));
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

    file.accept(
        new JsonRecursiveElementVisitor() {
          @Override
          public void visitProperty(@NotNull JsonProperty property) {
            if (OpenApiConstants.REF_KEY.equals(property.getName())) {
              if (property.getValue() != null) {
                final String refValue = StringUtils.removeAllQuotes(property.getValue().getText());
                result.add(
                    extractFileNameFromFileRefValue(refValue)
                        + DELIMITER
                        + getOpenApiFileTypeFromRefElement(property.getValue(), refValue));
              }
            }
            super.visitProperty(property);
          }
        });

    return result;
  }

  private Set<String> getPartialYamlOpenApiFileNames(final PsiFile file) {
    final Set<String> result = new HashSet<>();

    file.accept(
        new PsiRecursiveElementVisitor() {
          @Override
          public void visitElement(final PsiElement element) {
            if (element instanceof YAMLKeyValue) {
              final YAMLKeyValue yamlKeyValue = (YAMLKeyValue) element;
              if (OpenApiConstants.REF_KEY.equals(yamlKeyValue.getKeyText())) {
                final String refValue = StringUtils.removeAllQuotes(yamlKeyValue.getValueText());
                result.add(
                    extractFileNameFromFileRefValue(refValue)
                        + DELIMITER
                        + getOpenApiFileTypeFromRefElement(yamlKeyValue.getValue(), refValue));
              }
            }
            super.visitElement(element);
          }
        });

    return result;
  }

  private String extractFileNameFromFileRefValue(final String fileRefValue) {
    return org.apache.commons.lang.StringUtils.substringBefore(
        fileRefValue, OpenApiConstants.REFERENCE_PREFIX);
  }

  @NotNull
  private OpenApiFileType getOpenApiFileTypeFromRefElement(
      final PsiElement psiElement, final String refValue) {
    if (mainPathResolver.isSchemaRefValue(psiElement)) {
      return getFileTypeFromRefValue(
          refValue, OpenApiFileType.SINGLE_SCHEMA, OpenApiFileType.MULTIPLE_SCHEMAS);
    } else if (mainPathResolver.isResponseRefValue(psiElement)) {
      return getFileTypeFromRefValue(
          refValue, OpenApiFileType.SINGLE_RESPONSE, OpenApiFileType.MULTIPLE_RESPONSES);
    } else if (mainPathResolver.isParameterRefValue(psiElement)) {
      return getFileTypeFromRefValue(
          refValue, OpenApiFileType.SINGLE_PARAMETER, OpenApiFileType.MULTIPLE_PARAMETERS);
    } else if (mainPathResolver.isExampleRefValue(psiElement)) {
      return getFileTypeFromRefValue(
          refValue, OpenApiFileType.SINGLE_EXAMPLE, OpenApiFileType.MULTIPLE_EXAMPLES);
    } else if (mainPathResolver.isRequestBodyRefValue(psiElement)) {
      return getFileTypeFromRefValue(
          refValue, OpenApiFileType.SINGLE_REQUEST_BODY, OpenApiFileType.MULTIPLE_REQUEST_BODIES);
    } else if (mainPathResolver.isHeaderRefValue(psiElement)) {
      return getFileTypeFromRefValue(
          refValue, OpenApiFileType.SINGLE_HEADER, OpenApiFileType.MULTIPLE_HEADERS);
    } else if (mainPathResolver.isLinkRefValue(psiElement)) {
      return getFileTypeFromRefValue(
          refValue, OpenApiFileType.SINGLE_LINK, OpenApiFileType.MULTIPLE_LINKS);
    } else if (mainPathResolver.isCallbackRefValue(psiElement)) {
      return getFileTypeFromRefValue(
          refValue, OpenApiFileType.SINGLE_CALLBACK, OpenApiFileType.MULTIPLE_CALLBACKS);
    }

    return OpenApiFileType.UNDEFINED;
  }

  @NotNull
  private OpenApiFileType getFileTypeFromRefValue(
      final String refValue,
      final OpenApiFileType singleDefinitionInFile,
      final OpenApiFileType multipleDefinitionsInFile) {
    return refValue.contains(OpenApiConstants.HASH + OpenApiConstants.SLASH)
        ? multipleDefinitionsInFile
        : singleDefinitionInFile;
  }
}
