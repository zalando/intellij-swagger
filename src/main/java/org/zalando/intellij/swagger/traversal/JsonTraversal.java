package org.zalando.intellij.swagger.traversal;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.intellij.codeInsight.completion.InsertHandler;
import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.json.JsonElementTypes;
import com.intellij.json.psi.JsonArray;
import com.intellij.json.psi.JsonFile;
import com.intellij.json.psi.JsonLiteral;
import com.intellij.json.psi.JsonProperty;
import com.intellij.json.psi.JsonValue;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import org.jetbrains.annotations.NotNull;
import org.zalando.intellij.swagger.completion.StringUtils;
import org.zalando.intellij.swagger.completion.field.model.Field;
import org.zalando.intellij.swagger.completion.style.CompletionStyle;
import org.zalando.intellij.swagger.completion.value.model.Value;
import org.zalando.intellij.swagger.insert.JsonInsertFieldHandler;
import org.zalando.intellij.swagger.insert.JsonInsertValueHandler;
import org.zalando.intellij.swagger.traversal.keydepth.KeyDepth;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class JsonTraversal extends Traversal {

    public JsonTraversal(final KeyDepth keyDepth) {
        super(keyDepth);
    }

    @Override
    Optional<String> getNameOfNthKey(final PsiElement psiElement, final int nth) {
        return getNthOfType(psiElement, nth, JsonProperty.class)
                .map(JsonProperty::getName);
    }

    @Override
    public boolean isRoot(@NotNull final PsiElement psiElement) {
        return psiElement.getParent().getParent().getParent().getParent() instanceof JsonFile;
    }

    public boolean isKey(final PsiElement psiElement) {
        final PsiElement firstParent = psiElement.getParent();
        return Optional.ofNullable(firstParent)
                .map(PsiElement::getParent)
                .filter(jsonPropertyCandidate -> jsonPropertyCandidate instanceof JsonProperty)
                .map(JsonProperty.class::cast)
                .map(JsonProperty::getNameElement)
                .filter(nameElement -> nameElement == firstParent)
                .isPresent();
    }

    public Optional<String> getKeyName(final PsiElement psiElement) {
        final PsiElement firstParent = psiElement.getParent();
        return Optional.ofNullable(firstParent)
                .map(PsiElement::getParent)
                .filter(jsonPropertyCandidate -> jsonPropertyCandidate instanceof JsonProperty)
                .map(JsonProperty.class::cast)
                .map(JsonProperty::getNameElement)
                .filter(nameElement -> nameElement == firstParent)
                .map(JsonValue::getText)
                .map(StringUtils::removeAllQuotes);
    }

    @Override
    public boolean shouldQuote(final PsiElement psiElement) {
        return !psiElement.toString().contains(JsonElementTypes.DOUBLE_QUOTED_STRING.toString())
                && !isBooleanValue(psiElement);
    }

    @Override
    public boolean isPath(final PsiElement psiElement) {
        return nthKeyEquals(psiElement, 3, "paths");
    }

    @Override
    public boolean isOperation(final PsiElement psiElement) {
        return nthKeyEquals(psiElement, 4, "paths");
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
                .isPresent() && !(psiElement instanceof JsonProperty);
    }

    @Override
    public boolean isArrayStringElement(final PsiElement psiElement) {
        return psiElement.getParent() instanceof JsonLiteral &&
                psiElement.getParent().getParent() instanceof JsonArray;
    }

    @Override
    public CompletionStyle.QuoteStyle getQuoteStyle() {
        return CompletionStyle.QuoteStyle.DOUBLE;
    }

    @Override
    public boolean elementIsInsideParametersArray(final PsiElement psiElement) {
        return Optional.ofNullable(psiElement.getParent())
                .map(PsiElement::getParent)
                .map(PsiElement::getParent)
                .map(PsiElement::getParent)
                .map(PsiElement::getParent)
                .filter(element -> element instanceof JsonProperty)
                .map(JsonProperty.class::cast)
                .map(JsonProperty::getName)
                .filter(name -> name.equals("parameters"))
                .isPresent() && !isChildOfSchema(psiElement);
    }

    private boolean isChildOfSchema(final PsiElement psiElement) {
        if (psiElement == null) {
            return false;
        }
        if (psiElement instanceof JsonProperty) {
            if ("schema".equals(((JsonProperty) psiElement).getName())) {
                return true;
            }
        }
        return isChildOfSchema(psiElement.getParent());

    }

    @Override
    public List<PsiElement> getChildrenOf(final String propertyName, final PsiFile psiFile) {
        return getRootChildren(psiFile).stream()
                .filter(child -> child instanceof JsonProperty)
                .map(JsonProperty.class::cast)
                .filter(jsonProperty -> propertyName.equals(jsonProperty.getName()))
                .findAny()
                .map(JsonProperty::getValue)
                .map(jsonValue -> Arrays.asList(jsonValue.getChildren()))
                .orElse(Lists.newArrayList());
    }

    @Override
    public List<String> getKeyNamesOf(final String propertyName, final PsiFile containingFile) {
        return getChildrenOf(propertyName, containingFile).stream()
                .filter(el -> el instanceof JsonProperty)
                .map(JsonProperty.class::cast)
                .map(JsonProperty::getName)
                .collect(Collectors.toList());
    }

    @Override
    public boolean isUniqueKey(final String keyName, final PsiElement psiElement) {
        return Optional.ofNullable(psiElement.getParent())
                .map(PsiElement::getParent)
                .map(PsiElement::getParent)
                .map(el -> Arrays.asList(el.getChildren()))
                .map(children -> children.stream().filter(c -> c instanceof JsonProperty))
                .map(childrenStream -> childrenStream.map(JsonProperty.class::cast))
                .map(childrenStream -> childrenStream.noneMatch(jsonProperty -> keyName.equals(jsonProperty.getName())))
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
    boolean elementIsInsideArray(final PsiElement psiElement) {
        if (psiElement == null) {
            return false;
        } else if (psiElement instanceof JsonArray) {
            return true;
        }
        return elementIsInsideArray(psiElement.getParent());
    }

    @Override
    public boolean elementIsDirectValueOfKey(final PsiElement psiElement, final String... keyNames) {
        final Set<String> targetKeyNames = Sets.newHashSet(keyNames);
        return Optional.ofNullable(psiElement.getParent())
                .map(PsiElement::getParent)
                .filter(element -> element instanceof JsonProperty)
                .map(JsonProperty.class::cast)
                .map(JsonProperty::getName)
                .filter(targetKeyNames::contains)
                .isPresent();
    }

    public boolean isLastChild(@NotNull PsiElement psiElement) {
        final Optional<PsiElement> lastChildOfParent = Optional.ofNullable(psiElement.getParent())
                .map(PsiElement::getParent)
                .map(PsiElement::getParent)
                .map(el -> el.getChildren()[el.getChildren().length - 1]);

        final Optional<PsiElement> child = Optional.of(psiElement)
                .map(PsiElement::getParent)
                .map(PsiElement::getParent);

        return lastChildOfParent.equals(child);
    }

    private List<PsiElement> getRootChildren(final PsiFile psiFile) {
        return Arrays.asList(psiFile.getChildren()[0].getChildren());
    }

}
