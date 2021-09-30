package org.zalando.intellij.swagger.index;

import com.google.common.collect.ImmutableSet;
import com.intellij.json.psi.JsonFile;
import com.intellij.json.psi.JsonProperty;
import com.intellij.json.psi.impl.JsonRecursiveElementVisitor;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.util.indexing.DataIndexer;
import com.intellij.util.indexing.FileContent;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.yaml.psi.YAMLKeyValue;
import org.jetbrains.yaml.psi.YamlRecursivePsiElementVisitor;
import org.zalando.intellij.swagger.StringUtils;
import org.zalando.intellij.swagger.file.SpecFileType;
import org.zalando.intellij.swagger.reference.ApiConstants;
import org.zalando.intellij.swagger.service.SwaggerFilesUtils;

public abstract class SpecIndexer implements DataIndexer<String, Set<String>, FileContent> {

  public static String MAIN_SPEC_FILES = "MAIN_SPEC_FILES";
  public static String PARTIAL_SPEC_FILES = "PARTIAL_SPEC_FILES";
  public static final String DELIMITER = "-";

  protected abstract boolean shouldIndex(PsiFile file);

  protected abstract String getFileType(final PsiElement psiElement, final String refValue);

  private final SpecFileType mainSpecFileType;

  protected SpecIndexer(final SpecFileType mainSpecFileType) {
    this.mainSpecFileType = mainSpecFileType;
  }

  @NotNull
  @Override
  public Map<String, Set<String>> map(@NotNull FileContent inputData) {
    final Map<String, Set<String>> indexMap = new HashMap<>();

    if (inputData.getFileType().isBinary()) {
      return indexMap;
    }

    final PsiFile file = inputData.getPsiFile();

    if (shouldIndex(file)) {
      final VirtualFile specDirectory = inputData.getFile().getParent();
      final Set<String> referencedFiles = getReferencedFiles(file, specDirectory);

      indexMap.put(
          MAIN_SPEC_FILES,
          ImmutableSet.of(file.getName() + DELIMITER + mainSpecFileType.toString()));
      indexMap.put(PARTIAL_SPEC_FILES, referencedFiles);
    }
    return indexMap;
  }

  private Set<String> getReferencedFiles(final PsiFile file, final VirtualFile specDirectory) {
    return file instanceof JsonFile
        ? getReferencedFilesJson(file, specDirectory)
        : getReferencedFilesYaml(file, specDirectory);
  }

  private Set<String> getReferencedFilesJson(final PsiFile file, final VirtualFile specDirectory) {
    final Set<String> result = new HashSet<>();

    file.accept(
        new JsonRecursiveElementVisitor() {
          @Override
          public void visitProperty(@NotNull JsonProperty property) {
            if (ApiConstants.REF_KEY.equals(property.getName()) && property.getValue() != null) {
              final String refValue = StringUtils.removeAllQuotes(property.getValue().getText());

              if (SwaggerFilesUtils.isFileReference(refValue)) {
                getReferencedFileIndexValue(property.getValue(), refValue, specDirectory)
                    .ifPresent(result::add);
              }
            }
            super.visitProperty(property);
          }
        });

    return result;
  }

  private Set<String> getReferencedFilesYaml(final PsiFile file, final VirtualFile specDirectory) {
    final Set<String> result = new HashSet<>();

    file.accept(
        new YamlRecursivePsiElementVisitor() {
          @Override
          public void visitKeyValue(@NotNull YAMLKeyValue yamlKeyValue) {
            if (ApiConstants.REF_KEY.equals(yamlKeyValue.getKeyText())
                && yamlKeyValue.getValue() != null) {
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

  private Optional<String> getReferencedFileIndexValue(
      final PsiElement psiElement, final String refValue, final VirtualFile specDirectory) {
    final String relativePath =
        org.apache.commons.lang.StringUtils.substringBefore(
            refValue, ApiConstants.REFERENCE_PREFIX);

    return Optional.ofNullable(specDirectory.findFileByRelativePath(relativePath))
        .map(file -> file.getPath() + DELIMITER + getFileType(psiElement, refValue));
  }
}
