package org.zalando.intellij.swagger.inspection.reference;

import com.intellij.codeInspection.LocalInspectionTool;
import com.intellij.codeInspection.LocalInspectionToolSession;
import com.intellij.codeInspection.ProblemHighlightType;
import com.intellij.codeInspection.ProblemsHolder;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiReference;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.yaml.psi.YAMLKeyValue;
import org.jetbrains.yaml.psi.YAMLScalar;
import org.jetbrains.yaml.psi.YAMLValue;
import org.jetbrains.yaml.psi.YamlPsiElementVisitor;
import org.zalando.intellij.swagger.index.openapi.OpenApiIndexService;
import org.zalando.intellij.swagger.index.swagger.SwaggerIndexService;

abstract class ReferenceInspection extends LocalInspectionTool {

  void doCheck(final ProblemsHolder holder, final PsiElement psiElement) {
    for (PsiReference reference : psiElement.getReferences()) {
      final PsiElement resolved = reference.resolve();

      if (resolved == null) {
        holder.registerProblem(
            reference,
            String.format("Can not resolve reference %s", psiElement.getText()),
            ProblemHighlightType.GENERIC_ERROR);
      }
    }
  }
}
