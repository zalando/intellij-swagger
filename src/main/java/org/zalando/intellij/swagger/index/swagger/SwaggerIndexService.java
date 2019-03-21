package org.zalando.intellij.swagger.index.swagger;

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
import org.zalando.intellij.swagger.file.SwaggerFileType;

public class SwaggerIndexService {

  public boolean isMainSwaggerFile(final VirtualFile virtualFile, final Project project) {
    final FileBasedIndex index = FileBasedIndex.getInstance();
    final Collection<VirtualFile> swaggerFiles =
        index.getContainingFiles(
            SwaggerFileIndex.SWAGGER_INDEX_ID,
            SwaggerFileIndex.MAIN_SWAGGER_FILE,
            GlobalSearchScope.allScope(project));
    return swaggerFiles.contains(virtualFile);
  }

  public boolean isPartialSwaggerFile(final VirtualFile virtualFile, final Project project) {
    final Set<VirtualFile> partialSwaggerFiles = getPartialSwaggerFiles(project);
    return partialSwaggerFiles.contains(virtualFile);
  }

  private SwaggerFileType getPartialSwaggerFileType(
      final VirtualFile virtualFile, final Project project) {
    final Set<String> partialSwaggerFilesWithTypeInfo = getPartialSwaggerFilesWithTypeInfo(project);

    final Collection<VirtualFile> mainSwaggerFiles =
        FileBasedIndex.getInstance()
            .getContainingFiles(
                SwaggerFileIndex.SWAGGER_INDEX_ID,
                SwaggerFileIndex.MAIN_SWAGGER_FILE,
                GlobalSearchScope.allScope(project));

    if (mainSwaggerFiles.isEmpty()) {
      return SwaggerFileType.UNDEFINED;
    }

    final VirtualFile mainSwaggerFileFolder = mainSwaggerFiles.iterator().next().getParent();

    return partialSwaggerFilesWithTypeInfo
        .stream()
        .filter(
            nameWithTypeInfo -> {
              final VirtualFile foundFile =
                  mainSwaggerFileFolder.findFileByRelativePath(
                      substringBeforeLast(nameWithTypeInfo, SwaggerDataIndexer.DELIMITER));
              return virtualFile.equals(foundFile);
            })
        .findFirst()
        .map(nameWithTypeInfo -> substringAfterLast(nameWithTypeInfo, SwaggerDataIndexer.DELIMITER))
        .map(SwaggerFileType::valueOf)
        .orElse(SwaggerFileType.UNDEFINED);
  }

  public Optional<VirtualFile> getMainSwaggerFile(final Project project) {
    final Collection<VirtualFile> mainSwaggerFiles =
        FileBasedIndex.getInstance()
            .getContainingFiles(
                SwaggerFileIndex.SWAGGER_INDEX_ID,
                SwaggerFileIndex.MAIN_SWAGGER_FILE,
                GlobalSearchScope.allScope(project));

    if (mainSwaggerFiles.isEmpty()) {
      return Optional.empty();
    }

    return Optional.of(mainSwaggerFiles.iterator().next());
  }

  private Set<VirtualFile> getPartialSwaggerFiles(final Project project) {
    final Set<String> partialSwaggerFilesWithTypeInfo = getPartialSwaggerFilesWithTypeInfo(project);

    final Optional<VirtualFile> mainSwaggerFolder =
        getMainSwaggerFile(project).map(VirtualFile::getParent);

    return mainSwaggerFolder
        .map(
            specFolder ->
                partialSwaggerFilesWithTypeInfo
                    .stream()
                    .map(v -> substringBeforeLast(v, SwaggerDataIndexer.DELIMITER))
                    .map(specFolder::findFileByRelativePath)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toSet()))
        .orElse(Collections.emptySet());
  }

  private Set<String> getPartialSwaggerFilesWithTypeInfo(final Project project) {
    final FileBasedIndex index = FileBasedIndex.getInstance();
    return index
        .getValues(
            SwaggerFileIndex.SWAGGER_INDEX_ID,
            SwaggerFileIndex.PARTIAL_SWAGGER_FILES,
            GlobalSearchScope.allScope(project))
        .stream()
        .flatMap(Set::stream)
        .collect(Collectors.toSet());
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
