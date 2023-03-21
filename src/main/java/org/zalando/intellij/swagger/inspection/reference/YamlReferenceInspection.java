package org.zalando.intellij.swagger.inspection.reference;

import com.intellij.codeInspection.LocalInspectionToolSession;
import com.intellij.codeInspection.ProblemsHolder;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.PsiFile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.yaml.psi.YAMLKeyValue;
import org.jetbrains.yaml.psi.YAMLScalar;
import org.jetbrains.yaml.psi.YAMLValue;
import org.jetbrains.yaml.psi.YamlPsiElementVisitor;
import org.zalando.intellij.swagger.index.IndexFacade;
import org.zalando.intellij.swagger.intention.reference.CreateYamlReferenceIntentionAction;

public class YamlReferenceInspection extends ReferenceInspection {

  @NotNull
  @Override
  public PsiElementVisitor buildVisitor(
      @NotNull final ProblemsHolder holder,
      final boolean isOnTheFly,
      @NotNull final LocalInspectionToolSession session) {
    final PsiFile file = holder.getFile();
    final VirtualFile virtualFile = file.getVirtualFile();
    final Project project = holder.getProject();

    IndexFacade indexFacade = ApplicationManager.getApplication().getService(IndexFacade.class);
    boolean checkRefs =
        indexFacade.isMainSpecFile(virtualFile, project)
            || indexFacade.isPartialSpecFile(virtualFile, project);

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

          final String unquotedValue = StringUtil.unquoteString(value.getText());

          if (!unquotedValue.startsWith("http")) {
            doCheck(holder, value, new CreateYamlReferenceIntentionAction(unquotedValue));
          }
        }
        super.visitKeyValue(keyValue);
      }
    };
  }
}
