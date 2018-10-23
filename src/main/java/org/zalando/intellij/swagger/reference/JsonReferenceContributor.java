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

    registrar.registerReferenceProvider(filePattern(), createFileReferenceProvider());
    registrar.registerReferenceProvider(tagsPattern(), createTagsReferenceProvider());
  }

  private PsiElementPattern.Capture<JsonLiteral> localDefinitionsPattern() {
    return psiElement(JsonLiteral.class)
        .withParent(psiElement(JsonProperty.class).withName(SwaggerConstants.REF_KEY))
        .withText(StandardPatterns.string().contains(SwaggerConstants.REFERENCE_PREFIX))
        .andNot(StandardPatterns.string().contains(FileConstants.JSON_FILE_NAME_SUFFIX))
        .withLanguage(JsonLanguage.INSTANCE);
  }

  private PsiElementPattern.Capture<JsonLiteral> filePattern() {
    return psiElement(JsonLiteral.class)
        .withText(StandardPatterns.string().matches("(.)*.json(.)*"))
        .withLanguage(JsonLanguage.INSTANCE);
  }

  private PsiElementPattern.Capture<JsonValue> tagsPattern() {
    return swagger(
        psiElement(JsonValue.class)
            .inside(psiElement(JsonProperty.class).withName(SwaggerConstants.TAGS_KEY))
            .withLanguage(JsonLanguage.INSTANCE));
  }

  private <T extends PsiElement> PsiElementPattern.Capture<T> swagger(
      final PsiElementPattern.Capture<T> pattern) {
    return pattern.inside(
        psiElement(JsonObject.class).withChild(psiElement().withName(SwaggerConstants.SWAGGER)));
  }
}
