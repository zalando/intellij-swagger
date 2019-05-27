package org.zalando.intellij.swagger.index;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import org.junit.Test;
import org.zalando.intellij.swagger.index.openapi.OpenApiIndexService;
import org.zalando.intellij.swagger.index.swagger.SwaggerIndexService;
import org.zalando.intellij.swagger.service.intellij.DumbService;

public class IndexFacadeTest {

  private final OpenApiIndexService fakeOpenApiIndexService = mock(OpenApiIndexService.class);
  private final SwaggerIndexService fakeSwaggerIndexService = mock(SwaggerIndexService.class);
  private final DumbService dumbService = mock(DumbService.class);

  private final IndexFacade indexFacade =
      new IndexFacade(fakeOpenApiIndexService, fakeSwaggerIndexService, dumbService);

  private final VirtualFile fakeVirtualFile = mock(VirtualFile.class);
  private final Project fakeProject = mock(Project.class);

  @Test
  public void thatMainSwaggerFileIsDetected() {
    when(fakeSwaggerIndexService.isMainSpecFile(fakeVirtualFile, fakeProject)).thenReturn(true);

    final boolean isMainSpec = indexFacade.isMainSpecFile(fakeVirtualFile, fakeProject);

    assertThat(isMainSpec, is(true));
  }

  @Test
  public void thatMainOpenApiFileIsDetected() {
    when(fakeOpenApiIndexService.isMainSpecFile(fakeVirtualFile, fakeProject)).thenReturn(true);

    final boolean isMainSpec = indexFacade.isMainSpecFile(fakeVirtualFile, fakeProject);

    assertThat(isMainSpec, is(true));
  }

  @Test
  public void thatFileThatIsNotMainSpecIsDetected() {
    when(fakeOpenApiIndexService.isMainSpecFile(fakeVirtualFile, fakeProject)).thenReturn(false);
    when(fakeSwaggerIndexService.isMainSpecFile(fakeVirtualFile, fakeProject)).thenReturn(false);

    final boolean isMainSpec = indexFacade.isMainSpecFile(fakeVirtualFile, fakeProject);

    assertThat(isMainSpec, is(false));
  }
}
