package org.zalando.intellij.swagger.index;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiFile;
import java.util.Optional;
import org.zalando.intellij.swagger.index.openapi.OpenApiIndexService;
import org.zalando.intellij.swagger.index.swagger.SwaggerIndexService;

public class IndexService {

  private final OpenApiIndexService openApiIndexService;
  private final SwaggerIndexService swaggerIndexService;

  public IndexService() {
    this(new OpenApiIndexService(), new SwaggerIndexService());
  }

  public IndexService(
      final OpenApiIndexService openApiIndexService,
      final SwaggerIndexService swaggerIndexService) {
    this.openApiIndexService = openApiIndexService;
    this.swaggerIndexService = swaggerIndexService;
  }

  public Optional<VirtualFile> getMainSpecFile(final PsiFile psiFile) {

    final VirtualFile virtualFile = psiFile.getVirtualFile();
    final Project project = psiFile.getProject();

    if (isMainSpecFile(virtualFile, project)) {
      return Optional.of(psiFile.getVirtualFile());
    } else if (openApiIndexService.isPartialOpenApiFile(virtualFile, project)) {
      return openApiIndexService.getMainOpenApiFile(project);
    } else if (swaggerIndexService.isPartialSwaggerFile(virtualFile, project)) {
      return swaggerIndexService.getMainSwaggerFile(project);
    } else {
      return Optional.empty();
    }
  }

  public boolean isMainSpecFile(final VirtualFile virtualFile, final Project project) {
    return openApiIndexService.isMainOpenApiFile(virtualFile, project)
        || swaggerIndexService.isMainSwaggerFile(virtualFile, project);
  }
}
