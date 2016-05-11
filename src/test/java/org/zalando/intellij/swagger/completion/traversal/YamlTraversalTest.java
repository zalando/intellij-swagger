package org.zalando.intellij.swagger.completion.traversal;

import com.intellij.json.psi.JsonProperty;
import com.intellij.psi.PsiElement;
import org.jetbrains.yaml.psi.YAMLFile;
import org.jetbrains.yaml.psi.YAMLKeyValue;
import org.jetbrains.yaml.psi.YAMLMapping;
import org.jetbrains.yaml.psi.YAMLSequenceItem;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class YamlTraversalTest {

    private YamlTraversal yamlTraversal;

    @Before
    public void setUp() throws Exception {
        yamlTraversal = new YamlTraversal();
    }

    @Test
    public void thatRootIsResolved() {
        final PsiElement psiElement1 = mock(PsiElement.class);
        final PsiElement psiElement2 = mock(PsiElement.class);
        final PsiElement psiElement3 = mock(PsiElement.class);
        final PsiElement psiElement4 = mock(PsiElement.class);
        final YAMLFile yamlFile = mock(YAMLFile.class);

        when(psiElement1.getParent()).thenReturn(psiElement2);
        when(psiElement2.getParent()).thenReturn(psiElement3);
        when(psiElement3.getParent()).thenReturn(psiElement4);
        when(psiElement4.getParent()).thenReturn(yamlFile);

        assertTrue(yamlTraversal.isRoot(psiElement1));
    }

    @Test
    public void thatKeyIsResolvedFromYamlMapping() {
        final PsiElement psiElement1 = mock(PsiElement.class);
        final PsiElement psiElement2 = mock(PsiElement.class);
        final YAMLMapping yamlMapping = mock(YAMLMapping.class);

        when(psiElement1.getParent()).thenReturn(psiElement2);
        when(psiElement2.getParent()).thenReturn(yamlMapping);

        assertTrue(yamlTraversal.isKey(psiElement1));
    }

    @Test
    public void thatKeyIsResolvedFromYamlKeyValue() {
        final PsiElement psiElement1 = mock(PsiElement.class);
        final PsiElement psiElement2 = mock(PsiElement.class);
        final YAMLKeyValue yamlKeyValue = mock(YAMLKeyValue.class);

        when(psiElement1.getParent()).thenReturn(psiElement2);
        when(psiElement2.getParent()).thenReturn(yamlKeyValue);

        assertTrue(yamlTraversal.isKey(psiElement1));
    }

    @Test
    public void thatInfoIsResolved() {
        final PsiElement psiElement1 = mock(PsiElement.class);
        final PsiElement psiElement2 = mock(PsiElement.class);
        final YAMLKeyValue yamlKeyValue = mock(YAMLKeyValue.class);

        when(psiElement1.getParent()).thenReturn(psiElement2);
        when(psiElement2.getParent()).thenReturn(yamlKeyValue);
        when(yamlKeyValue.getName()).thenReturn("info");

        assertTrue(yamlTraversal.isInfo(psiElement1));
    }

    @Test
    public void thatLicenseIsResolved() {
        final PsiElement psiElement1 = mock(PsiElement.class);
        final PsiElement psiElement2 = mock(PsiElement.class);
        final YAMLKeyValue yamlKeyValue = mock(YAMLKeyValue.class);

        when(psiElement1.getParent()).thenReturn(psiElement2);
        when(psiElement2.getParent()).thenReturn(yamlKeyValue);
        when(yamlKeyValue.getName()).thenReturn("license");

        assertTrue(yamlTraversal.isLicense(psiElement1));
    }

    @Test
    public void thatPathIsResolved() {
        final PsiElement psiElement1 = mock(PsiElement.class);
        final YAMLKeyValue yamlKeyValue1 = mock(YAMLKeyValue.class);
        final YAMLKeyValue yamlKeyValue2 = mock(YAMLKeyValue.class);

        when(psiElement1.getParent()).thenReturn(yamlKeyValue1);
        when(yamlKeyValue1.getParent()).thenReturn(yamlKeyValue2);
        when(yamlKeyValue2.getName()).thenReturn("paths");

        assertTrue(yamlTraversal.isPath(psiElement1));
    }

    @Test
    public void thatOperationIsResolved() {
        final PsiElement psiElement1 = mock(PsiElement.class);
        final YAMLKeyValue yamlKeyValue1 = mock(YAMLKeyValue.class);
        final YAMLKeyValue yamlKeyValue2 = mock(YAMLKeyValue.class);
        final YAMLKeyValue yamlKeyValue3 = mock(YAMLKeyValue.class);

        when(psiElement1.getParent()).thenReturn(yamlKeyValue1);
        when(yamlKeyValue1.getParent()).thenReturn(yamlKeyValue2);
        when(yamlKeyValue2.getParent()).thenReturn(yamlKeyValue3);
        when(yamlKeyValue3.getName()).thenReturn("paths");

        assertTrue(yamlTraversal.isOperation(psiElement1));
    }

    @Test
    public void thatExternalDocsIsResolved() {
        final PsiElement psiElement1 = mock(PsiElement.class);
        final PsiElement psiElement2 = mock(PsiElement.class);
        final YAMLKeyValue yamlKeyValue = mock(YAMLKeyValue.class);

        when(psiElement1.getParent()).thenReturn(psiElement2);
        when(psiElement2.getParent()).thenReturn(yamlKeyValue);
        when(yamlKeyValue.getName()).thenReturn("externalDocs");

        assertTrue(yamlTraversal.isExternalDocs(psiElement1));
    }

    @Test
    public void thatParametersIsResolved() {
        final PsiElement psiElement = mock(PsiElement.class);
        final YAMLSequenceItem yamlSequenceItem = mock(YAMLSequenceItem.class);
        final YAMLKeyValue yamlKeyValue = mock(YAMLKeyValue.class);

        when(psiElement.getParent()).thenReturn(yamlSequenceItem);
        when(yamlSequenceItem.getParent()).thenReturn(yamlKeyValue);
        when(yamlKeyValue.getName()).thenReturn("parameters");

        assertTrue(yamlTraversal.isParameters(yamlSequenceItem));
    }

    @Test
    public void thatItemsIsResolved() {
        final PsiElement psiElement1 = mock(PsiElement.class);
        final PsiElement psiElement2 = mock(PsiElement.class);
        final YAMLKeyValue yamlKeyValue = mock(YAMLKeyValue.class);

        when(psiElement1.getParent()).thenReturn(psiElement2);
        when(psiElement2.getParent()).thenReturn(yamlKeyValue);
        when(yamlKeyValue.getName()).thenReturn("items");

        assertTrue(yamlTraversal.isItems(psiElement1));
    }

    @Test
    public void thatResponsesIsResolved() {
        final PsiElement psiElement1 = mock(PsiElement.class);
        final PsiElement psiElement2 = mock(PsiElement.class);
        final YAMLKeyValue yamlKeyValue = mock(YAMLKeyValue.class);

        when(psiElement1.getParent()).thenReturn(psiElement2);
        when(psiElement2.getParent()).thenReturn(yamlKeyValue);
        when(yamlKeyValue.getName()).thenReturn("responses");

        assertTrue(yamlTraversal.isResponses(psiElement1));
    }

    @Test
    public void thatResponseIsResolved() {
        final PsiElement psiElement1 = mock(PsiElement.class);
        final YAMLKeyValue yamlKeyValue1 = mock(YAMLKeyValue.class);
        final YAMLKeyValue yamlKeyValue2 = mock(YAMLKeyValue.class);

        when(psiElement1.getParent()).thenReturn(yamlKeyValue1);
        when(yamlKeyValue1.getParent()).thenReturn(yamlKeyValue2);
        when(yamlKeyValue2.getName()).thenReturn("responses");

        assertTrue(yamlTraversal.isResponse(psiElement1));
    }

    @Test
    public void thatHeaderIsResolved() {
        final PsiElement psiElement1 = mock(PsiElement.class);
        final YAMLKeyValue yamlKeyValue1 = mock(YAMLKeyValue.class);
        final YAMLKeyValue yamlKeyValue2 = mock(YAMLKeyValue.class);

        when(psiElement1.getParent()).thenReturn(yamlKeyValue1);
        when(yamlKeyValue1.getParent()).thenReturn(yamlKeyValue2);
        when(yamlKeyValue2.getName()).thenReturn("headers");

        assertTrue(yamlTraversal.isHeader(psiElement1));
    }

    @Test
    public void thatTagIsResolved() {
        final PsiElement psiElement1 = mock(PsiElement.class);
        final PsiElement psiElement2 = mock(PsiElement.class);
        final YAMLKeyValue yamlKeyValue = mock(YAMLKeyValue.class);

        when(psiElement1.getParent()).thenReturn(psiElement2);
        when(psiElement2.getParent()).thenReturn(yamlKeyValue);
        when(yamlKeyValue.getName()).thenReturn("tags");

        assertTrue(yamlTraversal.isTag(psiElement1));
    }

    @Test
    public void thatSecurityDefinitionsIsResolved() {
        final PsiElement psiElement1 = mock(PsiElement.class);
        final YAMLKeyValue yamlKeyValue1 = mock(YAMLKeyValue.class);
        final YAMLKeyValue yamlKeyValue2 = mock(YAMLKeyValue.class);

        when(psiElement1.getParent()).thenReturn(yamlKeyValue1);
        when(yamlKeyValue1.getParent()).thenReturn(yamlKeyValue2);
        when(yamlKeyValue2.getName()).thenReturn("securityDefinitions");

        assertTrue(yamlTraversal.isSecurityDefinition(psiElement1));
    }

    @Test
    public void thatSchemaIsResolved() {
        final PsiElement psiElement1 = mock(PsiElement.class);
        final PsiElement psiElement2 = mock(PsiElement.class);
        final YAMLKeyValue yamlKeyValue = mock(YAMLKeyValue.class);

        when(psiElement1.getParent()).thenReturn(psiElement2);
        when(psiElement2.getParent()).thenReturn(yamlKeyValue);
        when(yamlKeyValue.getName()).thenReturn("schema");

        assertTrue(yamlTraversal.isSchema(psiElement1));
    }

    @Test
    public void thatDefinitionsIsResolved() {
        final PsiElement psiElement1 = mock(PsiElement.class);
        final YAMLKeyValue yamlKeyValue1 = mock(YAMLKeyValue.class);
        final YAMLKeyValue yamlKeyValue2 = mock(YAMLKeyValue.class);

        when(psiElement1.getParent()).thenReturn(yamlKeyValue1);
        when(yamlKeyValue1.getParent()).thenReturn(yamlKeyValue2);
        when(yamlKeyValue2.getName()).thenReturn("definitions");

        assertTrue(yamlTraversal.isDefinitions(psiElement1));
    }
}
