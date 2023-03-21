package org.zalando.intellij.swagger.ui.provider;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.fileEditor.FileDocumentManagerListener;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiFile;
import java.util.Optional;
import org.jetbrains.annotations.NotNull;
import org.zalando.intellij.swagger.index.IndexFacade;
import org.zalando.intellij.swagger.service.PsiFileService;
import org.zalando.intellij.swagger.service.SwaggerFileService;

public class FileDocumentListener implements FileDocumentManagerListener {

  @Override
  public void beforeDocumentSaving(@NotNull final Document document) {
    final Optional<PsiFile> psiFile =
        ApplicationManager.getApplication().getService(PsiFileService.class).fromDocument(document);

    psiFile.ifPresent(
        file -> {
          IndexFacade indexFacade =
              ApplicationManager.getApplication().getService(IndexFacade.class);
          if (indexFacade.isIndexReady(file.getProject())) {
            final Optional<VirtualFile> specFile = indexFacade.getMainSpecFile(file);

            SwaggerFileService swaggerFileService =
                ApplicationManager.getApplication().getService(SwaggerFileService.class);
            specFile.ifPresent(swaggerFileService::convertSwaggerToHtmlAsync);
          }
        });
  }
}
