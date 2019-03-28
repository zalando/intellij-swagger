package org.zalando.intellij.swagger.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.intellij.openapi.vfs.VirtualFile;
import java.io.File;
import java.net.URL;
import org.junit.Before;
import org.junit.Test;

public class JsonBuilderTest {

  private final JsonBuilderService jsonBuilderService = new JsonBuilderService();

  private VirtualFile fakeVirtualFile = mock(VirtualFile.class);
  private VirtualFile fakeVirtualFile2 = mock(VirtualFile.class);
  private VirtualFile fakeVirtualFile3 = mock(VirtualFile.class);

  @Before
  public void before() {
    when(fakeVirtualFile.getParent()).thenReturn(fakeVirtualFile);
    when(fakeVirtualFile2.getParent()).thenReturn(fakeVirtualFile2);
    when(fakeVirtualFile3.getParent()).thenReturn(fakeVirtualFile3);
  }

  @Test
  public void thatFileWithoutFileReferencesIsHandled() {
    final JsonNode jsonNode = getJsonNode("petstore.json");

    JsonNode result = jsonBuilderService.buildFromSpec(jsonNode, fakeVirtualFile);

    JsonNode expected = getJsonNode("petstore.json");

    assertEquals(expected, result);
  }

  @Test
  public void thatFileWithDepthOneReferenceIsHandled() {
    when(fakeVirtualFile.findFileByRelativePath(anyString())).thenReturn(fakeVirtualFile2);
    when(fakeVirtualFile2.getPath()).thenReturn(getUrl("partial/Error.json").getPath());

    final JsonNode jsonNode = getJsonNode("petstore_with_file_ref_1.json");

    JsonNode result = jsonBuilderService.buildFromSpec(jsonNode, fakeVirtualFile);

    JsonNode expected = getJsonNode("petstore_with_file_ref_1_after.json");

    assertEquals(expected, result);
  }

  @Test
  public void thatFileWithDepthTwoReferenceIsHandled() {
    when(fakeVirtualFile.findFileByRelativePath("partial/Error_2.json"))
        .thenReturn(fakeVirtualFile2);
    when(fakeVirtualFile2.getPath()).thenReturn(getUrl("partial/Error_2.json").getPath());

    when(fakeVirtualFile2.findFileByRelativePath("Error.json")).thenReturn(fakeVirtualFile3);
    when(fakeVirtualFile3.getPath()).thenReturn(getUrl("partial/Error.json").getPath());

    final JsonNode jsonNode = getJsonNode("petstore_with_file_ref_2.json");

    JsonNode result = jsonBuilderService.buildFromSpec(jsonNode, fakeVirtualFile);

    JsonNode expected = getJsonNode("petstore_with_file_ref_2_after.json");

    assertEquals(expected, result);
  }

  @Test
  public void thatMissingFileReferenceIsHandled() {
    when(fakeVirtualFile.findFileByRelativePath("partial/Error.json")).thenReturn(null);

    final JsonNode jsonNode = getJsonNode("petstore_with_file_ref_3.json");

    JsonNode result = jsonBuilderService.buildFromSpec(jsonNode, fakeVirtualFile);

    JsonNode expected = getJsonNode("petstore_with_file_ref_3_after.json");

    assertEquals(expected, result);
  }

  @Test
  public void thatMissingJsonElementReferenceIsHandled() {
    when(fakeVirtualFile.findFileByRelativePath(anyString())).thenReturn(fakeVirtualFile2);
    when(fakeVirtualFile2.getPath()).thenReturn(getUrl("partial/Error.json").getPath());

    final JsonNode jsonNode = getJsonNode("petstore_with_file_ref_4.json");

    JsonNode result = jsonBuilderService.buildFromSpec(jsonNode, fakeVirtualFile);

    JsonNode expected = getJsonNode("petstore_with_file_ref_4_after.json");

    assertEquals(expected, result);
  }

  @Test
  public void thatJsonElementReferenceIsHandled() {
    when(fakeVirtualFile.findFileByRelativePath(anyString())).thenReturn(fakeVirtualFile2);
    when(fakeVirtualFile2.getPath()).thenReturn(getUrl("partial/Error_5.json").getPath());

    final JsonNode jsonNode = getJsonNode("petstore_with_file_ref_6.json");

    JsonNode result = jsonBuilderService.buildFromSpec(jsonNode, fakeVirtualFile);

    JsonNode expected = getJsonNode("petstore_with_file_ref_6_after.json");

    assertEquals(expected, result);
  }

