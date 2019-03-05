package org.zalando.intellij.swagger.traversal;

import com.google.common.collect.ImmutableList;
import com.intellij.codeInsight.completion.InsertHandler;
import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.json.psi.*;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import java.util.*;
import java.util.stream.Collectors;
import org.jetbrains.annotations.NotNull;
import org.zalando.intellij.swagger.StringUtils;
import org.zalando.intellij.swagger.completion.field.model.common.Field;
import org.zalando.intellij.swagger.completion.value.model.common.Value;
import org.zalando.intellij.swagger.insert.JsonInsertFieldHandler;
import org.zalando.intellij.swagger.insert.JsonInsertValueHandler;
import org.zalando.intellij.swagger.traversal.path.PathFinder;

public class JsonTraversal extends Traversal {

  public boolean isKey(final PsiElement psiElement) {
    return Optional.ofNullable(psiElement.getParent())
        .filter(JsonPsiUtil::isPropertyKey)
        .isPresent();
  }

  @Override
  public Optional<String> getKeyNameIfKey(final PsiElement psiElement) {
    return isKey(psiElement)
        ? Optional.of(StringUtils.removeAllQuotes(psiElement.getParent().getText()))
        : Optional.empty();
  }

  @Override
  public Optional<String> getKeyNameOfObject(final PsiElement psiElement) {
    return Optional.of(psiElement)
        .filter(el -> el instanceof JsonProperty)
        .map(JsonProperty.class::cast)
        .map(JsonProperty::getName);
  }

  @Override
  public Optional<String> getParentKeyName(final PsiElement psiElement) {
    return getNthOfType(psiElement, 1, JsonProperty.class)
        .map(JsonProperty::getName)
        .map(StringUtils::removeAllQuotes);
  }

  @Override
  public boolean isValue(final PsiElement psiElement) {
    final PsiElement firstParent = psiElement.getParent();
    return Optional.ofNullable(firstParent)
            .map(PsiElement::getParent)
            .filter(jsonPropertyCandidate -> jsonPropertyCandidate instanceof JsonProperty)
            .map(JsonProperty.class::cast)
            .map(JsonProperty::getValue)
            .filter(nameElement -> nameElement == firstParent)
            .isPresent()
        && !(psiElement instanceof JsonProperty);
  }

  @Override
  public boolean isArrayStringElement(final PsiElement psiElement) {
    return psiElement.getParent() instanceof JsonLiteral
        && psiElement.getParent().getParent() instanceof JsonArray;
  }

  @Override
  public Optional<String> extractSecurityNameFromSecurityItem(final PsiElement psiElement) {
    return Optional.of(psiElement)
        .map(PsiElement::getChildren)
        .map(children -> children.length > 0 ? children[0] : null)
        .filter(child -> child instanceof JsonProperty)
        .map(JsonProperty.class::cast)
        .map(JsonProperty::getName);
  }

  private boolean hasRootKey(final String propertyName, final PsiFile psiFile) {
    return getRootChildrenOfType(psiFile, JsonProperty.class)
        .stream()
        .anyMatch(jsonProperty -> propertyName.equals(jsonProperty.getName()));
  }

  public Optional<? extends PsiElement> getRootChildByName(
      final String propertyName, final PsiFile psiFile) {
    return getRootChildrenOfType(psiFile, JsonProperty.class)
        .stream()
        .filter(jsonProperty -> propertyName.equals(jsonProperty.getName()))
        .findFirst();
  }

  private <T extends PsiElement> List<T> getRootChildrenOfType(
      final PsiFile psiFile, final Class<T> type) {
    final PsiElement[] children =
        getRootObject(psiFile).map(PsiElement::getChildren).orElse(new PsiElement[0]);

    return Arrays.stream(children)
        .filter(child -> type.isAssignableFrom(child.getClass()))
        .map(type::cast)
        .collect(Collectors.toList());
  }

  @Override
  public List<String> getTagNames(final PsiFile psiFile) {
    return getTags(psiFile)
        .stream()
        .map(PsiElement::getText)
        .map(StringUtils::removeAllQuotes)
        .collect(Collectors.toList());
  }

  @Override
  public List<PsiElement> getTags(final PsiFile psiFile) {
    return getRootChildrenOfType(psiFile, JsonProperty.class)
        .stream()
        .filter(jsonProperty -> "tags".equals(jsonProperty.getName()))
        .map(JsonProperty::getValue)
        .map(el -> Arrays.asList(el.getChildren()))
        .flatMap(Collection::stream)
        .filter(el -> el instanceof JsonObject)
        .map(JsonObject.class::cast)
        .map(jsonObject -> jsonObject.findProperty("name"))
        .map(JsonProperty::getValue)
        .filter(Objects::nonNull)
        .collect(Collectors.toList());
  }

