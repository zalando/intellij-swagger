package org.zalando.intellij.swagger.ui.provider;

import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.fileEditor.FileDocumentManagerAdapter;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiDocumentManager;
import com.intellij.psi.PsiFile;
import java.util.Optional;
import org.jetbrains.annotations.NotNull;
import org.zalando.intellij.swagger.index.IndexService;
import org.zalando.intellij.swagger.service.SwaggerFileService;

public class FileDocumentListener extends FileDocumentManagerAdapter {

  private final IndexService indexService = new IndexService();

  @Override
  public void beforeDocumentSaving(@NotNull final Document document) {
    SwaggerFileService swaggerFileService = ServiceManager.getService(SwaggerFileService.class);
    final Project[] openProjects = ProjectManager.getInstance().getOpenProjects();

    if (openProjects.length > 0) {
      final PsiFile psiFile = PsiDocumentManager.getInstance(openProjects[0]).getPsiFile(document);

      if (psiFile != null) {
        final Optional<VirtualFile> specFile = indexService.getMainSpecFile(psiFile);

        specFile.ifPresent(swaggerFileService::convertSwaggerToHtmlAsync);
      }
    }
  }
}
