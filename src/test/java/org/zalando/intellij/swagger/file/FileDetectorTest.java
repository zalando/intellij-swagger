package org.zalando.intellij.swagger.file;

import com.google.common.collect.Lists;
import com.intellij.json.psi.JsonFile;
import com.intellij.json.psi.JsonProperty;
import com.intellij.json.psi.JsonValue;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import org.jetbrains.yaml.psi.YAMLDocument;
import org.jetbrains.yaml.psi.YAMLFile;
import org.jetbrains.yaml.psi.YAMLKeyValue;
import org.jetbrains.yaml.psi.YAMLValue;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class FileDetectorTest {

    private FileDetector fileDetector;

    @Before
    public void setUp() throws Exception {
        fileDetector = new FileDetector();
    }

    @Test
    public void thatIsNotSwaggerJsonFileBasedOnFileName() throws Exception {
        final PsiFile jsonFile = mock(PsiFile.class);
        when(jsonFile.getName()).thenReturn("swagger.json");

        assertFalse(fileDetector.isMainSwaggerJsonFile(jsonFile));
    }

    @Test
    public void thatIsNotSwaggerYamlFileBasedOnFileName() throws Exception {
        final PsiFile yamlFile = mock(PsiFile.class);
        when(yamlFile.getName()).thenReturn("swagger.yaml");

        assertFalse(fileDetector.isMainSwaggerYamlFile(yamlFile));
    }

    @Test
    public void thatIsSwaggerJsonFileBasedOnFileContent() throws Exception {
        final JsonFile jsonFile = mock(JsonFile.class);
        final JsonValue jsonValue = mock(JsonValue.class);
        final JsonProperty jsonProperty = mock(JsonProperty.class);

        when(jsonFile.getName()).thenReturn("file.json");
        when(jsonFile.getTopLevelValue()).thenReturn(jsonValue);
        when(jsonValue.getChildren()).thenReturn(new PsiElement[]{jsonProperty});
        when(jsonProperty.getName()).thenReturn("swagger");

        assertTrue(fileDetector.isMainSwaggerJsonFile(jsonFile));
    }

    @Test
    public void thatIsOpenApiJsonFileBasedOnFileContent() throws Exception {
        final JsonFile jsonFile = mock(JsonFile.class);
        final JsonValue jsonValue = mock(JsonValue.class);
        final JsonProperty jsonProperty = mock(JsonProperty.class);

        when(jsonFile.getName()).thenReturn("file.json");
        when(jsonFile.getTopLevelValue()).thenReturn(jsonValue);
        when(jsonValue.getChildren()).thenReturn(new PsiElement[]{jsonProperty});
        when(jsonProperty.getName()).thenReturn("openapi");

        assertTrue(fileDetector.isMainOpenApiJsonFile(jsonFile));
    }

    @Test
    public void thatIsSwaggerYamlFileBasedOnFileContent() throws Exception {
        YAMLFile yamlFile = mock(YAMLFile.class);
        YAMLDocument yamlDocument = mock(YAMLDocument.class);
        YAMLValue yamlValue = mock(YAMLValue.class);
        YAMLKeyValue yamlKeyValue = mock(YAMLKeyValue.class);

        when(yamlFile.getName()).thenReturn("file.yaml");
        when(yamlFile.getDocuments()).thenReturn(Lists.newArrayList(yamlDocument));
        when(yamlDocument.getTopLevelValue()).thenReturn(yamlValue);
        when(yamlValue.getYAMLElements()).thenReturn(Lists.newArrayList(yamlKeyValue));
        when(yamlKeyValue.getName()).thenReturn("swagger");

        assertTrue(fileDetector.isMainSwaggerYamlFile(yamlFile));
    }

    @Test
    public void thatIsOpenApiYamlFileBasedOnFileContent() throws Exception {
        YAMLFile yamlFile = mock(YAMLFile.class);
        YAMLDocument yamlDocument = mock(YAMLDocument.class);
        YAMLValue yamlValue = mock(YAMLValue.class);
        YAMLKeyValue yamlKeyValue = mock(YAMLKeyValue.class);

        when(yamlFile.getName()).thenReturn("file.yaml");
        when(yamlFile.getDocuments()).thenReturn(Lists.newArrayList(yamlDocument));
        when(yamlDocument.getTopLevelValue()).thenReturn(yamlValue);
        when(yamlValue.getYAMLElements()).thenReturn(Lists.newArrayList(yamlKeyValue));
        when(yamlKeyValue.getName()).thenReturn("openapi");

        assertTrue(fileDetector.isMainOpenApiYamlFile(yamlFile));
    }

    @Test
    public void thatYamlFileExtensionIsSwaggerContentCompatible() throws Exception {
        VirtualFile yamlFile = mock(VirtualFile.class);

        when(yamlFile.getName()).thenReturn("file.yaml");

        assertTrue(fileDetector.isSwaggerContentCompatible(yamlFile));
    }

    @Test
    public void thatYmlFileExtensionIsSwaggerContentCompatible() throws Exception {
        VirtualFile yamlFile = mock(VirtualFile.class);

        when(yamlFile.getName()).thenReturn("file.yml");

        assertTrue(fileDetector.isSwaggerContentCompatible(yamlFile));
    }

    @Test
    public void thatJsonFileExtensionIsSwaggerContentCompatible() throws Exception {
        VirtualFile yamlFile = mock(VirtualFile.class);

        when(yamlFile.getName()).thenReturn("file.json");

        assertTrue(fileDetector.isSwaggerContentCompatible(yamlFile));
    }

    @Test
    public void thatSimilarFileExtensionsAreNotSwaggerContentCompatible() throws Exception {
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
