package org.zalando.intellij.swagger.documentation.swagger;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import java.util.Optional;
import org.jetbrains.annotations.Nullable;
import org.zalando.intellij.swagger.documentation.ApiDocumentProvider;
import org.zalando.intellij.swagger.file.SwaggerFileType;
import org.zalando.intellij.swagger.index.swagger.SwaggerIndexService;
import org.zalando.intellij.swagger.traversal.path.swagger.PathResolver;
import org.zalando.intellij.swagger.traversal.path.swagger.PathResolverFactory;

public class SwaggerDocumentationProvider extends ApiDocumentProvider {

  private final SwaggerIndexService swaggerIndexService = new SwaggerIndexService();

  @Nullable
  @Override
  public String getQuickNavigateInfo(
      final PsiElement targetElement, final PsiElement originalElement) {
    Optional<PsiFile> psiFile =
        Optional.ofNullable(targetElement).map(PsiElement::getContainingFile);

    final Optional<VirtualFile> maybeVirtualFile = psiFile.map(PsiFile::getVirtualFile);
    final Optional<Project> maybeProject = psiFile.map(PsiFile::getProject);

    return maybeVirtualFile
        .flatMap(
            virtualFile -> {
              final Project project = maybeProject.get();

              final Optional<SwaggerFileType> maybeFileType =
                  swaggerIndexService.getFileType(project, virtualFile);

              return maybeFileType.map(
                  swaggerFileType -> {
                    final PathResolver pathResolver =
                        PathResolverFactory.fromSwaggerFileType(swaggerFileType);

                    if (pathResolver.childOfSchema(targetElement)) {
                      return handleSchemaReference(targetElement, originalElement);
                    } else if (pathResolver.childOfResponseDefinition(targetElement)) {
                      return handleResponseReference(targetElement);
                    } else if (pathResolver.childOfParameterDefinition(targetElement)) {
                      return handleParameterReference(targetElement);
                    }
                    return null;
                  });
            })
        .orElse(null);
  }
}
