package org.zalando.intellij.swagger.traversal;

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
import org.jetbrains.yaml.psi.impl.YAMLPlainTextImpl;
import org.zalando.intellij.swagger.completion.field.model.Field;
import org.zalando.intellij.swagger.completion.style.CompletionStyle;
import org.zalando.intellij.swagger.completion.value.model.Value;
import org.zalando.intellij.swagger.insert.YamlInsertFieldHandler;
import org.zalando.intellij.swagger.insert.YamlInsertValueHandler;
import org.zalando.intellij.swagger.traversal.keydepth.KeyDepth;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class YamlTraversal extends Traversal {

    public YamlTraversal(final KeyDepth keyDepth) {
        super(keyDepth);
    }

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
    public boolean isKey(final PsiElement psiElement) {
        return false;
    }

    @Override
    public Optional<String> getKeyName(final PsiElement psiElement) {
        return Optional.of(psiElement.getParent())
                .filter(el -> el instanceof YAMLKeyValue)
                .map(YAMLKeyValue.class::cast)
                .map(YAMLKeyValue::getKey)
                .filter(key -> key == psiElement)
                .isPresent() ? Optional.of(((YAMLKeyValue) psiElement.getParent()).getKeyText()) : Optional.empty();

    }

    @Override
    public boolean elementIsInsideParametersArray(final PsiElement psiElement) {
        return getNthOfType(psiElement, 1, YAMLSequence.class)
                .map(YAMLSequence::getParent)
                .filter(el -> el instanceof YAMLKeyValue)
                .map(YAMLKeyValue.class::cast)
                .map(YAMLKeyValue::getName)
                .filter(name -> name.equals("parameters"))
                .isPresent() && !isChildOfKey(psiElement, "schema");
    }

    @Override
    public boolean isChildOfKey(final PsiElement psiElement, final String keyName) {
        if (psiElement == null) {
            return false;
        }
        if (psiElement instanceof YAMLKeyValue) {
            if (keyName.equals(((YAMLKeyValue) psiElement).getName())) {
                return true;
            }
        }
        return isChildOfKey(psiElement.getParent(), keyName);
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
    public InsertHandler<LookupElement> createInsertFieldHandler(final Field field) {
        return new YamlInsertFieldHandler(field);
    }

    @Override
    public InsertHandler<LookupElement> createInsertValueHandler(final Value value) {
        return new YamlInsertValueHandler();
    }

    @Override
    boolean elementIsInsideArray(final PsiElement psiElement) {
        if (psiElement == null) {
            return false;
        } else if (psiElement instanceof YAMLSequence) {
            return true;
        }
        return elementIsInsideArray(psiElement.getParent());
    }

    @Override
    public boolean isValue(final PsiElement psiElement) {
        final PsiElement grandparent = psiElement.getParent().getParent();
        return grandparent instanceof YAMLKeyValue &&
                !(psiElement instanceof YAMLKeyValue) &&
                ((YAMLKeyValue) grandparent).getValue() == psiElement.getParent() &&
                !"".equals(psiElement.getText().trim());
    }

    @Override
    public boolean isArrayStringElement(final PsiElement psiElement) {
        return psiElement.getParent() instanceof YAMLPlainTextImpl &&
                psiElement.getParent().getParent() instanceof YAMLSequenceItem;
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
