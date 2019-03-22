package org.zalando.intellij.swagger;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.openapi.vfs.VirtualFile;
import com.jetbrains.jsonSchema.remote.JsonSchemaCatalogExclusion;
import org.jetbrains.annotations.NotNull;
import org.zalando.intellij.swagger.index.IndexService;
import org.zalando.intellij.swagger.index.openapi.OpenApiIndexService;
import org.zalando.intellij.swagger.index.swagger.SwaggerIndexService;

public class SwaggerJsonCatalogExclusion implements JsonSchemaCatalogExclusion {

  private final IndexService indexService =
      new IndexService(new OpenApiIndexService(), new SwaggerIndexService());

  @Override
  public boolean isExcluded(@NotNull VirtualFile file) {
    final Project[] openProjects = ProjectManager.getInstance().getOpenProjects();

    if (openProjects.length > 0) {
      return indexService.isMainSpecFile(file, openProjects[0]);
    }

    return false;
  }
}