  @Override
  public boolean isUniqueArrayStringValue(final String value, final PsiElement psiElement) {
    return Optional.ofNullable(psiElement.getParent())
        .map(PsiElement::getParent)
        .filter(el -> el instanceof JsonArray)
        .map(el -> Arrays.asList(el.getChildren()))
        .map(children -> children.stream().filter(c -> c instanceof JsonLiteral))
        .map(childrenStream -> childrenStream.map(JsonLiteral.class::cast))
        .map(
            childrenStream ->
                childrenStream.noneMatch(
                    jsonLiteral ->
                        value.equals(StringUtils.removeAllQuotes(jsonLiteral.getText()))))
        .orElse(true);
  }

  @Override
  public InsertHandler<LookupElement> createInsertFieldHandler(final Field field) {
    return new JsonInsertFieldHandler(this, field);
  }

  @Override
  public InsertHandler<LookupElement> createInsertValueHandler(final Value value) {
    return new JsonInsertValueHandler(value);
  }

  @Override
  public List<String> getSecurityScopesIfOAuth2(final PsiElement securityDefinitionItem) {
    final List<JsonProperty> properties = getChildProperties(securityDefinitionItem);

    final boolean isOAuth2 =
        properties
            .stream()
            .anyMatch(
                prop -> {
                  final Optional<String> value =
                      Optional.ofNullable(prop.getValue())
                          .map(JsonValue::getText)
                          .map(StringUtils::removeAllQuotes);
                  return "type".equals(prop.getName()) && Optional.of("oauth2").equals(value);
                });

    if (isOAuth2) {
      return properties
          .stream()
          .filter(prop -> "scopes".equals(prop.getName()))
          .map(this::getChildProperties)
          .flatMap(Collection::stream)
          .map(JsonProperty::getName)
          .collect(Collectors.toList());
    }

    return ImmutableList.of();
  }

  @Override
  public PsiElement extractObjectForValidation(final PsiElement psiElement) {
    if (psiElement.getParent().getParent().getParent().getParent() instanceof JsonArray) {
      return psiElement.getParent().getParent().getParent().getParent();
    }

    return psiElement.getParent().getParent().getParent();
  }

  private List<JsonProperty> getChildProperties(final PsiElement element) {
    return toJsonProperty(element)
        .map(JsonProperty::getValue)
        .map(JsonValue::getChildren)
        .map(Arrays::asList)
        .map(children -> children.stream().map(this::toJsonProperty))
        .map(
            children ->
                children
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .collect(Collectors.toList()))
        .orElse(ImmutableList.of());
  }

  private Optional<JsonProperty> toJsonProperty(final PsiElement psiElement) {
    return psiElement instanceof JsonProperty
        ? Optional.of((JsonProperty) psiElement)
        : Optional.empty();
  }

  public boolean isLastChild(@NotNull PsiElement psiElement) {
    final Optional<PsiElement> lastChildOfParent =
        Optional.ofNullable(psiElement.getParent())
            .map(PsiElement::getParent)
            .map(PsiElement::getParent)
            .map(el -> el.getChildren()[el.getChildren().length - 1]);

    final Optional<PsiElement> child =
        Optional.of(psiElement).map(PsiElement::getParent).map(PsiElement::getParent);

    return lastChildOfParent.equals(child);
  }

  private Optional<JsonObject> getRootObject(final PsiFile psiFile) {
    return Arrays.stream(psiFile.getChildren())
        .filter(el -> el instanceof JsonObject)
        .map(JsonObject.class::cast)
        .findFirst();
  }

  @Override
  public void addReferenceDefinition(final String path, PsiElement anchorPsiElement) {
    if (path.isEmpty()) return;

    final String current = org.apache.commons.lang.StringUtils.substringBefore(path, "/");

    final Optional<PsiElement> found =
        new PathFinder().findByPathFrom("$." + current, anchorPsiElement);

    if (found.isPresent()) {
      anchorPsiElement = found.get();
    } else {
      final JsonProperty jsonProperty =
          new JsonElementGenerator(anchorPsiElement.getProject()).createProperty(current, "{}");

      final Optional<JsonObject> jsonObject = getJsonObject(anchorPsiElement);

      if (jsonObject.isPresent()) {
        anchorPsiElement = JsonPsiUtil.addProperty(jsonObject.get(), jsonProperty, false);
      } else {
        return;
      }
    }

    final String remaining = org.apache.commons.lang.StringUtils.substringAfter(path, "/");
    addReferenceDefinition(remaining, anchorPsiElement);
  }

  private Optional<JsonObject> getJsonObject(final PsiElement psiElement) {
    final List<PsiElement> children =
        Optional.of(psiElement)
            .map(PsiElement::getChildren)
            .map(Arrays::asList)
            .orElse(new ArrayList<>());

    return children
        .stream()
        .filter(child -> child instanceof JsonObject)
        .map(JsonObject.class::cast)
        .findFirst();
  }
}
