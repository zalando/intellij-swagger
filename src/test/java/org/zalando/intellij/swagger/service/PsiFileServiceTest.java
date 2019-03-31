package org.zalando.intellij.swagger.service;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.intellij.openapi.editor.Document;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.psi.PsiFile;
import java.util.Optional;
import org.junit.Test;
import org.zalando.intellij.swagger.service.intellij.PsiDocumentManager;

public class PsiFileServiceTest {

  private PsiDocumentManager fakePsiDocumentManager = mock(PsiDocumentManager.class);
  private ProjectManager fakeProjectManager = mock(ProjectManager.class);
  private final Document fakeDocument = mock(Document.class);

  private final PsiFileService service =
      new PsiFileService(fakeProjectManager, fakePsiDocumentManager);

  @Test
  public void thatEmptyIsReturnedIfNoProjectIsFound() {
    when(fakeProjectManager.getOpenProjects()).thenReturn(new Project[0]);

    final Optional<PsiFile> psiFile = service.fromDocument(fakeDocument);

    assertThat(psiFile, equalTo(Optional.empty()));
  }
}
