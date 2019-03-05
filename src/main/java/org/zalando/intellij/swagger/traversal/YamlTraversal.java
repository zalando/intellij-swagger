package org.zalando.intellij.swagger.traversal;

import com.google.common.collect.ImmutableList;
import com.intellij.codeInsight.completion.InsertHandler;
import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import java.util.*;
import java.util.stream.Collectors;
import org.jetbrains.yaml.YAMLUtil;
import org.jetbrains.yaml.psi.*;
import org.zalando.intellij.swagger.StringUtils;
import org.zalando.intellij.swagger.completion.field.model.common.Field;
import org.zalando.intellij.swagger.completion.value.model.common.Value;
import org.zalando.intellij.swagger.insert.YamlInsertFieldHandler;
import org.zalando.intellij.swagger.insert.YamlInsertValueHandler;

public class YamlTraversal extends Traversal {

  @Override
  public Optional<String> getKeyNameIfKey(final PsiElement psiElement) {
    return getAsYamlKeyValue(psiElement).map(YAMLKeyValue::getKeyText);
  }

  private Optional<YAMLKeyValue> getAsYamlKeyValue(final PsiElement psiElement) {
    return Optional.ofNullable(psiElement)
        .map(PsiElement::getParent)
        .filter(el -> el instanceof YAMLKeyValue)
        .map(YAMLKeyValue.class::cast)
        .filter(value -> value.getKey() == psiElement);
  }

  @Override
  public Optional<String> getKeyNameOfObject(final PsiElement psiElement) {
    return Optional.of(psiElement)
        .filter(el -> el instanceof YAMLKeyValue)
        .map(YAMLKeyValue.class::cast)
        .map(YAMLKeyValue::getName);
  }

  @Override
  public Optional<String> getParentKeyName(final PsiElement psiElement) {
    return getNthOfType(psiElement, 1, YAMLKeyValue.class)
        .map(YAMLKeyValue::getName)
        .map(StringUtils::removeAllQuotes);
  }

  @Override
  public List<String> getTagNames(final PsiFile psiFile) {
    return getTags(psiFile).stream().map(PsiElement::getText).collect(Collectors.toList());
  }

  private List<PsiElement> getTags(final PsiFile psiFile) {
    return getRootChildrenOfType(psiFile, YAMLKeyValue.class)
        .stream()
        .filter(yamlKeyValue -> "tags".equals(yamlKeyValue.getName()))
        .map(YAMLKeyValue::getValue)
        .map(YAMLPsiElement::getYAMLElements)
        .flatMap(Collection::stream)
        .filter(el -> el instanceof YAMLSequenceItem)
        .map(YAMLSequenceItem.class::cast)
        .map(YAMLSequenceItem::getYAMLElements)
        .flatMap(Collection::stream)
        .filter(el -> el instanceof YAMLMapping)
        .map(YAMLMapping.class::cast)
        .map(yamlMapping -> yamlMapping.getKeyValueByKey("name"))
        .map(YAMLKeyValue::getValue)
        .filter(Objects::nonNull)
        .collect(Collectors.toList());
  }

  private <T extends PsiElement> List<T> getRootChildrenOfType(
      final PsiFile psiFile, final Class<T> type) {
    final PsiElement[] children =
        getRootMapping(psiFile).map(PsiElement::getChildren).orElse(new PsiElement[0]);

    return Arrays.stream(children)
        .filter(child -> type.isAssignableFrom(child.getClass()))
        .map(type::cast)
        .collect(Collectors.toList());
  }

  @Override
  public void addReferenceDefinition(final String path, PsiElement anchorPsiElement) {
    YAMLUtil.createI18nRecord(
        ((YAMLFile) anchorPsiElement.getContainingFile()), path.split("/"), "");
  }

  private Optional<YAMLMapping> getRootMapping(final PsiFile psiFile) {
    return Arrays.stream(psiFile.getChildren())
        .filter(el -> el instanceof YAMLDocument)
        .findFirst()
        .map(PsiElement::getChildren)
        .map(
            children ->
                Arrays.stream(children)
                    .filter(el -> el instanceof YAMLMapping)
                    .map(YAMLMapping.class::cast)
                    .findFirst()
                    .orElse(null));
  }

  @Override
  public boolean isUniqueArrayStringValue(final String value, final PsiElement psiElement) {
    return Optional.ofNullable(psiElement.getParent())
        .map(PsiElement::getParent)
        .map(PsiElement::getParent)
        .filter(el -> el instanceof YAMLSequence)
        .map(el -> Arrays.asList(el.getChildren()))
        .map(children -> children.stream().filter(c -> c instanceof YAMLSequenceItem))
        .map(childrenStream -> childrenStream.map(YAMLSequenceItem.class::cast))
        .map(
            childrenStream ->
                childrenStream.noneMatch(
                    item ->
                        item.getValue() != null
                            && value.equals(
                                StringUtils.removeAllQuotes(item.getValue().getText()))))
        .orElse(true);
  }

  @Override
  public InsertHandler<LookupElement> createInsertFieldHandler(final Field field) {
    return new YamlInsertFieldHandler(field);
  }

  @Override
  public InsertHandler<LookupElement> createInsertValueHandler(final Value value) {
    return new YamlInsertValueHandler();
  }

  @Override
  public List<String> getSecurityScopesIfOAuth2(final PsiElement securityDefinitionItem) {
    final List<YAMLKeyValue> properties = getChildProperties(securityDefinitionItem);

    final boolean isOAuth2 =
        properties
            .stream()
            .anyMatch(
                prop -> {
                  final Optional<String> value =
                      Optional.ofNullable(prop.getValue())
                          .map(YAMLValue::getText)
                          .map(StringUtils::removeAllQuotes);
                  return "type".equals(prop.getName()) && Optional.of("oauth2").equals(value);
                });

    if (isOAuth2) {
      return properties
          .stream()
          .filter(prop -> "scopes".equals(prop.getName()))
          .map(this::getChildProperties)
          .flatMap(Collection::stream)
          .map(YAMLKeyValue::getName)
          .collect(Collectors.toList());
    }

    return ImmutableList.of();
  }

  private List<YAMLKeyValue> getChildProperties(final PsiElement element) {
    return toYamlKeyValue(element)
        .map(YAMLKeyValue::getValue)
        .map(YAMLValue::getChildren)
        .map(Arrays::asList)
        .map(children -> children.stream().map(this::toYamlKeyValue))
        .map(
            children ->
                children
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .collect(Collectors.toList()))
        .orElse(ImmutableList.of());
  }

  private Optional<YAMLKeyValue> toYamlKeyValue(final PsiElement psiElement) {
    return psiElement instanceof YAMLKeyValue
        ? Optional.of((YAMLKeyValue) psiElement)
        : Optional.empty();
  }

  @Override
  public Optional<String> extractSecurityNameFromSecurityItem(final PsiElement psiElement) {
    return Optional.of(psiElement)
        .map(PsiElement::getChildren)
        .map(children -> children.length > 0 ? children[0] : null)
        .map(PsiElement::getChildren)
        .map(children -> children.length > 0 ? children[0] : null)
        .filter(el -> el instanceof YAMLKeyValue)
        .map(YAMLKeyValue.class::cast)
        .map(YAMLKeyValue::getName);
  }
}
