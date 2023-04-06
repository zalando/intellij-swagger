package org.zalando.intellij.swagger.inspection.schema;

import com.intellij.codeInspection.LocalInspectionTool;
import com.intellij.codeInspection.LocalInspectionToolSession;
import com.intellij.codeInspection.ProblemsHolder;
import com.intellij.openapi.vfs.VfsUtil;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.PsiFile;
import com.intellij.util.ResourceUtil;
import com.jetbrains.jsonSchema.extension.JsonLikePsiWalker;
import com.jetbrains.jsonSchema.ide.JsonSchemaService;
import com.jetbrains.jsonSchema.impl.JsonComplianceCheckerOptions;
import com.jetbrains.jsonSchema.impl.JsonSchemaComplianceChecker;
import com.jetbrains.jsonSchema.impl.JsonSchemaObject;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.yaml.psi.YAMLDocument;
import org.jetbrains.yaml.psi.YAMLFile;
import org.jetbrains.yaml.psi.YamlPsiElementVisitor;
import org.zalando.intellij.swagger.file.FileDetector;

public class YamlSchemaInspection extends LocalInspectionTool {

  private final FileDetector fileDetector = new FileDetector();

  @NotNull
  @Override
  public PsiElementVisitor buildVisitor(
      @NotNull final ProblemsHolder holder,
      final boolean isOnTheFly,
      @NotNull final LocalInspectionToolSession session) {

    PsiFile file = holder.getFile();

    if (fileDetector.isMainOpenApiYamlFile(file)) {
      return createVisitor("openapi.json", holder, session, file);
    } else if (fileDetector.isMainSwaggerYamlFile(file)) {
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
    List<YAMLDocument> documents = ((YAMLFile) file).getDocuments();
    if (documents.size() != 1) return PsiElementVisitor.EMPTY_VISITOR;

    PsiElement root = documents.get(0).getTopLevelValue();
    if (root == null) return PsiElementVisitor.EMPTY_VISITOR;

    JsonSchemaService service = JsonSchemaService.Impl.get(file.getProject());

    final URL url =
        ResourceUtil.getResource(getClass().getClassLoader(), "schemas", schemaFileName);
    Objects.requireNonNull(url, "Schema file " + schemaFileName + " not found from classpath");
    final VirtualFile virtualFile = VfsUtil.findFileByURL(url);
    Objects.requireNonNull(
        virtualFile,
        "Schema file " + schemaFileName + " with url " + url + " not found from classpath");
    final JsonSchemaObject schema = service.getSchemaObjectForSchemaFile(virtualFile);
    Objects.requireNonNull(schema, "Schema object for virtual file " + virtualFile + " not found");

    return new YamlPsiElementVisitor() {
      @Override
      public void visitElement(PsiElement element) {
        if (element != root) return;
        final JsonLikePsiWalker walker = JsonLikePsiWalker.getWalker(element, schema);
        if (walker == null) return;

        JsonComplianceCheckerOptions options = new JsonComplianceCheckerOptions(false);

        new JsonSchemaComplianceChecker(schema, holder, walker, session, options).annotate(element);
      }
    };
  }
}
