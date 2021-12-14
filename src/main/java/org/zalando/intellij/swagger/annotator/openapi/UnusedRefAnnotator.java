package org.zalando.intellij.swagger.annotator.openapi;

import com.intellij.codeInspection.ProblemHighlightType;
import com.intellij.lang.annotation.Annotation;
import com.intellij.lang.annotation.AnnotationHolder;
import com.intellij.lang.annotation.Annotator;
import com.intellij.lang.annotation.HighlightSeverity;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReference;
import com.intellij.psi.search.searches.ReferencesSearch;
import java.util.Optional;
import org.jetbrains.annotations.NotNull;
import org.zalando.intellij.swagger.file.OpenApiFileType;
import org.zalando.intellij.swagger.index.openapi.OpenApiIndexService;
import org.zalando.intellij.swagger.traversal.Traversal;
import org.zalando.intellij.swagger.traversal.path.openapi.PathResolver;
import org.zalando.intellij.swagger.traversal.path.openapi.PathResolverFactory;

public abstract class UnusedRefAnnotator implements Annotator {

  private final Traversal traversal;
  private final OpenApiIndexService openApiIndexService = new OpenApiIndexService();

  UnusedRefAnnotator(Traversal traversal) {
    this.traversal = traversal;
  }

  public abstract PsiElement getPsiElement(PsiElement psiElement);

  public abstract PsiElement getSearchablePsiElement(PsiElement psiElement);

  @Override
  public void annotate(
      @NotNull final PsiElement psiElement, @NotNull final AnnotationHolder annotationHolder) {

    final Optional<OpenApiFileType> maybeFileType = openApiIndexService.getFileType(psiElement);

    maybeFileType.ifPresent(
        fileType -> {
          final PathResolver pathResolver = PathResolverFactory.fromOpenApiFileType(fileType);
          traversal
              .getKeyNameIfKey(psiElement)
              .ifPresent(
                  keyName -> {
                    final PsiElement currentElement = getPsiElement(psiElement);
                    PsiElement searchableCurrentElement = getSearchablePsiElement(psiElement);

                    if (pathResolver.isSchema(currentElement)) {
                      warn(annotationHolder, searchableCurrentElement, "Schema is never used");
                    } else if (pathResolver.isResponse(currentElement)) {
                      warn(annotationHolder, searchableCurrentElement, "Response is never used");
                    } else if (pathResolver.isParameter(currentElement)) {
                      warn(annotationHolder, searchableCurrentElement, "Parameter is never used");
                    } else if (pathResolver.isExample(currentElement)) {
                      warn(annotationHolder, searchableCurrentElement, "Example is never used");
                    } else if (pathResolver.isRequestBody(currentElement)) {
                      warn(
                          annotationHolder, searchableCurrentElement, "Request body is never used");
                    } else if (pathResolver.isHeader(currentElement)) {
                      warn(annotationHolder, searchableCurrentElement, "Header is never used");
                    } else if (pathResolver.isLink(currentElement)) {
                      warn(annotationHolder, searchableCurrentElement, "Link is never used");
                    } else if (pathResolver.isCallback(currentElement)) {
                      warn(annotationHolder, searchableCurrentElement, "Callback is never used");
                    }
                  });
        });
  }

  private void warn(
      final AnnotationHolder annotationHolder,
      final PsiElement searchableCurrentElement,
      final String warning) {
    final PsiReference first = ReferencesSearch.search(searchableCurrentElement).findFirst();

    if (first == null) {
      Annotation annotation =
          annotationHolder
              .newAnnotation(HighlightSeverity.WEAK_WARNING, warning)
              .createAnnotation();
      annotation.setHighlightType(ProblemHighlightType.LIKE_UNUSED_SYMBOL);
    }
  }
}
