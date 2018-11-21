package org.zalando.intellij.swagger.file.icon;

import com.intellij.ide.IconProvider;
import com.intellij.json.psi.JsonFile;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiElement;
import javax.swing.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.yaml.psi.YAMLFile;
import org.zalando.intellij.swagger.index.swagger.SwaggerIndexService;

public class SwaggerIconProvider extends IconProvider {

  private final SwaggerIndexService swaggerIndexService = new SwaggerIndexService();

  @Nullable
  @Override
  public Icon getIcon(@NotNull PsiElement element, int flags) {
    if (element instanceof YAMLFile || element instanceof JsonFile) {

      final VirtualFile virtualFile = element.getContainingFile().getVirtualFile();
      final Project project = element.getProject();

      if (isMain(virtualFile, project) || isPartial(virtualFile, project)) {
        return Icons.SWAGGER_API_ICON;
      }
    }

    return null;
  }

  private boolean isPartial(VirtualFile virtualFile, Project project) {
    return swaggerIndexService.isPartialSwaggerFile(virtualFile, project);
  }

  private boolean isMain(VirtualFile virtualFile, Project project) {
    return swaggerIndexService.isMainSwaggerFile(virtualFile, project);
  }
}
