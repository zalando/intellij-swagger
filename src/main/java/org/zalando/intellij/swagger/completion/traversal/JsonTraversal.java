package org.zalando.intellij.swagger.completion.traversal;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.intellij.codeInsight.completion.InsertHandler;
import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.json.JsonElementTypes;
import com.intellij.json.psi.JsonArray;
import com.intellij.json.psi.JsonFile;
import com.intellij.json.psi.JsonProperty;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import org.jetbrains.annotations.NotNull;
import org.zalando.intellij.swagger.completion.level.field.Field;
import org.zalando.intellij.swagger.completion.level.inserthandler.JsonInsertHandler;
import org.zalando.intellij.swagger.completion.style.CompletionStyle;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class JsonTraversal extends Traversal {

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
                .isPresent();
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
    public InsertHandler<LookupElement> createInsertHandler(final Field field) {
        return new JsonInsertHandler(this, field);
    }

    @Override
    boolean elementIsInsideArray(final PsiElement psiElement) {
        return getNthOfType(psiElement, Integer.MAX_VALUE, JsonArray.class) != null;
    }

    @Override
    int getInfoNth() {
        return 2;
    }

    @Override
    int getContactNth() {
        return 2;
    }

    @Override
    int getLicenseNth() {
        return 2;
    }

    @Override
    int getPathNth() {
        return 3;
    }

    @Override
    int getOperationNth() {
        return 3;
    }

    @Override
    int getExternalDocsNth() {
        return 2;
    }

    @Override
    int getParametersNth() {
        return 2;
    }

    @Override
    int getItemsNth() {
        return 2;
    }

    @Override
    int getResponsesNth() {
        return 2;
    }

    @Override
    int getResponseNth() {
        return 3;
    }

    @Override
    int getHeadersNth() {
        return 3;
    }

    @Override
    int getTagsNth() {
        return 2;
    }

    @Override
    int getSecurityDefinitionsNth() {
        return 3;
    }

    @Override
    int getSchemaNth() {
        return 2;
    }

    @Override
    int getXmlNth() {
        return 2;
    }

    @Override
    int getDefinitionsNth() {
        return 3;
    }

    @Override
    int getParameterDefinitionNth() {
        return 3;
    }

    @Override
    int getMimeNth() {
        return 1;
    }

    @Override
    int getSchemesNth() {
        return 1;
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
