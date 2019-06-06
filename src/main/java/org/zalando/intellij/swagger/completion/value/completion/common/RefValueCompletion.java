package org.zalando.intellij.swagger.completion.value.completion.common;

import com.google.common.collect.Lists;
import com.intellij.codeInsight.completion.CompletionResultSet;
import com.intellij.openapi.vfs.VfsUtil;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiNamedElement;
import com.intellij.psi.util.PsiTreeUtil;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.zalando.intellij.swagger.completion.CompletionHelper;
import org.zalando.intellij.swagger.completion.value.ValueCompletion;
import org.zalando.intellij.swagger.completion.value.model.common.StringValue;
import org.zalando.intellij.swagger.completion.value.model.common.Value;
import org.zalando.intellij.swagger.file.SpecFileType;
import org.zalando.intellij.swagger.traversal.path.PathFinder;

public class RefValueCompletion extends ValueCompletion {

  private static final char PATH_SEPARATOR = '/';

  private final SpecFileType specFileType;

  public RefValueCompletion(
      final CompletionHelper completionHelper,
      final CompletionResultSet completionResultSet,
      final SpecFileType specFileType) {
    super(completionHelper, completionResultSet);
    this.specFileType = specFileType;
  }

  @Override
  public void fill() {
    getSuggestions().forEach(this::addValue);
  }

  private List<Value> getSuggestions() {
    final List<Value> suggestions = getLocalSuggestions();
    suggestions.addAll(getExternalSuggestions());
    return suggestions;
  }

  private List<Value> getExternalSuggestions() {
    return completionHelper.getPartialSpecFiles(specFileType).entrySet().stream()
        .flatMap(
            entry -> {
              final PsiFile file = entry.getKey();
              final SpecFileType type = entry.getValue();

              if (type.isSingleDefinition()) {
                return getSingleDefinition(file);
              } else if (type.isMultipleDefinitionsInRoot()) {
                return getMultipleDefinitionsInRoot(file);
              } else if (type.isMultipleDefinitionsNotInRoot()) {
                return getMultipleDefinitionsNotInRoot(file);
              } else {
                return Lists.<StringValue>newArrayList().stream();
              }
            })
        .collect(Collectors.toList());
  }

  private Stream<? extends Value> getSingleDefinition(final PsiFile file) {
    final String relativePath =
        VfsUtil.findRelativePath(
            completionHelper.getVirtualFile(),
            file.getOriginalFile().getVirtualFile(),
            PATH_SEPARATOR);

    return Lists.newArrayList(new StringValue(relativePath)).stream();
  }

  private Stream<Value> getMultipleDefinitionsInRoot(final PsiFile file) {
    return PsiTreeUtil.findChildrenOfType(file, PsiNamedElement.class).stream()
        .map(PsiNamedElement::getName)
        .map(
            key -> {
              final String relativePath =
                  VfsUtil.findRelativePath(
                      completionHelper.getVirtualFile(), file.getVirtualFile(), PATH_SEPARATOR);

              if (relativePath == null) {
                return null;
              }

              return String.format("%s#/%s", relativePath, key);
            })
        .filter(Objects::nonNull)
        .map(StringValue::new);
  }

  private Stream<Value> getMultipleDefinitionsNotInRoot(final PsiFile file) {
    return PsiTreeUtil.findChildrenOfType(file, PsiNamedElement.class).stream()
        .flatMap(
            rootKey ->
                PsiTreeUtil.findChildrenOfType(rootKey, PsiNamedElement.class).stream()
                    .map(
                        key -> {
                          final String relativePath =
                              VfsUtil.findRelativePath(
                                  completionHelper.getVirtualFile(),
                                  file.getVirtualFile(),
                                  PATH_SEPARATOR);

                          if (relativePath == null) {
                            return null;
                          }

                          return String.format(
                              "%s#/%s/%s", relativePath, rootKey.getName(), key.getName());
                        }))
        .filter(Objects::nonNull)
        .map(StringValue::new);
  }

  private List<Value> getLocalSuggestions() {
    final String pathExpression = String.format("$.%s", specFileType.asSpecKey());

    return new PathFinder()
        .findNamedChildren(pathExpression, completionHelper.getPsiFile()).stream()
            .map(e -> String.format("#/%s/%s", specFileType.asSpecKey(), e.getName()))
            .map(StringValue::new)
            .collect(Collectors.toList());
  }
}
