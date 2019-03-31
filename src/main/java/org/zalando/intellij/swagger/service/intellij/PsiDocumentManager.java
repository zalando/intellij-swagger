package org.zalando.intellij.swagger.service.intellij;

import com.intellij.openapi.editor.Document;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiFile;

public class PsiDocumentManager {

  public PsiFile getPsiFile(final Project project, final Document document) {
    return com.intellij.psi.PsiDocumentManager.getInstance(project).getPsiFile(document);
  }
}
