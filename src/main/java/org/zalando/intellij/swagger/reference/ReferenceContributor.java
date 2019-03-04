package org.zalando.intellij.swagger.reference;

import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReference;
import com.intellij.psi.PsiReferenceContributor;
import com.intellij.psi.PsiReferenceProvider;
import com.intellij.util.ProcessingContext;
import org.jetbrains.annotations.NotNull;
import org.zalando.intellij.swagger.StringUtils;
import org.zalando.intellij.swagger.traversal.Traversal;

import java.util.Optional;

abstract class ReferenceContributor extends PsiReferenceContributor {

  final Traversal traversal;

  ReferenceContributor(Traversal traversal) {
    this.traversal = traversal;
  }

  PsiReferenceProvider createLocalReferenceProvider() {
    return new PsiReferenceProvider() {
      @NotNull
      @Override
      public PsiReference[] getReferencesByElement(
          @NotNull PsiElement element, @NotNull ProcessingContext context) {
        return Optional.ofNullable(element.getText())
            .map(
                text ->
                    new PsiReference[] {
                      new LocalReference(element, StringUtils.removeAllQuotes(text))
                    })
            .orElse(LocalReference.EMPTY_ARRAY);
      }
    };
  }

  PsiReferenceProvider createSchemaNameReferenceProvider() {
    return new PsiReferenceProvider() {
      @NotNull
      @Override
      public PsiReference[] getReferencesByElement(
          @NotNull PsiElement element, @NotNull ProcessingContext context) {
        return Optional.ofNullable(element.getText())
            .map(
                text ->
                    new PsiReference[] {
                      new SchemaNameReference(element, StringUtils.removeAllQuotes(text))
                    })
            .orElse(SchemaNameReference.EMPTY_ARRAY);
      }
    };
  }

  PsiReferenceProvider createFileReferenceProvider() {
    return new PsiReferenceProvider() {
      @NotNull
      @Override
      public PsiReference[] getReferencesByElement(
          @NotNull PsiElement element, @NotNull ProcessingContext context) {
        return Optional.ofNullable(element.getText())
            .map(
                text ->
                    new PsiReference[] {
                      new FileReference(element, StringUtils.removeAllQuotes(text))
                    })
            .orElse(FileReference.EMPTY_ARRAY);
      }
    };
  }
}
