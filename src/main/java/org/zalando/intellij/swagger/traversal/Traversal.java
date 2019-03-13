package org.zalando.intellij.swagger.traversal;

import com.intellij.codeInsight.completion.InsertHandler;
import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.json.psi.JsonArray;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiNamedElement;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.jetbrains.yaml.psi.YAMLSequence;
import org.zalando.intellij.swagger.completion.field.model.common.Field;
import org.zalando.intellij.swagger.completion.value.model.common.Value;

public abstract class Traversal {

  public abstract Optional<String> getKeyNameIfKey(final PsiElement psiElement);

  public abstract Optional<String> getKeyNameOfObject(final PsiElement psiElement);

  public abstract Optional<String> getParentKeyName(final PsiElement psiElement);

  public abstract List<String> getTagNames(final PsiFile psiFile);

  public abstract boolean isUniqueArrayStringValue(String value, final PsiElement psiElement);

  public abstract Optional<String> extractSecurityNameFromSecurityItem(final PsiElement psiElement);

  public abstract InsertHandler<LookupElement> createInsertFieldHandler(Field field);

  public abstract InsertHandler<LookupElement> createInsertValueHandler(final Value value);

  public abstract List<String> getSecurityScopesIfOAuth2(final PsiElement securityDefinitionItem);

  public abstract void addReferenceDefinition(final String path, final PsiElement anchorPsiElement);

  <T extends PsiElement> Optional<T> getNthOfType(
      final PsiElement psiElement, int nth, Class<T> targetType) {
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

  public Optional<PsiElement> getParentByName(
      final PsiElement psiElement, final String parentName) {
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
}
