package org.zalando.intellij.swagger;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.openapi.vfs.VirtualFile;
import com.jetbrains.jsonSchema.remote.JsonSchemaCatalogExclusion;
import org.jetbrains.annotations.NotNull;
import org.zalando.intellij.swagger.index.IndexService;

public class SwaggerJsonCatalogExclusion implements JsonSchemaCatalogExclusion {

  private final IndexService indexService;
  private final ProjectManager projectManager;

  public SwaggerJsonCatalogExclusion(
      final IndexService indexService, final ProjectManager projectManager) {
    this.indexService = indexService;
    this.projectManager = projectManager;
  }

  @Override
  public boolean isExcluded(@NotNull VirtualFile file) {
    final Project[] openProjects = projectManager.getOpenProjects();

    if (openProjects.length > 0 && indexService.isIndexReady(openProjects[0])) {
      return indexService.isMainSpecFile(file, openProjects[0]);
    }

    return false;
  }
}
