package org.zalando.intellij.swagger.annotator.swagger;

import com.intellij.codeInspection.ProblemHighlightType;
import com.intellij.lang.annotation.Annotation;
import com.intellij.lang.annotation.AnnotationHolder;
import com.intellij.lang.annotation.Annotator;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReference;
import com.intellij.psi.search.searches.ReferencesSearch;
import java.util.Optional;
import org.jetbrains.annotations.NotNull;
import org.zalando.intellij.swagger.file.SwaggerFileType;
import org.zalando.intellij.swagger.index.swagger.SwaggerIndexService;
import org.zalando.intellij.swagger.traversal.Traversal;
import org.zalando.intellij.swagger.traversal.path.swagger.PathResolver;
import org.zalando.intellij.swagger.traversal.path.swagger.PathResolverFactory;

public abstract class UnusedRefAnnotator implements Annotator {

  private final Traversal traversal;
  private final SwaggerIndexService swaggerIndexService = new SwaggerIndexService();

  UnusedRefAnnotator(Traversal traversal) {
    this.traversal = traversal;
  }

  public abstract PsiElement getPsiElement(PsiElement psiElement);

  public abstract PsiElement getSearchablePsiElement(PsiElement psiElement);

  @Override
  public void annotate(
      @NotNull final PsiElement psiElement, @NotNull final AnnotationHolder annotationHolder) {

    final Optional<SwaggerFileType> maybeFileType = swaggerIndexService.getFileType(psiElement);

    maybeFileType.ifPresent(
        fileType -> {
          final PathResolver pathResolver = PathResolverFactory.fromSwaggerFileType(fileType);

          traversal
              .getKeyNameIfKey(psiElement)
              .ifPresent(
                  keyName -> {
                    final PsiElement currentElement = getPsiElement(psiElement);
                    PsiElement searchableCurrentElement = getSearchablePsiElement(psiElement);

                    if (pathResolver.isDefinition(currentElement)) {
                      warn(
                          psiElement,
                          annotationHolder,
                          searchableCurrentElement,
                          "Definition is never used");
                    } else if (pathResolver.isParameter(currentElement)) {
                      warn(
                          psiElement,
                          annotationHolder,
                          searchableCurrentElement,
                          "Parameter is never used");
                    } else if (pathResolver.isResponse(currentElement)) {
                      warn(
                          psiElement,
                          annotationHolder,
                          searchableCurrentElement,
                          "Response is never used");
                    }
                  });
        });
  }

  private void warn(
      final PsiElement psiElement,
      final AnnotationHolder annotationHolder,
      final PsiElement searchableCurrentElement,
      final String warning) {
    final PsiReference first = ReferencesSearch.search(searchableCurrentElement).findFirst();

    if (first == null) {
      Annotation annotation = annotationHolder.createWeakWarningAnnotation(psiElement, warning);
      annotation.setHighlightType(ProblemHighlightType.LIKE_UNUSED_SYMBOL);
    }
  }
}
