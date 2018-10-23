package org.zalando.intellij.swagger.completion;

import com.intellij.codeInsight.completion.InsertHandler;
import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiNamedElement;
import java.util.List;
import java.util.Optional;
import org.zalando.intellij.swagger.completion.field.model.common.Field;
import org.zalando.intellij.swagger.completion.value.model.common.Value;
import org.zalando.intellij.swagger.traversal.Traversal;
import org.zalando.intellij.swagger.traversal.path.PathFinder;

public class CompletionHelper {

  protected final PsiElement psiElement;
  private final Traversal traversal;

  protected CompletionHelper(final PsiElement psiElement, final Traversal traversal) {
    this.psiElement = psiElement;
    this.traversal = traversal;
  }

  public boolean hasPath(final String pathExpression) {
    return new PathFinder().isInsidePath(psiElement, pathExpression);
  }

  public boolean isUniqueKey(final String keyName) {
    List<? extends PsiNamedElement> children =
        new PathFinder().findDirectNamedChildren("parent", psiElement);

    return children.stream().noneMatch((c) -> keyName.equals(c.getName()));
  }

  public InsertHandler<LookupElement> createInsertFieldHandler(final Field field) {
    return traversal.createInsertFieldHandler(field);
  }

  public InsertHandler<LookupElement> createInsertValueHandler(final Value value) {
    return traversal.createInsertValueHandler(value);
  }

  public Optional<String> extractSecurityNameFromSecurityObject(final PsiElement psiElement) {
    return traversal.extractSecurityNameFromSecurityItem(psiElement);
  }

  public List<String> getSecurityScopesIfOAuth2(final PsiElement securityDefinitionItem) {
    return traversal.getSecurityScopesIfOAuth2(securityDefinitionItem);
  }

  public Optional<String> getKeyNameOfObject(final PsiElement psiElement) {
    return traversal.getKeyNameOfObject(psiElement);
  }

  public Optional<PsiElement> getParentByName(final String parentName) {
    return traversal.getParentByName(psiElement, parentName);
  }

  public Optional<String> getParentKeyName() {
    return traversal.getParentKeyName(psiElement);
  }

  public boolean isUniqueArrayStringValue(final String keyName) {
    return traversal.isUniqueArrayStringValue(keyName, psiElement);
  }

  public List<PsiElement> getChildrenOfArrayObject(final PsiElement psiElement) {
    return traversal.getChildrenOfArrayObject(psiElement);
  }

  public List<String> getTagNames() {
    return traversal.getTagNames(psiElement.getContainingFile());
  }

  public PsiFile getPsiFile() {
    return psiElement.getContainingFile();
  }
}
