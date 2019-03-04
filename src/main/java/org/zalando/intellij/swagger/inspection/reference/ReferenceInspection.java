package org.zalando.intellij.swagger.inspection.reference;

import static com.intellij.codeInspection.ExternalAnnotatorInspectionVisitor.LocalQuickFixBackedByIntentionAction;

import com.intellij.codeInsight.intention.IntentionAction;
import com.intellij.codeInspection.LocalInspectionTool;
import com.intellij.codeInspection.ProblemHighlightType;
import com.intellij.codeInspection.ProblemsHolder;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReference;
import org.zalando.intellij.swagger.reference.LocalReference;

abstract class ReferenceInspection extends LocalInspectionTool {

  void doCheck(
      final ProblemsHolder holder,
      final PsiElement psiElement,
      final IntentionAction intentionAction) {
    final PsiReference[] references = psiElement.getReferences();

    for (PsiReference reference : references) {
      final PsiElement resolved = reference.resolve();

      if (resolved == null) {
        if (reference instanceof LocalReference) {
          registerLocalReferenceProblem(holder, psiElement, intentionAction);
        } else {
          registerReferenceProblem(holder, psiElement);
        }
      }
    }

    /*
     * If there are no PSI references for a $ref value,
     * it must contain unsupported naming convention or point to an unknown file
     * other than JSON or YAML.
     */
    if (references.length == 0) {
      registerReferenceProblem(holder, psiElement);
    }
  }

  /*
   * Local references are provided with a quick fix for adding the non-existing element.
   */
  private void registerLocalReferenceProblem(
      final ProblemsHolder holder,
      final PsiElement psiElement,
      final IntentionAction intentionAction) {

    final LocalQuickFixBackedByIntentionAction quickFix =
        new LocalQuickFixBackedByIntentionAction(intentionAction);

    holder.registerProblem(
        psiElement, getMessage(psiElement), ProblemHighlightType.GENERIC_ERROR, quickFix);
  }

  private void registerReferenceProblem(final ProblemsHolder holder, final PsiElement psiElement) {
    holder.registerProblem(psiElement, getMessage(psiElement), ProblemHighlightType.GENERIC_ERROR);
  }

  private String getMessage(final PsiElement psiElement) {
    return String.format("Can not resolve reference %s", psiElement.getText());
  }
}
