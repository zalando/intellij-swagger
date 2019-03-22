package org.zalando.intellij.swagger.index;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import org.junit.Test;
import org.zalando.intellij.swagger.index.openapi.OpenApiIndexService;
import org.zalando.intellij.swagger.index.swagger.SwaggerIndexService;

public class IndexServiceTest {

  private final OpenApiIndexService fakeOpenApiIndexService = mock(OpenApiIndexService.class);
  private final SwaggerIndexService fakeSwaggerIndexService = mock(SwaggerIndexService.class);

  private final IndexService indexService =
      new IndexService(fakeOpenApiIndexService, fakeSwaggerIndexService);

  private final VirtualFile fakeVirtualFile = mock(VirtualFile.class);
  private final Project fakeProject = mock(Project.class);

  @Test
  public void thatMainSwaggerFileIsDetected() {
    when(fakeSwaggerIndexService.isMainSwaggerFile(fakeVirtualFile, fakeProject)).thenReturn(true);

    final boolean isMainSpec = indexService.isMainSpecFile(fakeVirtualFile, fakeProject);

    assertThat(isMainSpec, is(true));
  }

  @Test
  public void thatMainOpenApiFileIsDetected() {
    when(fakeOpenApiIndexService.isMainOpenApiFile(fakeVirtualFile, fakeProject)).thenReturn(true);

    final boolean isMainSpec = indexService.isMainSpecFile(fakeVirtualFile, fakeProject);

    assertThat(isMainSpec, is(true));
  }

  @Test
  public void thatFileThatIsNotMainSpecIsDetected() {
    when(fakeOpenApiIndexService.isMainOpenApiFile(fakeVirtualFile, fakeProject)).thenReturn(false);
    when(fakeSwaggerIndexService.isMainSwaggerFile(fakeVirtualFile, fakeProject)).thenReturn(false);

    final boolean isMainSpec = indexService.isMainSpecFile(fakeVirtualFile, fakeProject);

    assertThat(isMainSpec, is(false));
  }
}
