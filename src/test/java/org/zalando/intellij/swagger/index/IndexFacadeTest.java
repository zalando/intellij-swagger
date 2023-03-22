package org.zalando.intellij.swagger.index;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.*;

import com.intellij.mock.MockApplication;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.zalando.intellij.swagger.index.openapi.OpenApiIndexService;
import org.zalando.intellij.swagger.index.swagger.SwaggerIndexService;
import org.zalando.intellij.swagger.service.intellij.DumbService;

@Ignore
public class IndexFacadeTest {

  private final OpenApiIndexService fakeOpenApiIndexService = mock(OpenApiIndexService.class);
  private final SwaggerIndexService fakeSwaggerIndexService = mock(SwaggerIndexService.class);
  private final DumbService dumbService = mock(DumbService.class);

  private final IndexFacade indexFacade = new IndexFacade();

  private final VirtualFile fakeVirtualFile = mock(VirtualFile.class);
  private final Project fakeProject = mock(Project.class);

  @Before
  public void setUp() throws Exception {
    MockApplication app = MockApplication.setUp(() -> {});
    app.registerService(OpenApiIndexService.class, fakeOpenApiIndexService);
    app.registerService(SwaggerIndexService.class, fakeSwaggerIndexService);
    app.registerService(DumbService.class, dumbService);
  }

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
