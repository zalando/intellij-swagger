package org.zalando.intellij.swagger.completion.field.completion.swagger;

import com.intellij.codeInsight.completion.CompletionResultSet;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiNamedElement;
import java.util.List;
import java.util.stream.Collectors;
import org.zalando.intellij.swagger.completion.CompletionHelper;
import org.zalando.intellij.swagger.completion.field.FieldCompletion;
import org.zalando.intellij.swagger.completion.field.model.common.ArrayField;
import org.zalando.intellij.swagger.traversal.path.PathFinder;

class RootSecurityCompletion extends FieldCompletion {

  RootSecurityCompletion(
      final CompletionHelper completionHelper, final CompletionResultSet completionResultSet) {
    super(completionHelper, completionResultSet);
  }

  @Override
  public void fill() {
    getSecurityDefinitions()
        .forEach(
            field -> {
              final PsiFile containingFile = completionHelper.getPsiFile().getContainingFile();
              final List<? extends PsiNamedElement> security =
                  new PathFinder().findNamedChildren("$.security", containingFile);
              final List<String> existingNames = extractNames(security);

              if (!existingNames.contains(field.getName())) {
                addUnique(field);
              }
            });
  }

  private List<String> extractNames(final List<? extends PsiNamedElement> securityObjects) {
    return securityObjects.stream().map(PsiNamedElement::getName).collect(Collectors.toList());
  }

  private List<ArrayField> getSecurityDefinitions() {
    final PsiFile containingFile = completionHelper.getPsiFile().getContainingFile();
    final List<? extends PsiNamedElement> securityDefinitions =
        new PathFinder().findNamedChildren("$.securityDefinitions", containingFile);

    return securityDefinitions.stream()
        .map(PsiNamedElement::getName)
        .map(ArrayField::new)
        .collect(Collectors.toList());
  }
}
