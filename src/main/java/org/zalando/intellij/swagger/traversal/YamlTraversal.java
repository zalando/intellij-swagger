package org.zalando.intellij.swagger.traversal;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.intellij.codeInsight.completion.InsertHandler;
import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.util.containers.ContainerUtil;
import java.util.*;
import java.util.stream.Collectors;
import org.jetbrains.yaml.YAMLElementGenerator;
import org.jetbrains.yaml.YAMLUtil;
import org.jetbrains.yaml.psi.*;
import org.jetbrains.yaml.psi.impl.YAMLPlainTextImpl;
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

  public boolean isAnchorKey(final PsiElement psiElement) {
    return getAsYamlKeyValue(psiElement)
        .map(YAMLKeyValue::getValue)
        .map(YAMLValue::getText)
        .filter(value -> value.startsWith("&"))
        .isPresent();
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

  @Override
  public List<PsiElement> getTags(final PsiFile psiFile) {
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

  private boolean hasRootKey(final String keyName, final PsiFile psiFile) {
    return getRootChildrenOfType(psiFile, YAMLKeyValue.class)
        .stream()
        .anyMatch(yamlKeyValue -> keyName.equals(yamlKeyValue.getName()));
  }

  public Optional<? extends PsiElement> getRootChildByName(
      final String keyName, final PsiFile psiFile) {
    return getRootChildrenOfType(psiFile, YAMLKeyValue.class)
        .stream()
        .filter(yamlKeyValue -> keyName.equals(yamlKeyValue.getName()))
        .findFirst();
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

  private Optional<YAMLMapping> getYamlMapping(final PsiElement psiElement) {
    final List<PsiElement> children =
        Optional.of(psiElement)
            .map(PsiElement::getChildren)
            .map(Arrays::asList)
            .orElse(new ArrayList<>());

    return children
        .stream()
        .filter(child -> child instanceof YAMLMapping)
        .map(YAMLMapping.class::cast)
        .findFirst();
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

  private Optional<PsiElement> addProperty(YAMLMapping yamlMapping, YAMLKeyValue yamlKeyValue) {
    final List<YAMLKeyValue> keyValues = Lists.newArrayList(yamlMapping.getKeyValues());

    return Optional.ofNullable(ContainerUtil.getLastItem(keyValues))
        .map(
            lastKeyValue -> {
              final PsiElement addedKeyValue = yamlMapping.addAfter(yamlKeyValue, lastKeyValue);
              yamlMapping.addBefore(
                  new YAMLElementGenerator(yamlMapping.getProject()).createEol(), addedKeyValue);
              return addedKeyValue;
            });
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

  @Override
  public PsiElement extractObjectForValidation(final PsiElement psiElement) {
    final Optional<PsiElement> thirdParent =
        Optional.ofNullable(psiElement.getParent())
            .map(PsiElement::getParent)
            .map(PsiElement::getParent);

    if (thirdParent.isPresent() && thirdParent.get() instanceof YAMLSequenceItem) {
      return ((YAMLSequenceItem) thirdParent.get()).getValue();
    } else if (thirdParent.isPresent() && thirdParent.get() instanceof YAMLKeyValue) {
      return ((YAMLKeyValue) thirdParent.get()).getKey();
    } else {
      return psiElement.getParent();
    }
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
  public boolean isValue(final PsiElement psiElement) {
    return !(org.apache.commons.lang.StringUtils.isBlank(psiElement.getText())
            || psiElement instanceof YAMLKeyValue)
        && Optional.ofNullable(psiElement.getParent())
            .map(PsiElement::getParent)
            .filter(el -> el instanceof YAMLKeyValue)
            .map(YAMLKeyValue.class::cast)
            .filter(el -> el.getValue() == psiElement.getParent())
            .isPresent();
  }

  @Override
  public boolean isArrayStringElement(final PsiElement psiElement) {
    return psiElement.getParent() instanceof YAMLPlainTextImpl
        && psiElement.getParent().getParent() instanceof YAMLSequenceItem;
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

  public boolean isChildOfAnchorKey(final PsiElement psiElement) {
    if (isAnchorKey(extractObjectForValidation(psiElement))) {
      return true;
    }

    final PsiElement parent = psiElement.getParent();

    return parent != null && isChildOfAnchorKey(parent);
  }
}
