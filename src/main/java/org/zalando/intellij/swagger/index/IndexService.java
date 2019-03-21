package org.zalando.intellij.swagger.index;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiFile;
import org.zalando.intellij.swagger.index.openapi.OpenApiIndexService;
import org.zalando.intellij.swagger.index.swagger.SwaggerIndexService;

import java.util.Optional;

public class IndexService {

  private final OpenApiIndexService openApiIndexService = new OpenApiIndexService();
  private final SwaggerIndexService swaggerIndexService = new SwaggerIndexService();

  public Optional<VirtualFile> getMainSpecFile(final PsiFile psiFile) {

    final VirtualFile virtualFile = psiFile.getVirtualFile();
    final Project project = psiFile.getProject();

    if (openApiIndexService.isMainOpenApiFile(virtualFile, project)
        || swaggerIndexService.isMainSwaggerFile(virtualFile, project)) {
      return Optional.of(psiFile.getVirtualFile());
    } else if (openApiIndexService.isPartialOpenApiFile(virtualFile, project)) {
      return openApiIndexService.getMainOpenApiFile(project);
    } else if (swaggerIndexService.isPartialSwaggerFile(virtualFile, project)) {
      return swaggerIndexService.getMainSwaggerFile(project);
    } else {
      return Optional.empty();
    }
  }
}
