package org.zalando.intellij.swagger.completion.traversal;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.intellij.codeInsight.completion.InsertHandler;
import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import org.jetbrains.yaml.psi.YAMLFile;
import org.jetbrains.yaml.psi.YAMLKeyValue;
import org.jetbrains.yaml.psi.YAMLSequence;
import org.jetbrains.yaml.psi.YAMLSequenceItem;
import org.jetbrains.yaml.psi.YAMLValue;
import org.zalando.intellij.swagger.completion.level.field.Field;
import org.zalando.intellij.swagger.completion.level.inserthandler.YamlInsertHandler;
import org.zalando.intellij.swagger.completion.style.CompletionStyle;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class YamlTraversal extends Traversal {

    @Override
    Optional<String> getNameOfNthKey(final PsiElement psiElement, final int nth) {
        return getNthOfType(psiElement, nth, YAMLKeyValue.class)
                .map(YAMLKeyValue::getName);
    }

    @Override
    public boolean isRoot(final PsiElement psiElement) {
        return psiElement.getParent().getParent() instanceof YAMLFile ||
                psiElement.getParent().getParent().getParent().getParent() instanceof YAMLFile;
    }

    @Override
    public boolean shouldQuote(final PsiElement psiElement) {
        return false;
    }

    @Override
    public CompletionStyle.QuoteStyle getQuoteStyle() {
        return CompletionStyle.QuoteStyle.SINGLE;
    }

    @Override
    public boolean elementIsInsideParametersArray(final PsiElement psiElement) {
        return getNthOfType(psiElement, 1, YAMLSequence.class)
                .map(YAMLSequence::getParent)
                .filter(el -> el instanceof YAMLKeyValue)
                .map(YAMLKeyValue.class::cast)
                .map(YAMLKeyValue::getName)
                .filter(name -> name.equals("parameters"))
                .isPresent();
    }

    @Override
    public List<PsiElement> getChildrenOf(final String propertyName, final PsiFile psiFile) {
        return getRootChildren(psiFile).stream()
                .filter(child -> child instanceof YAMLKeyValue)
                .map(YAMLKeyValue.class::cast)
                .filter(yamlKeyValue -> propertyName.equals(yamlKeyValue.getName()))
                .findAny()
                .map(YAMLKeyValue::getValue)
                .map(YAMLValue::getYAMLElements)
                .map(c -> c.stream().map(PsiElement.class::cast).collect(Collectors.toList()))
                .orElse(Lists.newArrayList());
    }

    @Override
    public List<String> getKeyNamesOf(final String propertyName, final PsiFile containingFile) {
        return getChildrenOf(propertyName, containingFile).stream()
                .filter(el -> el instanceof YAMLKeyValue)
                .map(YAMLKeyValue.class::cast)
                .map(YAMLKeyValue::getName)
                .collect(Collectors.toList());
    }

    @Override
    public boolean isUniqueKey(final String keyName, final PsiElement psiElement) {
        return Optional.ofNullable(psiElement.getParent())
                .map(PsiElement::getParent)
                .map(el -> Arrays.asList(el.getChildren()))
                .map(children -> children.stream().filter(c -> c instanceof YAMLKeyValue))
                .map(childrenStream -> childrenStream.map(YAMLKeyValue.class::cast))
                .map(childrenStream -> childrenStream.noneMatch(yamlKeyValue -> keyName.equals(yamlKeyValue.getName())))
                .orElse(true);
    }

    @Override
    public InsertHandler<LookupElement> createInsertHandler(final Field field) {
        return new YamlInsertHandler(field);
    }

    @Override
    boolean elementIsInsideArray(final PsiElement psiElement) {
        return getNthOfType(psiElement, Integer.MAX_VALUE, YAMLSequence.class) != null;
    }

    @Override
    int getInfoNth() {
        return 1;
    }

    @Override
    int getContactNth() {
        return 1;
    }

    @Override
    int getLicenseNth() {
        return 1;
    }

    @Override
    int getPathNth() {
        return 2;
    }

    @Override
    int getOperationNth() {
        return 3;
    }

    @Override
    int getExternalDocsNth() {
        return 1;
    }

    @Override
    int getParametersNth() {
        return 1;
    }

    @Override
    int getItemsNth() {
        return 1;
    }

    @Override
    int getResponsesNth() {
        return 1;
    }

    @Override
    int getResponseNth() {
        return 2;
    }

    @Override
    int getHeadersNth() {
        return 1;
    }

    @Override
    int getTagsNth() {
        return 1;
    }

    @Override
    int getSecurityDefinitionsNth() {
        return 2;
    }

    @Override
    int getSchemaNth() {
        return 1;
    }

    @Override
    int getXmlNth() {
        return 1;
    }

    @Override
    int getDefinitionsNth() {
        return 2;
    }

    @Override
    int getParameterDefinitionNth() {
        return 2;
    }

    @Override
    int getMimeNth() {
        return 1;
    }

    @Override
    int getSchemesNth() {
        return 1;
    }

    public boolean elementIsDirectValueOfKey(final PsiElement psiElement, final String... keyNames) {
        final Set<String> targetKeyNames = Sets.newHashSet(keyNames);
        return getNthOfType(psiElement, 1, YAMLKeyValue.class)
                .map(YAMLKeyValue::getName)
                .filter(targetKeyNames::contains)
                .isPresent() &&
                !Optional.ofNullable(psiElement.getParent())
                        .map(PsiElement::getParent)
                        .filter(el -> el instanceof YAMLSequenceItem)
                        .isPresent();
    }

    private List<PsiElement> getRootChildren(final PsiFile psiFile) {
        return Optional.of(psiFile)
                .filter(file -> file instanceof YAMLFile)
                .map(YAMLFile.class::cast)
                .map(yamlFile -> yamlFile.getDocuments().get(0))
                .map(yamlDocument -> yamlDocument.getYAMLElements().get(0))
                .map(psiElement -> Arrays.asList(psiElement.getChildren()))
                .orElse(Lists.newArrayList());
    }

}
