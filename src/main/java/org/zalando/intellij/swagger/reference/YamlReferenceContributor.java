package org.zalando.intellij.swagger.reference;

import static com.intellij.patterns.PlatformPatterns.psiElement;

import com.intellij.patterns.PsiElementPattern;
import com.intellij.patterns.StandardPatterns;
import com.intellij.patterns.StringPattern;
import com.intellij.psi.PsiReferenceRegistrar;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.yaml.YAMLLanguage;
import org.jetbrains.yaml.psi.YAMLKeyValue;
import org.jetbrains.yaml.psi.YAMLQuotedText;
import org.jetbrains.yaml.psi.YAMLValue;

public class YamlReferenceContributor extends ReferenceContributor {
  private static final StringPattern URL_PATTERN =
      StandardPatterns.string().matches("^(\"|\')?https://(.)*");

  private static final StringPattern YAML_FILE_NAME_PATTERN =
      StandardPatterns.string().matches("(.)*.ya?ml(.)*");

  @Override
  public void registerReferenceProviders(@NotNull final PsiReferenceRegistrar registrar) {
    registrar.registerReferenceProvider(localDefinitionsPattern(), createLocalReferenceProvider());
    registrar.registerReferenceProvider(
        mappingSchemaNamePattern(), createSchemaNameReferenceProvider());

    registrar.registerReferenceProvider(filePattern(), createFileReferenceProvider());
  }

  private PsiElementPattern.Capture<YAMLQuotedText> localDefinitionsPattern() {
    return psiElement(YAMLQuotedText.class)
        .andOr(
            psiElement()
                .withParent(psiElement(YAMLKeyValue.class).withName(SwaggerConstants.REF_KEY)),
            psiElement().withSuperParent(3, psiElement(YAMLKeyValue.class).withName("mapping")))
        .withText(StandardPatterns.string().contains(SwaggerConstants.REFERENCE_PREFIX))
        .withoutText(YAML_FILE_NAME_PATTERN)
        .withoutText(URL_PATTERN)
        .withLanguage(YAMLLanguage.INSTANCE);
  }

  private PsiElementPattern.Capture<YAMLQuotedText> mappingSchemaNamePattern() {
    return psiElement(YAMLQuotedText.class)
        .withSuperParent(3, psiElement(YAMLKeyValue.class).withName("mapping"))
        .withoutText(StandardPatterns.string().contains(SwaggerConstants.REFERENCE_PREFIX))
        .withoutText(YAML_FILE_NAME_PATTERN)
        .withoutText(URL_PATTERN)
        .withLanguage(YAMLLanguage.INSTANCE);
  }

  private PsiElementPattern.Capture<YAMLValue> filePattern() {
    return psiElement(YAMLValue.class)
        .withText(YAML_FILE_NAME_PATTERN)
        .withoutText(URL_PATTERN)
        .withLanguage(YAMLLanguage.INSTANCE);
  }
}
