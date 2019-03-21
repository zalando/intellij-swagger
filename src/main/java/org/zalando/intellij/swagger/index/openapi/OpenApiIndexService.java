package org.zalando.intellij.swagger.index.openapi;

import static org.apache.commons.lang.StringUtils.substringAfterLast;
import static org.apache.commons.lang.StringUtils.substringBeforeLast;

import com.intellij.codeInsight.completion.CompletionParameters;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiElement;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.util.indexing.FileBasedIndex;
import java.util.*;
import java.util.stream.Collectors;
import org.zalando.intellij.swagger.file.OpenApiFileType;

public class OpenApiIndexService {

  public boolean isMainOpenApiFile(final VirtualFile virtualFile, final Project project) {
    final FileBasedIndex index = FileBasedIndex.getInstance();
    final Collection<VirtualFile> openApiFiles =
        index.getContainingFiles(
            OpenApiFileIndex.OPEN_API_INDEX_ID,
            OpenApiFileIndex.MAIN_OPEN_API_FILE,
            GlobalSearchScope.allScope(project));

    return openApiFiles.contains(virtualFile);
  }

  public boolean isPartialOpenApiFile(final VirtualFile virtualFile, final Project project) {
    final Set<VirtualFile> partialOpenApiFiles = getPartialOpenApiFiles(project);
    return partialOpenApiFiles.contains(virtualFile);
  }

  private OpenApiFileType getPartialOpenApiFileType(
      final VirtualFile virtualFile, final Project project) {
    final Set<String> partialOpenApiFilesWithTypeInfo = getPartialOpenApiFilesWithTypeInfo(project);

    final Collection<VirtualFile> mainOpenApiFiles =
        FileBasedIndex.getInstance()
            .getContainingFiles(
                OpenApiFileIndex.OPEN_API_INDEX_ID,
                OpenApiFileIndex.MAIN_OPEN_API_FILE,
                GlobalSearchScope.allScope(project));

    if (mainOpenApiFiles.isEmpty()) {
      return OpenApiFileType.UNDEFINED;
    }

    final VirtualFile mainOpenApiFileFolder = mainOpenApiFiles.iterator().next().getParent();

    return partialOpenApiFilesWithTypeInfo
        .stream()
        .filter(
            nameWithTypeInfo -> {
              final VirtualFile foundFile =
                  mainOpenApiFileFolder.findFileByRelativePath(
                      substringBeforeLast(nameWithTypeInfo, OpenApiDataIndexer.DELIMITER));
              return virtualFile.equals(foundFile);
            })
        .findFirst()
        .map(nameWithTypeInfo -> substringAfterLast(nameWithTypeInfo, OpenApiDataIndexer.DELIMITER))
        .map(OpenApiFileType::valueOf)
        .orElse(OpenApiFileType.UNDEFINED);
  }

  private Set<VirtualFile> getPartialOpenApiFiles(final Project project) {
    final Set<String> partialOpenApiFilesWithTypeInfo = getPartialOpenApiFilesWithTypeInfo(project);

    final Optional<VirtualFile> mainOpenApiFolder =
        getMainOpenApiFile(project).map(VirtualFile::getParent);

    return mainOpenApiFolder
        .map(
            specFolder ->
                partialOpenApiFilesWithTypeInfo
                    .stream()
                    .map(v -> substringBeforeLast(v, OpenApiDataIndexer.DELIMITER))
                    .map(specFolder::findFileByRelativePath)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toSet()))
        .orElse(Collections.emptySet());
  }

  public Optional<VirtualFile> getMainOpenApiFile(final Project project) {
    final Collection<VirtualFile> mainOpenApiFiles =
        FileBasedIndex.getInstance()
            .getContainingFiles(
                OpenApiFileIndex.OPEN_API_INDEX_ID,
                OpenApiFileIndex.MAIN_OPEN_API_FILE,
                GlobalSearchScope.allScope(project));

    if (mainOpenApiFiles.isEmpty()) {
      return Optional.empty();
    }

    return Optional.of(mainOpenApiFiles.iterator().next());
  }

  private Set<String> getPartialOpenApiFilesWithTypeInfo(final Project project) {
    final FileBasedIndex index = FileBasedIndex.getInstance();
    return index
        .getValues(
            OpenApiFileIndex.OPEN_API_INDEX_ID,
            OpenApiFileIndex.PARTIAL_OPEN_API_FILES,
            GlobalSearchScope.allScope(project))
        .stream()
        .flatMap(Set::stream)
        .collect(Collectors.toSet());
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
