package org.zalando.intellij.swagger.ui.provider;

import com.intellij.openapi.editor.Document;
import com.intellij.openapi.fileEditor.FileDocumentManagerListener;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiFile;
import java.util.Optional;
import org.jetbrains.annotations.NotNull;
import org.zalando.intellij.swagger.index.IndexService;
import org.zalando.intellij.swagger.service.PsiFileService;
import org.zalando.intellij.swagger.service.SwaggerFileService;
import org.zalando.intellij.swagger.service.intellij.DumbService;

public class FileDocumentListener implements FileDocumentManagerListener {

  private final IndexService indexService;
  private final SwaggerFileService swaggerFileService;
  private final PsiFileService psiFileService;
  private final DumbService dumbService;

  public FileDocumentListener(
      final IndexService indexService,
      final SwaggerFileService swaggerFileService,
      final PsiFileService psiFileService,
      final DumbService dumbService) {
    this.indexService = indexService;
    this.swaggerFileService = swaggerFileService;
    this.psiFileService = psiFileService;
    this.dumbService = dumbService;
  }

  @Override
  public void beforeDocumentSaving(@NotNull final Document document) {
    final Optional<PsiFile> psiFile = psiFileService.fromDocument(document);

    psiFile.ifPresent(
        file -> {
          if (indexIsReady(file.getProject())) {
            final Optional<VirtualFile> specFile = indexService.getMainSpecFile(file);

            specFile.ifPresent(swaggerFileService::convertSwaggerToHtmlAsync);
          }
        });
  }

  private boolean indexIsReady(final Project project) {
    return !dumbService.isDumb(project);
  }
}
