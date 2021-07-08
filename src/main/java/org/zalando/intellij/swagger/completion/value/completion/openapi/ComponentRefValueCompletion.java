package org.zalando.intellij.swagger.completion.value.completion.openapi;

import com.intellij.codeInsight.completion.CompletionResultSet;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ProjectFileIndex;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiManager;
import com.intellij.psi.search.FilenameIndex;
import com.intellij.psi.search.GlobalSearchScope;
import java.util.List;
import java.util.stream.Collectors;
import org.zalando.intellij.swagger.completion.CompletionHelper;
import org.zalando.intellij.swagger.completion.value.ValueCompletion;
import org.zalando.intellij.swagger.completion.value.model.common.StringValue;
import org.zalando.intellij.swagger.completion.value.model.common.Value;
import org.zalando.intellij.swagger.traversal.path.PathFinder;

class ComponentRefValueCompletion extends ValueCompletion {

  private final String refType;

  ComponentRefValueCompletion(
      final CompletionHelper completionHelper,
      final CompletionResultSet completionResultSet,
      final String refType) {
    super(completionHelper, completionResultSet);
    this.refType = refType;
  }

  @Override
  public void fill() {
    final PsiFile psiFile = completionHelper.getPsiFile().getOriginalFile();
    final Project project = psiFile.getProject();
    final PsiManager psiManager = PsiManager.getInstance(project);
    final VirtualFile virtualFile = psiFile.getVirtualFile();

    getSchemaKeys(psiFile).forEach(this::addValue);

    if (virtualFile != null && virtualFile.getExtension() != null) {
      final Module module = ProjectFileIndex.getInstance(project).getModuleForFile(virtualFile);

      if (module != null) {
        FilenameIndex.getAllFilesByExt(
                project, virtualFile.getExtension(), GlobalSearchScope.moduleScope(module))
            .forEach(
                f -> {
                  PsiFile file = psiManager.findFile(f);
                  if (file != null && !psiFile.equals(file)) {
                    getSchemaKeys(file)
                        .stream()
                        .map(
                            v -> {
                              return new StringValue("./" + file.getName() + v.getValue());
                            })
                        .forEach(this::addValue);
                  }
                });
      }
    }
  }

  private List<Value> getSchemaKeys(PsiFile psiFile) {
    final String pathExpression = String.format("$.components.%s", refType);

    return new PathFinder()
        .findNamedChildren(pathExpression, psiFile)
        .stream()
        .map(e -> String.format("#/components/%s/%s", refType, e.getName()))
        .map(StringValue::new)
        .collect(Collectors.toList());
  }
}
