package org.zalando.intellij.swagger;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.openapi.vfs.VirtualFile;
import com.jetbrains.jsonSchema.remote.JsonSchemaCatalogExclusion;
import org.jetbrains.annotations.NotNull;
import org.zalando.intellij.swagger.index.IndexFacade;

public class SwaggerJsonCatalogExclusion implements JsonSchemaCatalogExclusion {

  @Override
  public boolean isExcluded(@NotNull VirtualFile file) {
    final Project[] openProjects =
        ApplicationManager.getApplication().getService(ProjectManager.class).getOpenProjects();

    IndexFacade indexFacade = ApplicationManager.getApplication().getService(IndexFacade.class);
    if (openProjects.length > 0 && indexFacade.isIndexReady(openProjects[0])) {
      return indexFacade.isMainSpecFile(file, openProjects[0]);
    }

    return false;
  }
}
