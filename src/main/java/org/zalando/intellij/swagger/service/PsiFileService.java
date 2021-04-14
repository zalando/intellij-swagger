package org.zalando.intellij.swagger.service;

import java.util.Optional;

import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.psi.PsiFile;
import org.zalando.intellij.swagger.service.intellij.PsiDocumentManager;

public class PsiFileService {

  public Optional<PsiFile> fromDocument(final Document document) {
    final Project[] openProjects = ServiceManager.getService(ProjectManager.class).getOpenProjects();

    if (openProjects.length > 0) {
      final Project openProject = openProjects[0];

      return Optional.ofNullable(ServiceManager.getService(PsiDocumentManager.class).getPsiFile(openProject, document));
    }

    return Optional.empty();
  }
}
