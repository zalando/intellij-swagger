package org.zalando.intellij.swagger.intention.reference;

import com.intellij.codeInsight.intention.IntentionAction;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiFile;
import org.apache.commons.lang.StringUtils;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.yaml.psi.YAMLFile;
import org.zalando.intellij.swagger.traversal.YamlTraversal;

public class CreateYamlReferenceIntentionAction implements IntentionAction {

  private final String referenceValueWithPrefix;

  public CreateYamlReferenceIntentionAction(final String referenceValueWithPrefix) {
    this.referenceValueWithPrefix = referenceValueWithPrefix;
  }

  @Nls
  @NotNull
  @Override
  public String getText() {
    return "Create";
  }

  @Nls
  @NotNull
  @Override
  public String getFamilyName() {
    return "Create";
  }

  @Override
  public boolean isAvailable(
      @NotNull final Project project, final Editor editor, final PsiFile psiFile) {
    return true;
  }

  @Override
  public void invoke(@NotNull final Project project, final Editor editor, final PsiFile psiFile) {
    final Editor activeEditor = FileEditorManager.getInstance(project).getSelectedTextEditor();

    final String path = StringUtils.substringAfter(referenceValueWithPrefix, "#/");

    new ReferenceCreator(path, ((YAMLFile) psiFile).getDocuments().get(0), new YamlTraversal())
        .create();
  }

  @Override
  public boolean startInWriteAction() {
    return true;
  }
}
