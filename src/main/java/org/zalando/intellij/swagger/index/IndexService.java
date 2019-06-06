package org.zalando.intellij.swagger.index;

import com.intellij.codeInsight.completion.CompletionParameters;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiManager;
import com.intellij.psi.search.FilenameIndex;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.util.indexing.FileBasedIndex;
import com.intellij.util.indexing.ID;

import org.apache.commons.io.FilenameUtils;
import org.zalando.intellij.swagger.file.SpecFileType;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static org.apache.commons.lang.StringUtils.substringAfterLast;
import static org.apache.commons.lang.StringUtils.substringBeforeLast;

public abstract class IndexService<T extends SpecFileType> {

  public abstract ID<String, Set<String>> getIndexId();

  protected abstract T toSpecType(final String raw);

  protected abstract T getMainSpecType();

  protected abstract T getUndefinedSpecType();

  public boolean isMainSpecFile(final VirtualFile virtualFile, final Project project) {
    return FileBasedIndex.getInstance()
        .getContainingFiles(
            getIndexId(), SpecIndexer.MAIN_SPEC_FILES, GlobalSearchScope.allScope(project))
        .contains(virtualFile);
  }

  public boolean isPartialSpecFile(final VirtualFile virtualFile, final Project project) {
    return FileBasedIndex.getInstance()
        .getValues(
            getIndexId(), SpecIndexer.PARTIAL_SPEC_FILES, GlobalSearchScope.allScope(project))
        .stream()
        .flatMap(Set::stream)
        .anyMatch(value -> value.startsWith(virtualFile.getPath()));
  }

  public Optional<VirtualFile> getMainSpecFile(final Project project) {
    final Collection<VirtualFile> mainSpecFiles =
        FileBasedIndex.getInstance()
            .getContainingFiles(
                getIndexId(), SpecIndexer.MAIN_SPEC_FILES, GlobalSearchScope.allScope(project));

    if (mainSpecFiles.isEmpty()) {
      return Optional.empty();
    }

    return Optional.of(mainSpecFiles.iterator().next());
  }

  public Optional<T> getFileType(final PsiElement psiElement) {
    final Project project = psiElement.getProject();
    final VirtualFile virtualFile = psiElement.getContainingFile().getVirtualFile();

    return getFileType(project, virtualFile);
  }

  public Optional<T> getFileType(final CompletionParameters parameters) {
    final Project project = parameters.getOriginalFile().getProject();
    final VirtualFile virtualFile = parameters.getOriginalFile().getVirtualFile();

    return getFileType(project, virtualFile);
  }

  public Optional<T> getFileType(final Project project, final VirtualFile virtualFile) {
    if (isMainSpecFile(virtualFile, project)) {
      return Optional.of(getMainSpecType());
    } else if (isPartialSpecFile(virtualFile, project)) {
      return Optional.of(getPartialSpecFileType(virtualFile, project));
    } else {
      return Optional.empty();
    }
  }

  private T getPartialSpecFileType(final VirtualFile virtualFile, final Project project) {
    Optional<String> indexValue =
        FileBasedIndex.getInstance()
            .getValues(
                getIndexId(), SpecIndexer.PARTIAL_SPEC_FILES, GlobalSearchScope.allScope(project))
            .stream()
            .flatMap(Set::stream)
            .filter(value -> value.startsWith(virtualFile.getPath()))
            .findFirst();

    return indexValue
        .map(value -> substringAfterLast(value, SpecIndexer.DELIMITER))
        .map(this::toSpecType)
        .orElse(getUndefinedSpecType());
  }

  public Map<PsiFile, SpecFileType> getPartialSpecFiles(
      final Project project, final SpecFileType specFileType) {
    return FileBasedIndex.getInstance()
        .getValues(
            getIndexId(), SpecIndexer.PARTIAL_SPEC_FILES, GlobalSearchScope.allScope(project))
        .stream()
        .flatMap(Set::stream)
        .collect(
            Collectors.toMap(
                value -> getPsiFile(project, value),
                value -> getSpecFileType(specFileType, value)));
  }

  private SpecFileType getSpecFileType(final SpecFileType specFileType, final String value) {
    return specFileType.fromString(substringAfterLast(value, SpecIndexer.DELIMITER));
  }

  private PsiFile getPsiFile(final Project project, final String value) {
    final String path = substringBeforeLast(value, SpecIndexer.DELIMITER);
    final String name = FilenameUtils.getName(path);

    return FilenameIndex.getVirtualFilesByName(project, name, GlobalSearchScope.allScope(project))
        .stream()
        .filter(file -> file.getPath().equals(path))
        .findFirst()
        .map(file -> PsiManager.getInstance(project).findFile(file))
        .orElse(null);
  }
}
