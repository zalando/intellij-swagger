package org.zalando.intellij.swagger.file;

import com.google.common.collect.Lists;
import com.intellij.json.psi.JsonFile;
import com.intellij.json.psi.JsonProperty;
import com.intellij.json.psi.JsonValue;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import org.jetbrains.yaml.psi.YAMLDocument;
import org.jetbrains.yaml.psi.YAMLFile;
import org.jetbrains.yaml.psi.YAMLKeyValue;
import org.jetbrains.yaml.psi.YAMLPsiElement;
import org.jetbrains.yaml.psi.YAMLValue;
import org.junit.Before;
import org.junit.Test;

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
    public void thatIsSwaggerJsonFileBasedOnFileName() throws Exception {
        final PsiFile jsonFile = mock(PsiFile.class);
        when(jsonFile.getName()).thenReturn("swagger.json");

        assertTrue(fileDetector.isSwaggerJsonFile(jsonFile));
    }

    @Test
    public void thatIsSwaggerYamlFileBasedOnFileName() throws Exception {
        final PsiFile jsonFile = mock(PsiFile.class);
        when(jsonFile.getName()).thenReturn("swagger.yaml");

        assertTrue(fileDetector.isSwaggerYamlFile(jsonFile));
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

        assertTrue(fileDetector.isSwaggerJsonFile(jsonFile));
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

        assertTrue(fileDetector.isSwaggerYamlFile(yamlFile));
    }

}
