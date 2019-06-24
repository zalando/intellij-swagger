package org.zalando.intellij.swagger.reference.contributor;

import com.intellij.patterns.ElementPattern;
import com.intellij.patterns.StandardPatterns;
import com.intellij.patterns.StringPattern;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReference;
import com.intellij.psi.PsiReferenceContributor;
import com.intellij.psi.PsiReferenceProvider;
import com.intellij.util.ProcessingContext;
import java.util.Optional;
import org.jetbrains.annotations.NotNull;
import org.zalando.intellij.swagger.StringUtils;
import org.zalando.intellij.swagger.reference.FileReference;
import org.zalando.intellij.swagger.reference.LocalReference;
import org.zalando.intellij.swagger.reference.SchemaNameReference;

abstract class ReferenceContributor extends PsiReferenceContributor {

  private static final StringPattern JSON_FILE_NAME_PATTERN =
      StandardPatterns.string().matches("(.)*\\.json(.)*");

  private static final StringPattern YAML_FILE_NAME_PATTERN =
      StandardPatterns.string().matches("(.)*\\.ya?ml(.)*");

  static final ElementPattern FILE_NAME_PATTERN =
      StandardPatterns.or(JSON_FILE_NAME_PATTERN, YAML_FILE_NAME_PATTERN);

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
