package org.zalando.intellij.swagger.file;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiFile;
import org.junit.Before;
import org.junit.Test;

public class FileDetectorTest {

  private FileDetector fileDetector;

  @Before
  public void setUp() {
    fileDetector = new FileDetector();
  }

  @Test
  public void thatIsNotSwaggerJsonFileBasedOnFileName() {
    final PsiFile jsonFile = mock(PsiFile.class);
    when(jsonFile.getName()).thenReturn("swagger.json");

    assertFalse(fileDetector.isMainSwaggerJsonFile(jsonFile));
  }

  @Test
  public void thatIsNotSwaggerYamlFileBasedOnFileName() {
    final PsiFile yamlFile = mock(PsiFile.class);
    when(yamlFile.getName()).thenReturn("swagger.yaml");

    assertFalse(fileDetector.isMainSwaggerYamlFile(yamlFile));
  }

  @Test
  public void thatYamlFileExtensionIsSwaggerContentCompatible() {
    VirtualFile yamlFile = mock(VirtualFile.class);

    when(yamlFile.getName()).thenReturn("file.yaml");

    assertTrue(fileDetector.isSwaggerContentCompatible(yamlFile));
  }

  @Test
  public void thatYmlFileExtensionIsSwaggerContentCompatible() {
    VirtualFile yamlFile = mock(VirtualFile.class);

    when(yamlFile.getName()).thenReturn("file.yml");

    assertTrue(fileDetector.isSwaggerContentCompatible(yamlFile));
  }

  @Test
  public void thatJsonFileExtensionIsSwaggerContentCompatible() {
    VirtualFile yamlFile = mock(VirtualFile.class);

    when(yamlFile.getName()).thenReturn("file.json");

    assertTrue(fileDetector.isSwaggerContentCompatible(yamlFile));
  }

  @Test
  public void thatSimilarFileExtensionsAreNotSwaggerContentCompatible() {
    VirtualFile file = mock(VirtualFile.class);

    when(file.getName()).thenReturn("file.libyaml");
    assertFalse(fileDetector.isSwaggerContentCompatible(file));

    when(file.getName()).thenReturn("file.libyml");
    assertFalse(fileDetector.isSwaggerContentCompatible(file));

    when(file.getName()).thenReturn("i-love-yaml");
    assertFalse(fileDetector.isSwaggerContentCompatible(file));

    when(file.getName()).thenReturn("pseudo-json");
    assertFalse(fileDetector.isSwaggerContentCompatible(file));
  }
}
