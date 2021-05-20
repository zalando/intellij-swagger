package org.zalando.intellij.swagger.file.icon;

import com.intellij.ide.IconProvider;
import com.intellij.json.psi.JsonFile;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiElement;
import javax.swing.Icon;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.yaml.psi.YAMLFile;
import org.zalando.intellij.swagger.index.IndexService;

public abstract class SpecIconProvider extends IconProvider {

  @Nullable
  @Override
  public Icon getIcon(@NotNull PsiElement element, int flags) {
    if (element instanceof YAMLFile || element instanceof JsonFile) {

      final VirtualFile virtualFile = element.getContainingFile().getVirtualFile();
      final Project project = element.getProject();

      IndexService indexService = getIndexService();
      if (indexService.isMainSpecFile(virtualFile, project)
          || indexService.isPartialSpecFile(virtualFile, project)) {
        return getIcon();
      }
    }

    return null;
  }

  protected abstract IndexService getIndexService();

  protected abstract Icon getIcon();
}
