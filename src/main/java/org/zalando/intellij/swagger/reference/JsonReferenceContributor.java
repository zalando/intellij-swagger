package org.zalando.intellij.swagger.reference;

import static com.intellij.patterns.PlatformPatterns.psiElement;

import com.intellij.json.JsonLanguage;
import com.intellij.json.psi.JsonLiteral;
import com.intellij.json.psi.JsonProperty;
import com.intellij.patterns.PsiElementPattern;
import com.intellij.patterns.StandardPatterns;
import com.intellij.patterns.StringPattern;
import com.intellij.psi.PsiReferenceRegistrar;
import org.jetbrains.annotations.NotNull;

public class JsonReferenceContributor extends ReferenceContributor {

  private static final StringPattern URL_PATTERN =
      StandardPatterns.string().matches("^\"https://(.)*");

  private static final StringPattern JSON_FILE_NAME_PATTERN =
      StandardPatterns.string().matches("(.)*.json(.)*");

  @Override
  public void registerReferenceProviders(@NotNull final PsiReferenceRegistrar registrar) {
    registrar.registerReferenceProvider(localDefinitionsPattern(), createLocalReferenceProvider());
    registrar.registerReferenceProvider(
        mappingSchemaNamePattern(), createSchemaNameReferenceProvider());

    registrar.registerReferenceProvider(filePattern(), createFileReferenceProvider());
  }

  private PsiElementPattern.Capture<JsonLiteral> localDefinitionsPattern() {
    return psiElement(JsonLiteral.class)
        .andOr(
            psiElement()
                .withParent(psiElement(JsonProperty.class).withName(SwaggerConstants.REF_KEY)),
            psiElement().withSuperParent(3, psiElement(JsonProperty.class).withName("mapping")))
        .withText(StandardPatterns.string().contains(SwaggerConstants.REFERENCE_PREFIX))
        .withoutText(JSON_FILE_NAME_PATTERN)
        .withoutText(URL_PATTERN)
        .withLanguage(JsonLanguage.INSTANCE);
  }

  private PsiElementPattern.Capture<JsonLiteral> mappingSchemaNamePattern() {
    return psiElement(JsonLiteral.class)
        .withSuperParent(3, psiElement(JsonProperty.class).withName("mapping"))
        .withoutText(StandardPatterns.string().contains(SwaggerConstants.REFERENCE_PREFIX))
        .withoutText(JSON_FILE_NAME_PATTERN)
        .withoutText(URL_PATTERN)
        .withLanguage(JsonLanguage.INSTANCE);
  }

  private PsiElementPattern.Capture<JsonLiteral> filePattern() {
    return psiElement(JsonLiteral.class)
        .withText(JSON_FILE_NAME_PATTERN)
        .withoutText(URL_PATTERN)
        .withLanguage(JsonLanguage.INSTANCE);
  }
}
