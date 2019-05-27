package org.zalando.intellij.swagger.ui.provider;

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

  private final IndexFacade indexFacade;
  private final SwaggerFileService swaggerFileService;
  private final PsiFileService psiFileService;

  public FileDocumentListener(
      final IndexFacade indexFacade,
      final SwaggerFileService swaggerFileService,
      final PsiFileService psiFileService) {
    this.indexFacade = indexFacade;
    this.swaggerFileService = swaggerFileService;
    this.psiFileService = psiFileService;
  }

  @Override
  public void beforeDocumentSaving(@NotNull final Document document) {
    final Optional<PsiFile> psiFile = psiFileService.fromDocument(document);

    psiFile.ifPresent(
        file -> {
          if (indexFacade.isIndexReady(file.getProject())) {
            final Optional<VirtualFile> specFile = indexFacade.getMainSpecFile(file);

            specFile.ifPresent(swaggerFileService::convertSwaggerToHtmlAsync);
          }
        });
  }
}
