package org.zalando.intellij.swagger.index;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiFile;
import java.util.Optional;
import org.zalando.intellij.swagger.index.openapi.OpenApiIndexService;
import org.zalando.intellij.swagger.index.swagger.SwaggerIndexService;
import org.zalando.intellij.swagger.service.intellij.DumbService;

public class IndexFacade {

  public Optional<VirtualFile> getMainSpecFile(final PsiFile psiFile) {
    final VirtualFile virtualFile = psiFile.getVirtualFile();
    final Project project = psiFile.getProject();

    OpenApiIndexService openApiIndexService = ApplicationManager.getApplication().getService(OpenApiIndexService.class);
    SwaggerIndexService swaggerIndexService = ApplicationManager.getApplication().getService(SwaggerIndexService.class);

    if (isMainSpecFile(virtualFile, project)) {
      return Optional.of(psiFile.getVirtualFile());
    } else if (openApiIndexService.isPartialSpecFile(virtualFile, project)) {
      return openApiIndexService.getMainSpecFile(project);
    } else if (swaggerIndexService.isPartialSpecFile(virtualFile, project)) {
      return swaggerIndexService.getMainSpecFile(project);
    } else {
      return Optional.empty();
    }
  }

  public boolean isMainSpecFile(final VirtualFile virtualFile, final Project project) {
    return ApplicationManager.getApplication().getService(OpenApiIndexService.class).isMainSpecFile(virtualFile, project)
        || ApplicationManager.getApplication().getService(SwaggerIndexService.class)
            .isMainSpecFile(virtualFile, project);
  }

  public boolean isPartialSpecFile(final VirtualFile virtualFile, final Project project) {
    return ApplicationManager.getApplication().getService(OpenApiIndexService.class)
            .isPartialSpecFile(virtualFile, project)
        || ApplicationManager.getApplication().getService(SwaggerIndexService.class)
            .isPartialSpecFile(virtualFile, project);
  }

  public boolean isIndexReady(final Project project) {
    return !ApplicationManager.getApplication().getService(DumbService.class).isDumb(project);
  }
}
