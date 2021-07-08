package org.zalando.intellij.swagger.documentation.openapi;

import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import java.util.Optional;
import java.util.stream.Stream;
import org.jetbrains.annotations.Nullable;
import org.zalando.intellij.swagger.documentation.ApiDocumentProvider;
import org.zalando.intellij.swagger.file.OpenApiFileType;
import org.zalando.intellij.swagger.index.openapi.OpenApiIndexService;
import org.zalando.intellij.swagger.traversal.path.openapi.PathResolver;
import org.zalando.intellij.swagger.traversal.path.openapi.PathResolverFactory;

public class OpenApiDocumentationProvider extends ApiDocumentProvider {

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

              final Optional<OpenApiFileType> maybeFileType =
                  ServiceManager.getService(OpenApiIndexService.class)
                      .getFileType(project, virtualFile);

              return maybeFileType.map(
                  openApiFileType -> {
                    final PathResolver pathResolver =
                        PathResolverFactory.fromOpenApiFileType(openApiFileType);

                    if (pathResolver.childOfSchema(targetElement)) {
                      return handleSchemaReference(targetElement, originalElement);
                    } else if (pathResolver.childOfResponse(targetElement)) {
                      return handleResponseReference(targetElement);
                    } else if (pathResolver.childOfParameters(targetElement)) {
                      return handleParameterReference(targetElement);
                    } else if (pathResolver.childOfExample(targetElement)) {
                      return handleExampleReference(targetElement);
                    } else if (pathResolver.childOfRequestBody(targetElement)) {
                      return handleRequestBodyReference(targetElement);
                    } else if (pathResolver.childOfHeader(targetElement)) {
                      return handleHeaderReference(targetElement);
                    } else if (pathResolver.childOfLink(targetElement)) {
                      return handleLinkReference(targetElement);
                    }
                    return null;
                  });
            })
        .orElse(null);
  }

  private String handleLinkReference(final PsiElement targetElement) {
    final Optional<String> description = getUnquotedFieldValue(targetElement, "description");

    return toHtml(Stream.of(description));
  }

  private String handleHeaderReference(final PsiElement targetElement) {
    final Optional<String> description = getUnquotedFieldValue(targetElement, "description");

    return toHtml(Stream.of(description));
  }

  private String handleRequestBodyReference(final PsiElement targetElement) {
    final Optional<String> description = getUnquotedFieldValue(targetElement, "description");

    return toHtml(Stream.of(description));
  }

  private String handleExampleReference(final PsiElement targetElement) {
    final Optional<String> summary = getUnquotedFieldValue(targetElement, "summary");
    final Optional<String> description = getUnquotedFieldValue(targetElement, "description");

    return toHtml(Stream.of(summary, description));
  }
}
