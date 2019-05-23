package org.zalando.intellij.swagger.index.swagger;

import static org.apache.commons.lang.StringUtils.substringAfterLast;

import com.intellij.codeInsight.completion.CompletionParameters;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiElement;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.util.indexing.FileBasedIndex;
import java.util.*;
import org.zalando.intellij.swagger.file.SwaggerFileType;
import org.zalando.intellij.swagger.index.SpecIndexer;

public class SwaggerIndexService {

  public boolean isMainSwaggerFile(final VirtualFile virtualFile, final Project project) {
    return FileBasedIndex.getInstance()
        .getContainingFiles(
            SwaggerFileIndex.SWAGGER_INDEX_ID,
            SpecIndexer.MAIN_SPEC_FILES,
            GlobalSearchScope.allScope(project))
        .contains(virtualFile);
  }

  public boolean isPartialSwaggerFile(final VirtualFile virtualFile, final Project project) {
    return FileBasedIndex.getInstance()
        .getValues(
            SwaggerFileIndex.SWAGGER_INDEX_ID,
            SpecIndexer.PARTIAL_SPEC_FILES,
            GlobalSearchScope.allScope(project))
        .stream()
        .flatMap(Set::stream)
        .anyMatch(value -> value.startsWith(virtualFile.getPath()));
  }

  private SwaggerFileType getPartialSwaggerFileType(
      final VirtualFile virtualFile, final Project project) {
    Optional<String> indexValue =
        FileBasedIndex.getInstance()
            .getValues(
                SwaggerFileIndex.SWAGGER_INDEX_ID,
                SpecIndexer.PARTIAL_SPEC_FILES,
                GlobalSearchScope.allScope(project))
            .stream()
            .flatMap(Set::stream)
            .filter(value -> value.startsWith(virtualFile.getPath()))
            .findFirst();

    return indexValue
        .map(value -> substringAfterLast(value, SpecIndexer.DELIMITER))
        .map(SwaggerFileType::valueOf)
        .orElse(SwaggerFileType.UNDEFINED);
  }

  public Optional<VirtualFile> getMainSwaggerFile(final Project project) {
    final Collection<VirtualFile> mainSwaggerFiles =
        FileBasedIndex.getInstance()
            .getContainingFiles(
                SwaggerFileIndex.SWAGGER_INDEX_ID,
                SpecIndexer.MAIN_SPEC_FILES,
                GlobalSearchScope.allScope(project));

    if (mainSwaggerFiles.isEmpty()) {
      return Optional.empty();
    }

    return Optional.of(mainSwaggerFiles.iterator().next());
  }

  public Optional<SwaggerFileType> getFileType(final PsiElement psiElement) {
    final Project project = psiElement.getProject();
    final VirtualFile virtualFile = psiElement.getContainingFile().getVirtualFile();

    return getFileType(project, virtualFile);
  }

  public Optional<SwaggerFileType> getFileType(final CompletionParameters parameters) {
    final Project project = parameters.getOriginalFile().getProject();
    final VirtualFile virtualFile = parameters.getOriginalFile().getVirtualFile();

    return getFileType(project, virtualFile);
  }

  public Optional<SwaggerFileType> getFileType(
      final Project project, final VirtualFile virtualFile) {
    final boolean isMainSwaggerFile = isMainSwaggerFile(virtualFile, project);
    final boolean isPartialSwaggerFile = isPartialSwaggerFile(virtualFile, project);

    if (isMainSwaggerFile || isPartialSwaggerFile) {
      final SwaggerFileType fileType =
          isMainSwaggerFile
              ? SwaggerFileType.MAIN
              : getPartialSwaggerFileType(virtualFile, project);

      return Optional.of(fileType);
    }

    return Optional.empty();
  }
}
