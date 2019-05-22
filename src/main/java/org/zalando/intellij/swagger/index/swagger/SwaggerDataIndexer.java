package org.zalando.intellij.swagger.index.swagger;

import com.google.common.collect.ImmutableSet;
import com.intellij.json.psi.JsonFile;
import com.intellij.json.psi.JsonProperty;
import com.intellij.json.psi.impl.JsonRecursiveElementVisitor;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.util.containers.HashSet;
import com.intellij.util.indexing.DataIndexer;
import com.intellij.util.indexing.FileContent;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.yaml.psi.YAMLKeyValue;
import org.jetbrains.yaml.psi.YamlRecursivePsiElementVisitor;
import org.zalando.intellij.swagger.StringUtils;
import org.zalando.intellij.swagger.file.FileDetector;
import org.zalando.intellij.swagger.file.SwaggerFileType;
import org.zalando.intellij.swagger.reference.SwaggerConstants;
import org.zalando.intellij.swagger.service.SwaggerFilesUtils;
import org.zalando.intellij.swagger.traversal.path.swagger.MainPathResolver;
import org.zalando.intellij.swagger.traversal.path.swagger.PathResolver;

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

    final PsiFile specFile = inputData.getPsiFile();

    if (fileDetector.isMainSwaggerFile(specFile)) {
      final VirtualFile specDirectory = inputData.getFile().getParent();
      final Set<String> referencedFiles = getReferencedFiles(specFile, specDirectory);

      indexMap.put(SwaggerFileIndex.PARTIAL_SWAGGER_FILES, referencedFiles);
      indexMap.put(
          SwaggerFileIndex.MAIN_SWAGGER_FILE,
          ImmutableSet.of(specFile.getName() + DELIMITER + SwaggerFileType.MAIN));
    }
    return indexMap;
  }

  private Set<String> getReferencedFiles(final PsiFile file, final VirtualFile specDirectory) {
    return isJsonFile(file)
        ? getPartialJsonSwaggerFileNames(file, specDirectory)
        : getPartialYamlSwaggerFileNames(file, specDirectory);
  }

  private boolean isJsonFile(PsiFile file) {
    return file instanceof JsonFile;
  }

  private Set<String> getPartialJsonSwaggerFileNames(
      final PsiFile file, final VirtualFile specDirectory) {
    final Set<String> result = new HashSet<>();

    file.accept(
        new JsonRecursiveElementVisitor() {
          @Override
          public void visitProperty(@NotNull JsonProperty property) {
            if (SwaggerConstants.REF_KEY.equals(property.getName())) {
              if (property.getValue() != null) {
                final String refValue = StringUtils.removeAllQuotes(property.getValue().getText());

                if (SwaggerFilesUtils.isFileReference(refValue)) {
                  getReferencedFileIndexValue(property.getValue(), refValue, specDirectory)
                      .ifPresent(result::add);
                }
              }
            }
            super.visitProperty(property);
          }
        });

    return result;
  }

  private Optional<String> getReferencedFileIndexValue(
      final PsiElement psiElement, final String refValue, final VirtualFile specDirectory) {
    final String relativePath =
        org.apache.commons.lang.StringUtils.substringBefore(
            refValue, SwaggerConstants.REFERENCE_PREFIX);

    return Optional.ofNullable(specDirectory.findFileByRelativePath(relativePath))
        .map(file -> file.getPath() + DELIMITER + getSwaggerFileType(psiElement, refValue));
  }

  private Set<String> getPartialYamlSwaggerFileNames(
      final PsiFile file, final VirtualFile specDirectory) {
    final Set<String> result = new HashSet<>();

    file.accept(
        new YamlRecursivePsiElementVisitor() {
          @Override
          public void visitKeyValue(@NotNull YAMLKeyValue yamlKeyValue) {
            if (SwaggerConstants.REF_KEY.equals(yamlKeyValue.getKeyText())) {
              final String refValue = StringUtils.removeAllQuotes(yamlKeyValue.getValueText());

              if (SwaggerFilesUtils.isFileReference(refValue)) {
                getReferencedFileIndexValue(yamlKeyValue.getValue(), refValue, specDirectory)
                    .ifPresent(result::add);
              }
            }
            super.visitKeyValue(yamlKeyValue);
          }
        });

    return result;
  }

  @NotNull
  private SwaggerFileType getSwaggerFileType(final PsiElement psiElement, final String refValue) {
    if (pathResolver.isDefinitionRefValue(psiElement)) {
      return getFileTypeFromRefValue(
          refValue,
          SwaggerFileType.DEFINITIONS,
          SwaggerFileType.DEFINITIONS_MULTIPLE_IN_ROOT,
          SwaggerFileType.DEFINITIONS_MULTIPLE_NOT_IN_ROOT);
    } else if (pathResolver.isParameterRefValue(psiElement)) {
      return getFileTypeFromRefValue(
          refValue,
          SwaggerFileType.PARAMETERS,
          SwaggerFileType.PARAMETERS_MULTIPLE_IN_ROOT,
          SwaggerFileType.PARAMETERS_MULTIPLE_NOT_IN_ROOT);
    } else if (pathResolver.isResponseRefValue(psiElement)) {
      return getFileTypeFromRefValue(
          refValue,
          SwaggerFileType.RESPONSES,
          SwaggerFileType.RESPONSES_MULTIPLE_IN_ROOT,
          SwaggerFileType.RESPONSES_MULTIPLE_NOT_IN_ROOT);
    } else if (pathResolver.isPathRefValue(psiElement)) {
      return getFileTypeFromRefValue(
          refValue,
          SwaggerFileType.PATHS,
          SwaggerFileType.PATHS_MULTIPLE_IN_ROOT,
          SwaggerFileType.PATHS_MULTIPLE_NOT_IN_ROOT);
    }
    return SwaggerFileType.UNDEFINED;
  }

  @NotNull
  private SwaggerFileType getFileTypeFromRefValue(
      final String refValue,
      final SwaggerFileType singleDefinitionInFile,
      final SwaggerFileType multipleDefinitionsInRootFile,
      final SwaggerFileType multipleDefinitionsNotInRootFile) {
    if (refValue.contains(SwaggerConstants.REFERENCE_PREFIX)) {
      final String definitionPath =
          org.apache.commons.lang.StringUtils.substringAfterLast(refValue, SwaggerConstants.HASH);
      int slashCount =
          org.apache.commons.lang.StringUtils.countMatches(definitionPath, SwaggerConstants.SLASH);

      return slashCount == 1 ? multipleDefinitionsInRootFile : multipleDefinitionsNotInRootFile;
    }

    return singleDefinitionInFile;
  }
}
