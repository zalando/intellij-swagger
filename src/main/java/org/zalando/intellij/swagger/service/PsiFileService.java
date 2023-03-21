package org.zalando.intellij.swagger.service;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.psi.PsiFile;
import java.util.Optional;
import org.zalando.intellij.swagger.service.intellij.PsiDocumentManager;

public class PsiFileService {

  public Optional<PsiFile> fromDocument(final Document document) {
    final Project[] openProjects =
      ApplicationManager.getApplication().getService(ProjectManager.class).getOpenProjects();

    if (openProjects.length > 0) {
      final Project openProject = openProjects[0];

      return Optional.ofNullable(
        ApplicationManager.getApplication().getService(PsiDocumentManager.class).getPsiFile(openProject, document));
    }

    return Optional.empty();
  }
}
