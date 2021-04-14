package org.zalando.intellij.swagger.inspection.reference;

import com.intellij.codeInspection.LocalInspectionToolSession;
import com.intellij.codeInspection.ProblemsHolder;
import com.intellij.json.psi.JsonElementVisitor;
import com.intellij.json.psi.JsonProperty;
import com.intellij.json.psi.JsonStringLiteral;
import com.intellij.json.psi.JsonValue;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.PsiFile;
import org.jetbrains.annotations.NotNull;
import org.zalando.intellij.swagger.index.IndexFacade;
import org.zalando.intellij.swagger.intention.reference.CreateJsonReferenceIntentionAction;

public class JsonReferenceInspection extends ReferenceInspection {

  @NotNull
  @Override
  public PsiElementVisitor buildVisitor(
      @NotNull final ProblemsHolder holder,
      final boolean isOnTheFly,
      @NotNull final LocalInspectionToolSession session) {
    final PsiFile file = holder.getFile();
    final VirtualFile virtualFile = file.getVirtualFile();
    final Project project = holder.getProject();
    IndexFacade indexFacade = ServiceManager.getService(IndexFacade.class);

    boolean checkRefs =
        indexFacade.isMainSpecFile(virtualFile, project)
            || indexFacade.isPartialSpecFile(virtualFile, project);

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

          final String unquotedValue = StringUtil.unquoteString(value.getText());

          if (!unquotedValue.startsWith("http")) {
            doCheck(holder, value, new CreateJsonReferenceIntentionAction(unquotedValue));
          }
        }
        super.visitProperty(o);
      }
    };
  }
}
