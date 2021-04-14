package org.zalando.intellij.swagger.ui.provider;

import java.util.Optional;

import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.fileEditor.FileDocumentManagerListener;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiFile;
import org.jetbrains.annotations.NotNull;
import org.zalando.intellij.swagger.index.IndexFacade;
import org.zalando.intellij.swagger.service.PsiFileService;
import org.zalando.intellij.swagger.service.SwaggerFileService;

public class FileDocumentListener implements FileDocumentManagerListener {

  @Override
  public void beforeDocumentSaving(@NotNull final Document document) {
    final Optional<PsiFile> psiFile = ServiceManager.getService(PsiFileService.class).fromDocument(document);

    psiFile.ifPresent(
        file -> {
          IndexFacade indexFacade = ServiceManager.getService(IndexFacade.class);
          if (indexFacade.isIndexReady(file.getProject())) {
            final Optional<VirtualFile> specFile = indexFacade.getMainSpecFile(file);

            SwaggerFileService swaggerFileService = ServiceManager.getService(SwaggerFileService.class);
            specFile.ifPresent(swaggerFileService::convertSwaggerToHtmlAsync);
          }
        });
  }
}
