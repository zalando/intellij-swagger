package org.zalando.intellij.swagger.service;

import java.util.Optional;

import com.intellij.mock.MockApplication;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.psi.PsiFile;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.*;
import org.zalando.intellij.swagger.service.intellij.PsiDocumentManager;

public class PsiFileServiceTest {

  private final PsiDocumentManager fakePsiDocumentManager = mock(PsiDocumentManager.class);
  private final ProjectManager fakeProjectManager = mock(ProjectManager.class);
  private final Document fakeDocument = mock(Document.class);

  private final PsiFileService service = new PsiFileService();

  @Before
  public void setUp() {
    MockApplication app = MockApplication.setUp(() -> {});
    app.registerService(ProjectManager.class, fakeProjectManager);
    app.registerService(PsiDocumentManager.class, fakePsiDocumentManager);
  }

  @Test
  public void thatEmptyIsReturnedIfNoProjectIsFound() {
    when(fakeProjectManager.getOpenProjects()).thenReturn(new Project[0]);

    final Optional<PsiFile> psiFile = service.fromDocument(fakeDocument);

    assertThat(psiFile, equalTo(Optional.empty()));
  }
}
