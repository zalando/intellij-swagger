package org.zalando.intellij.swagger.index.openapi;

import static org.apache.commons.lang.StringUtils.substringAfterLast;

import com.intellij.codeInsight.completion.CompletionParameters;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiElement;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.util.indexing.FileBasedIndex;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import org.zalando.intellij.swagger.file.OpenApiFileType;
import org.zalando.intellij.swagger.index.SpecIndexer;

public class OpenApiIndexService {

  public boolean isMainOpenApiFile(final VirtualFile virtualFile, final Project project) {
    return FileBasedIndex.getInstance()
        .getContainingFiles(
            OpenApiFileIndex.OPEN_API_INDEX_ID,
            SpecIndexer.MAIN_SPEC_FILES,
            GlobalSearchScope.allScope(project))
        .contains(virtualFile);
  }

  public boolean isPartialOpenApiFile(final VirtualFile virtualFile, final Project project) {
    return FileBasedIndex.getInstance()
        .getValues(
            OpenApiFileIndex.OPEN_API_INDEX_ID,
            SpecIndexer.PARTIAL_SPEC_FILES,
            GlobalSearchScope.allScope(project))
        .stream()
        .flatMap(Set::stream)
        .anyMatch(value -> value.startsWith(virtualFile.getPath()));
  }

  private OpenApiFileType getPartialOpenApiFileType(
      final VirtualFile virtualFile, final Project project) {
    Optional<String> indexValue =
        FileBasedIndex.getInstance()
            .getValues(
                OpenApiFileIndex.OPEN_API_INDEX_ID,
                SpecIndexer.PARTIAL_SPEC_FILES,
                GlobalSearchScope.allScope(project))
            .stream()
            .flatMap(Set::stream)
            .filter(value -> value.startsWith(virtualFile.getPath()))
            .findFirst();

    return indexValue
        .map(value -> substringAfterLast(value, SpecIndexer.DELIMITER))
        .map(OpenApiFileType::valueOf)
        .orElse(OpenApiFileType.UNDEFINED);
  }

  public Optional<VirtualFile> getMainOpenApiFile(final Project project) {
    final Collection<VirtualFile> mainOpenApiFiles =
        FileBasedIndex.getInstance()
            .getContainingFiles(
                OpenApiFileIndex.OPEN_API_INDEX_ID,
                SpecIndexer.MAIN_SPEC_FILES,
                GlobalSearchScope.allScope(project));

    if (mainOpenApiFiles.isEmpty()) {
      return Optional.empty();
    }

    return Optional.of(mainOpenApiFiles.iterator().next());
  }

  public Optional<OpenApiFileType> getFileType(final PsiElement psiElement) {
    final Project project = psiElement.getProject();
    final VirtualFile virtualFile = psiElement.getContainingFile().getVirtualFile();

    return getOpenApiFileType(project, virtualFile);
  }

  public Optional<OpenApiFileType> getFileType(final CompletionParameters parameters) {
    final Project project = parameters.getOriginalFile().getProject();
    final VirtualFile virtualFile = parameters.getOriginalFile().getVirtualFile();

    return getOpenApiFileType(project, virtualFile);
  }

  public Optional<OpenApiFileType> getOpenApiFileType(
      final Project project, final VirtualFile virtualFile) {
    final boolean isMainOpenApiFile = isMainOpenApiFile(virtualFile, project);
    final boolean isPartialOpenApiFile = isPartialOpenApiFile(virtualFile, project);

    if (isMainOpenApiFile || isPartialOpenApiFile) {
      final OpenApiFileType fileType =
          isMainOpenApiFile
              ? OpenApiFileType.MAIN
              : getPartialOpenApiFileType(virtualFile, project);

      return Optional.of(fileType);
    }

    return Optional.empty();
  }
}
