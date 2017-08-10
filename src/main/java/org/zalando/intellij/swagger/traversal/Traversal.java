package org.zalando.intellij.swagger.traversal;

import com.intellij.codeInsight.completion.InsertHandler;
import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.json.psi.JsonArray;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiNamedElement;
import org.jetbrains.yaml.psi.YAMLSequence;
import org.zalando.intellij.swagger.completion.field.model.common.Field;
import org.zalando.intellij.swagger.completion.value.model.Value;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public abstract class Traversal {

    public abstract Optional<String> getKeyNameIfKey(final PsiElement psiElement);

    public abstract Optional<String> getKeyNameOfObject(final PsiElement psiElement);

    public abstract Optional<String> getParentKeyName(final PsiElement psiElement);

    public abstract boolean elementIsDirectValueOfKey(final PsiElement psiElement, final String... keyNames);

    public abstract List<PsiElement> getChildrenOfRootProperty(final String propertyName, final PsiFile psiFile);

    public abstract List<String> getTagNames(final PsiFile psiFile);

    public abstract List<String> getKeyNamesOfDefinition(final String propertyName, final PsiFile containingFile);

    public abstract boolean isUniqueKey(String keyName, final PsiElement psiElement);

    public abstract boolean isUniqueArrayStringValue(String value, final PsiElement psiElement);

    public abstract boolean isValue(final PsiElement psiElement);

    public abstract boolean isArrayStringElement(final PsiElement psiElement);

    public abstract Optional<String> extractSecurityNameFromSecurityItem(final PsiElement psiElement);

    public abstract InsertHandler<LookupElement> createInsertFieldHandler(Field field);

    public abstract InsertHandler<LookupElement> createInsertValueHandler(final Value value);

    public abstract List<String> getSecurityScopesIfOAuth2(final PsiElement securityDefinitionItem);

    public abstract PsiElement extractObjectForValidation(final PsiElement psiElement);

    public abstract Optional<? extends PsiElement> getRootChildByName(final String keyName, final PsiFile psiFile);

    public abstract void addReferenceDefinition(final String definitions, final String referenceValue, final PsiFile psiFile);

    public final boolean isBooleanValue(final PsiElement psiElement) {
        return elementIsDirectValueOfKey(psiElement, "deprecated", "required", "allowEmptyValue",
                "exclusiveMaximum", "exclusiveMinimum", "uniqueItems", "readOnly", "attribute", "wrapped");
    }

    <T extends PsiElement> Optional<T> getNthOfType(final PsiElement psiElement, int nth, Class<T> targetType) {
        if (psiElement == null) {
            return Optional.empty();
        } else if (targetType.isAssignableFrom(psiElement.getClass())) {
            if (nth == 1) {
                return Optional.of(targetType.cast(psiElement));
            } else {
                nth--;
            }
        }
        return getNthOfType(psiElement.getParent(), nth, targetType);
    }

    public Optional<PsiElement> getParentByName(final PsiElement psiElement, final String parentName) {
        if (psiElement == null) {
            return Optional.empty();
        }

        if (psiElement instanceof PsiNamedElement) {
            final PsiNamedElement psiNamedElement = (PsiNamedElement) psiElement;

            if (parentName.equals(psiNamedElement.getName())) {
                return Optional.of(psiElement);
            }
        }

        return getParentByName(psiElement.getParent(), parentName);
    }

    public List<PsiElement> getChildrenOfArrayObject(final PsiElement psiElement) {
        return Arrays.stream(psiElement.getChildren())
                .filter(child -> child instanceof JsonArray || child instanceof YAMLSequence)
                .map(el -> Arrays.asList(el.getChildren()))
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    public abstract List<PsiElement> getTags(final PsiFile containingFile);

}
