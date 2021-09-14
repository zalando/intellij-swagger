package org.zalando.intellij.swagger.inspection.schema;

import com.intellij.codeInspection.LocalInspectionTool;
import com.intellij.codeInspection.LocalInspectionToolSession;
import com.intellij.codeInspection.ProblemsHolder;
import com.intellij.json.psi.JsonElementVisitor;
import com.intellij.json.psi.JsonFile;
import com.intellij.json.psi.JsonValue;
import com.intellij.openapi.vfs.VfsUtil;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.PsiFile;
import com.intellij.util.ObjectUtils;
import com.intellij.util.ResourceUtil;
import com.jetbrains.jsonSchema.extension.JsonLikePsiWalker;
import com.jetbrains.jsonSchema.ide.JsonSchemaService;
import com.jetbrains.jsonSchema.impl.JsonComplianceCheckerOptions;
import com.jetbrains.jsonSchema.impl.JsonSchemaComplianceChecker;
import com.jetbrains.jsonSchema.impl.JsonSchemaObject;
import java.net.URL;
import org.jetbrains.annotations.NotNull;
import org.zalando.intellij.swagger.file.FileDetector;

public class JsonSchemaInspection extends LocalInspectionTool {

  private final FileDetector fileDetector = new FileDetector();

  @NotNull
  @Override
  public PsiElementVisitor buildVisitor(
      @NotNull final ProblemsHolder holder,
      final boolean isOnTheFly,
      @NotNull final LocalInspectionToolSession session) {

    PsiFile file = holder.getFile();

    if (fileDetector.isMainOpenApiJsonFile(file)) {
      return createVisitor("openapi.json", holder, session, file);
    } else if (fileDetector.isMainSwaggerJsonFile(file)) {
      return createVisitor("swagger.json", holder, session, file);
    } else {
      return PsiElementVisitor.EMPTY_VISITOR;
    }
  }

  private PsiElementVisitor createVisitor(
      final String schemaFileName,
      final ProblemsHolder holder,
      final LocalInspectionToolSession session,
      final PsiFile file) {

    JsonValue root =
        file instanceof JsonFile
            ? ObjectUtils.tryCast(file.getFirstChild(), JsonValue.class)
            : null;
    if (root == null) return PsiElementVisitor.EMPTY_VISITOR;

    JsonSchemaService service = JsonSchemaService.Impl.get(file.getProject());

    final URL url =
        ResourceUtil.getResource(getClass().getClassLoader(), "schemas", schemaFileName);
    final VirtualFile virtualFile = VfsUtil.findFileByURL(url);

    final JsonSchemaObject schema = service.getSchemaObjectForSchemaFile(virtualFile);

    return new JsonElementVisitor() {
      @Override
      public void visitElement(PsiElement element) {
        if (element == root) {
          final JsonLikePsiWalker walker = JsonLikePsiWalker.getWalker(element, schema);
          if (walker == null) return;

          JsonComplianceCheckerOptions options = new JsonComplianceCheckerOptions(false);

          new JsonSchemaComplianceChecker(schema, holder, walker, session, options)
              .annotate(element);
        }
      }
    };
  }
}
