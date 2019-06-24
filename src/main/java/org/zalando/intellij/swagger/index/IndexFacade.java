package org.zalando.intellij.swagger.index;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiFile;
import java.util.Optional;
import org.zalando.intellij.swagger.index.openapi.OpenApiIndexService;
import org.zalando.intellij.swagger.index.swagger.SwaggerIndexService;
import org.zalando.intellij.swagger.service.intellij.DumbService;

public class IndexFacade {

  private final OpenApiIndexService openApiIndexService;
  private final SwaggerIndexService swaggerIndexService;
  private final DumbService dumbService;

  public IndexFacade(
      final OpenApiIndexService openApiIndexService,
      final SwaggerIndexService swaggerIndexService,
      final DumbService dumbService) {
    this.openApiIndexService = openApiIndexService;
    this.swaggerIndexService = swaggerIndexService;
    this.dumbService = dumbService;
  }

  public Optional<VirtualFile> getMainSpecFile(final PsiFile psiFile) {

    final VirtualFile virtualFile = psiFile.getVirtualFile();
    final Project project = psiFile.getProject();

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
    return openApiIndexService.isMainSpecFile(virtualFile, project)
        || swaggerIndexService.isMainSpecFile(virtualFile, project);
  }

  public boolean isPartialSpecFile(final VirtualFile virtualFile, final Project project) {
    return openApiIndexService.isPartialSpecFile(virtualFile, project)
        || swaggerIndexService.isPartialSpecFile(virtualFile, project);
  }

  public boolean isIndexReady(final Project project) {
    return !dumbService.isDumb(project);
  }
}