  @Test
  public void thatCircularReferenceIsHandled() {
    when(fakeVirtualFile.findFileByRelativePath("partial/Error_3.json"))
        .thenReturn(fakeVirtualFile2);

    when(fakeVirtualFile2.getPath()).thenReturn(getUrl("partial/Error_3.json").getPath());
    when(fakeVirtualFile2.findFileByRelativePath("Error_4.json")).thenReturn(fakeVirtualFile3);

    when(fakeVirtualFile3.getPath()).thenReturn(getUrl("partial/Error_4.json").getPath());
    when(fakeVirtualFile3.findFileByRelativePath("Error_3.json")).thenReturn(fakeVirtualFile2);

    final JsonNode jsonNode = getJsonNode("petstore_with_file_ref_5.json");

    JsonNode result = jsonBuilderService.buildFromSpec(jsonNode, fakeVirtualFile);

    JsonNode expected = getJsonNode("petstore_with_file_ref_5_after.json");

    assertEquals(expected, result);
  }

  @Test
  public void thatReferencesInArrayAreHandled() {
    final VirtualFile specFile = mock(VirtualFile.class);
    final VirtualFile parameter1File = mock(VirtualFile.class);
    final VirtualFile parameter2File = mock(VirtualFile.class);
    final VirtualFile parameter3File = mock(VirtualFile.class);
    final VirtualFile parameterSchemaFile = mock(VirtualFile.class);

    when(specFile.getParent()).thenReturn(specFile);
    when(parameter1File.getParent()).thenReturn(parameter1File);
    when(parameter2File.getParent()).thenReturn(parameter2File);
    when(parameter3File.getParent()).thenReturn(parameter3File);
    when(parameterSchemaFile.getParent()).thenReturn(parameterSchemaFile);

    when(specFile.findFileByRelativePath("partial/Parameter_1.json")).thenReturn(parameter1File);
    when(parameter1File.getPath()).thenReturn(getUrl("partial/Parameter_1.json").getPath());

    when(specFile.findFileByRelativePath("partial/Parameter_2.json")).thenReturn(parameter2File);
    when(parameter2File.getPath()).thenReturn(getUrl("partial/Parameter_2.json").getPath());

    when(specFile.findFileByRelativePath("partial/Parameter_3.json")).thenReturn(parameter3File);
    when(parameter3File.getPath()).thenReturn(getUrl("partial/Parameter_3.json").getPath());

    when(parameter3File.findFileByRelativePath("Parameter_schema.json"))
        .thenReturn(parameterSchemaFile);
    when(parameterSchemaFile.getPath())
        .thenReturn(getUrl("partial/Parameter_schema.json").getPath());

    final JsonNode jsonNode = getJsonNode("petstore_with_file_ref_7.json");

    JsonNode result = jsonBuilderService.buildFromSpec(jsonNode, specFile);

    JsonNode expected = getJsonNode("petstore_with_file_ref_7_after.json");

    assertEquals(expected, result);
  }

  @Test
  public void thatCircularReferenceInArrayIsHandled() {
    final VirtualFile specFile = mock(VirtualFile.class);
    final VirtualFile parameter4File = mock(VirtualFile.class);

    when(specFile.getParent()).thenReturn(specFile);
    when(parameter4File.getParent()).thenReturn(parameter4File);

    when(specFile.findFileByRelativePath("partial/Parameter_4.json")).thenReturn(parameter4File);
    when(parameter4File.getPath()).thenReturn(getUrl("partial/Parameter_4.json").getPath());

    when(parameter4File.findFileByRelativePath("Parameter_4.json")).thenReturn(parameter4File);

    final JsonNode jsonNode = getJsonNode("petstore_with_file_ref_8.json");

    JsonNode result = jsonBuilderService.buildFromSpec(jsonNode, specFile);

    JsonNode expected = getJsonNode("petstore_with_file_ref_8_after.json");

    assertEquals(expected, result);
  }

  @Test
  public void thatYamlFileWithDepthOneReferenceIsHandled() {
    when(fakeVirtualFile.findFileByRelativePath(anyString())).thenReturn(fakeVirtualFile2);
    when(fakeVirtualFile2.getPath()).thenReturn(getUrl("partial/Error.yaml").getPath());

    final JsonNode jsonNode = getJsonNode("petstore_with_file_ref_1.yaml");

    JsonNode result = jsonBuilderService.buildFromSpec(jsonNode, fakeVirtualFile);

    JsonNode expected = getJsonNode("petstore_with_file_ref_1_after.json");

    assertEquals(expected, result);
  }

