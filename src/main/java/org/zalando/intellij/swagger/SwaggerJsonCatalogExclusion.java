package org.zalando.intellij.swagger;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiManager;
import com.jetbrains.jsonSchema.remote.JsonSchemaCatalogExclusion;
import org.jetbrains.annotations.NotNull;
import org.zalando.intellij.swagger.file.FileDetector;

public class SwaggerJsonCatalogExclusion implements JsonSchemaCatalogExclusion {

  private final FileDetector fileDetector = new FileDetector();

  @Override
  public boolean isExcluded(@NotNull VirtualFile file) {
    final Project[] openProjects = ProjectManager.getInstance().getOpenProjects();

    if (openProjects.length > 0) {
      final PsiFile psiFile = PsiManager.getInstance(openProjects[0]).findFile(file);

      if (psiFile != null) {
        return fileDetector.isMainSwaggerFile(psiFile) || fileDetector.isMainOpenApiFile(psiFile);
      }
    }

    return false;
  }
}
