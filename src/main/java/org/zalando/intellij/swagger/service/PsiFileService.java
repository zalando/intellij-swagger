package org.zalando.intellij.swagger.service;

import com.intellij.openapi.editor.Document;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.psi.PsiFile;
import java.util.Optional;
import org.zalando.intellij.swagger.service.intellij.PsiDocumentManager;

public class PsiFileService {

  private final ProjectManager projectManager;
  private final PsiDocumentManager psiDocumentManager;

  public PsiFileService(
      final ProjectManager projectManager, final PsiDocumentManager psiDocumentManager) {
    this.projectManager = projectManager;
    this.psiDocumentManager = psiDocumentManager;
  }

  public Optional<PsiFile> fromDocument(final Document document) {
    final Project[] openProjects = projectManager.getOpenProjects();

    if (openProjects.length > 0) {
      final Project openProject = openProjects[0];

      return Optional.ofNullable(psiDocumentManager.getPsiFile(openProject, document));
    }

    return Optional.empty();
  }
}
