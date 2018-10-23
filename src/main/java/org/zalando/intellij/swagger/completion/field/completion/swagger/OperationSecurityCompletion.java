package org.zalando.intellij.swagger.completion.field.completion.swagger;

import com.intellij.codeInsight.completion.CompletionResultSet;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiNamedElement;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.zalando.intellij.swagger.completion.CompletionHelper;
import org.zalando.intellij.swagger.completion.field.FieldCompletion;
import org.zalando.intellij.swagger.completion.field.model.common.ArrayField;
import org.zalando.intellij.swagger.traversal.path.PathFinder;

class OperationSecurityCompletion extends FieldCompletion {

  OperationSecurityCompletion(
      final CompletionHelper completionHelper, final CompletionResultSet completionResultSet) {
    super(completionHelper, completionResultSet);
  }

  @Override
  public void fill() {
    getSecurityDefinitions()
        .forEach(
            field ->
                completionHelper
                    .getParentByName("security")
                    .ifPresent(
                        securityParent -> {
                          final List<PsiElement> security =
                              completionHelper.getChildrenOfArrayObject(securityParent);
                          final List<String> existingNames = extractNames(security);
                          if (!existingNames.contains(field.getName())) {
                            addUnique(field);
                          }
                        }));
  }

  private List<String> extractNames(final List<PsiElement> securityObjects) {
    return securityObjects
        .stream()
        .map(completionHelper::extractSecurityNameFromSecurityObject)
        .filter(Optional::isPresent)
        .map(Optional::get)
        .collect(Collectors.toList());
  }

  private List<ArrayField> getSecurityDefinitions() {
    final PsiFile containingFile = completionHelper.getPsiFile().getContainingFile();
    final List<? extends PsiNamedElement> securityDefinitions =
        new PathFinder().findNamedChildren("$.securityDefinitions", containingFile);

    return securityDefinitions
        .stream()
        .map(PsiNamedElement::getName)
        .map(ArrayField::new)
        .collect(Collectors.toList());
  }
}
