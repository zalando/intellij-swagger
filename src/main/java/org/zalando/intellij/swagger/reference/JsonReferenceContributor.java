package org.zalando.intellij.swagger.reference;

import static com.intellij.patterns.PlatformPatterns.psiElement;

import com.intellij.json.JsonLanguage;
import com.intellij.json.psi.JsonLiteral;
import com.intellij.json.psi.JsonObject;
import com.intellij.json.psi.JsonProperty;
import com.intellij.json.psi.JsonValue;
import com.intellij.patterns.PsiElementPattern;
import com.intellij.patterns.StandardPatterns;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReferenceRegistrar;
import org.jetbrains.annotations.NotNull;
import org.zalando.intellij.swagger.file.FileConstants;
import org.zalando.intellij.swagger.traversal.JsonTraversal;

public class JsonReferenceContributor extends ReferenceContributor {

  public JsonReferenceContributor() {
    super(new JsonTraversal());
  }

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
        .withoutText(StandardPatterns.string().contains(FileConstants.JSON_FILE_NAME_SUFFIX))
        .withoutText(StandardPatterns.string().matches("^\"https://(.)*"))
        .withLanguage(JsonLanguage.INSTANCE);
  }

  private PsiElementPattern.Capture<JsonLiteral> mappingSchemaNamePattern() {
    return psiElement(JsonLiteral.class)
        .withSuperParent(3, psiElement(JsonProperty.class).withName("mapping"))
        .withoutText(StandardPatterns.string().contains(SwaggerConstants.REFERENCE_PREFIX))
        .withoutText(StandardPatterns.string().contains(FileConstants.JSON_FILE_NAME_SUFFIX))
        .withoutText(StandardPatterns.string().matches("^\"https://(.)*"))
        .withLanguage(JsonLanguage.INSTANCE);
  }

  private PsiElementPattern.Capture<JsonLiteral> filePattern() {
    return psiElement(JsonLiteral.class)
        .withText(StandardPatterns.string().matches("(.)*.json(.)*"))
        .withoutText(StandardPatterns.string().matches("^\"https://(.)*"))
        .withLanguage(JsonLanguage.INSTANCE);
  }

  private <T extends PsiElement> PsiElementPattern.Capture<T> swagger(
      final PsiElementPattern.Capture<T> pattern) {
    return pattern.inside(
        psiElement(JsonObject.class).withChild(psiElement().withName(SwaggerConstants.SWAGGER)));
  }
}
