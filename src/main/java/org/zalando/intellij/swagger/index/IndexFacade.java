package org.zalando.intellij.swagger.index;

import java.util.Optional;

import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiFile;
import org.zalando.intellij.swagger.index.openapi.OpenApiIndexService;
import org.zalando.intellij.swagger.index.swagger.SwaggerIndexService;
import org.zalando.intellij.swagger.service.intellij.DumbService;

public class IndexFacade {

  public Optional<VirtualFile> getMainSpecFile(final PsiFile psiFile) {
    final VirtualFile virtualFile = psiFile.getVirtualFile();
    final Project project = psiFile.getProject();

    OpenApiIndexService openApiIndexService = ServiceManager.getService(OpenApiIndexService.class);
    SwaggerIndexService swaggerIndexService = ServiceManager.getService(SwaggerIndexService.class);

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
    return ServiceManager.getService(OpenApiIndexService.class).isMainSpecFile(virtualFile, project)
        || ServiceManager.getService(SwaggerIndexService.class).isMainSpecFile(virtualFile, project);
  }

  public boolean isPartialSpecFile(final VirtualFile virtualFile, final Project project) {
    return ServiceManager.getService(OpenApiIndexService.class).isPartialSpecFile(virtualFile, project)
        || ServiceManager.getService(SwaggerIndexService.class).isPartialSpecFile(virtualFile, project);
  }

  public boolean isIndexReady(final Project project) {
    return !ServiceManager.getService(DumbService.class).isDumb(project);
  }
}