  @Test
  public void thatYamlFileWithDepthTwoReferenceIsHandled() {
    when(fakeVirtualFile.findFileByRelativePath("partial/Error_2.yaml"))
        .thenReturn(fakeVirtualFile2);
    when(fakeVirtualFile2.getPath()).thenReturn(getUrl("partial/Error_2.yaml").getPath());

    when(fakeVirtualFile2.findFileByRelativePath("Error.yaml")).thenReturn(fakeVirtualFile3);
    when(fakeVirtualFile3.getPath()).thenReturn(getUrl("partial/Error.yaml").getPath());

    final JsonNode jsonNode = getJsonNode("petstore_with_file_ref_2.yaml");

    JsonNode result = jsonBuilderService.buildFromSpec(jsonNode, fakeVirtualFile);

    JsonNode expected = getJsonNode("petstore_with_file_ref_2_after.json");

    assertEquals(expected, result);
  }

  @Test
  public void thatMissingYamlFileReferenceIsHandled() {
    when(fakeVirtualFile.findFileByRelativePath("partial/Error.yaml")).thenReturn(null);

    final JsonNode jsonNode = getJsonNode("petstore_with_file_ref_3.yaml");

    JsonNode result = jsonBuilderService.buildFromSpec(jsonNode, fakeVirtualFile);

    JsonNode expected = getJsonNode("petstore_with_file_ref_3_yaml_after.json");

    assertEquals(expected, result);
  }

  @Test
  public void thatMissingYamlElementReferenceIsHandled() {
    when(fakeVirtualFile.findFileByRelativePath(anyString())).thenReturn(fakeVirtualFile2);
    when(fakeVirtualFile2.getPath()).thenReturn(getUrl("partial/Error.yaml").getPath());

    final JsonNode jsonNode = getJsonNode("petstore_with_file_ref_4.yaml");

    JsonNode result = jsonBuilderService.buildFromSpec(jsonNode, fakeVirtualFile);

    JsonNode expected = getJsonNode("petstore_with_file_ref_4_yaml_after.json");

    assertEquals(expected, result);
  }

  @Test
  public void thatYamlElementReferenceIsHandled() {
    when(fakeVirtualFile.findFileByRelativePath(anyString())).thenReturn(fakeVirtualFile2);
    when(fakeVirtualFile2.getPath()).thenReturn(getUrl("partial/Error_5.yaml").getPath());

    final JsonNode jsonNode = getJsonNode("petstore_with_file_ref_6.yaml");

    JsonNode result = jsonBuilderService.buildFromSpec(jsonNode, fakeVirtualFile);

    JsonNode expected = getJsonNode("petstore_with_file_ref_6_after.json");

    assertEquals(expected, result);
  }

  @Test
  public void thatCircularYamlReferenceIsHandled() {
    when(fakeVirtualFile.findFileByRelativePath("partial/Error_3.yaml"))
        .thenReturn(fakeVirtualFile2);

    when(fakeVirtualFile2.getPath()).thenReturn(getUrl("partial/Error_3.yaml").getPath());
    when(fakeVirtualFile2.findFileByRelativePath("Error_4.yaml")).thenReturn(fakeVirtualFile3);

    when(fakeVirtualFile3.getPath()).thenReturn(getUrl("partial/Error_4.yaml").getPath());
    when(fakeVirtualFile3.findFileByRelativePath("Error_3.yaml")).thenReturn(fakeVirtualFile2);

    final JsonNode jsonNode = getJsonNode("petstore_with_file_ref_5.yaml");

    JsonNode result = jsonBuilderService.buildFromSpec(jsonNode, fakeVirtualFile);

    JsonNode expected = getJsonNode("petstore_with_file_ref_5_after.json");

    assertEquals(expected, result);
  }

  @Test
  public void thatLocalReferenceInReferencedFileIsHandled() {
    final VirtualFile specFile = mock(VirtualFile.class);
    final VirtualFile refFile = mock(VirtualFile.class);

    when(specFile.getParent()).thenReturn(specFile);

    when(specFile.findFileByRelativePath("partial/Pets.json")).thenReturn(refFile);
    when(refFile.getPath()).thenReturn(getUrl("partial/Pets.json").getPath());

    final JsonNode jsonNode = getJsonNode("petstore_with_file_ref_9.json");

    JsonNode result = jsonBuilderService.buildFromSpec(jsonNode, specFile);

    JsonNode expected = getJsonNode("petstore_with_file_ref_9_after.json");

    assertEquals(expected, result);
  }

  private JsonNode getJsonNode(final String fileName) {
    try {
      final URL url = getUrl(fileName);
      final File file = new File(url.toURI());

      final ObjectMapper mapper = new ObjectMapper(new YAMLFactory());

      return mapper.readTree(file);
    } catch (final Exception e) {
      throw new RuntimeException(e);
    }
  }

  private URL getUrl(final String fileName) {
    return ClassLoader.getSystemClassLoader().getResource("service/json/" + fileName);
  }
}
