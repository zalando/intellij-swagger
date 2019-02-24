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

public class YamlReferenceInspection extends ReferenceInspection {

  private final OpenApiIndexService openApiIndexService = new OpenApiIndexService();
  private final SwaggerIndexService swaggerIndexService = new SwaggerIndexService();

  @NotNull
  @Override
  public PsiElementVisitor buildVisitor(
      @NotNull final ProblemsHolder holder,
      final boolean isOnTheFly,
      @NotNull final LocalInspectionToolSession session) {
    final PsiFile file = holder.getFile();

    boolean checkRefs = isOpenApi(holder, file) || isSwagger(holder, file);

    return new YamlPsiElementVisitor() {
      @Override
      public void visitKeyValue(@NotNull YAMLKeyValue keyValue) {
        if (!checkRefs) {
          return;
        }
        if ("$ref".equals(keyValue.getKeyText())) {
          YAMLValue value = keyValue.getValue();

          if (!(value instanceof YAMLScalar)) {
            return;
          }

          doCheck(holder, value);
        }
        super.visitKeyValue(keyValue);
      }
    };
  }

  private boolean isOpenApi(@NotNull final ProblemsHolder holder, final PsiFile file) {
    return openApiIndexService.isMainOpenApiFile(file.getVirtualFile(), holder.getProject())
        || openApiIndexService.isPartialOpenApiFile(file.getVirtualFile(), holder.getProject());
  }

  private boolean isSwagger(@NotNull final ProblemsHolder holder, final PsiFile file) {
    return swaggerIndexService.isMainSwaggerFile(file.getVirtualFile(), holder.getProject())
        || swaggerIndexService.isPartialSwaggerFile(file.getVirtualFile(), holder.getProject());
  }
}
