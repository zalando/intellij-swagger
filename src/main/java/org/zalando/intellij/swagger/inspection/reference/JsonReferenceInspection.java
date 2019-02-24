package org.zalando.intellij.swagger.inspection.reference;

import com.intellij.codeInspection.LocalInspectionTool;
import com.intellij.codeInspection.LocalInspectionToolSession;
import com.intellij.codeInspection.ProblemHighlightType;
import com.intellij.codeInspection.ProblemsHolder;
import com.intellij.json.JsonBundle;
import com.intellij.json.psi.JsonElementVisitor;
import com.intellij.json.psi.JsonObject;
import com.intellij.json.psi.JsonProperty;
import com.intellij.json.psi.JsonStringLiteral;
import com.intellij.json.psi.JsonValue;
import com.intellij.openapi.paths.WebReference;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiReference;
import com.intellij.psi.impl.source.resolve.reference.impl.providers.FileReference;
import com.jetbrains.jsonSchema.impl.JsonPointerReferenceProvider;

import org.jetbrains.annotations.NotNull;
import org.zalando.intellij.swagger.file.FileDetector;
import org.zalando.intellij.swagger.index.openapi.OpenApiIndexService;
import org.zalando.intellij.swagger.index.swagger.SwaggerIndexService;

public class JsonReferenceInspection extends ReferenceInspection {

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

    return new JsonElementVisitor() {
      @Override
      public void visitProperty(@NotNull JsonProperty o) {
        if (!checkRefs) {
          return;
        }
        if ("$ref".equals(o.getName())) {
          JsonValue value = o.getValue();

          if (!(value instanceof JsonStringLiteral)) {
            return;
          }

          doCheck(holder, value);
        }
        super.visitProperty(o);
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
