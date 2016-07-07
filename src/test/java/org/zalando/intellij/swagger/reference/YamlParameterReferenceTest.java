package org.zalando.intellij.swagger.reference;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import com.google.common.collect.Lists;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import org.jetbrains.yaml.psi.YAMLKeyValue;
import org.jetbrains.yaml.psi.YAMLQuotedText;
import org.junit.Test;
import org.zalando.intellij.swagger.traversal.YamlTraversal;

import java.util.List;

public class YamlParameterReferenceTest {

    @Test
    public void thatParameterElementIsResolved() {
        final PsiFile psiFile = mock(PsiFile.class);
        final YamlTraversal yamlTraversal = mock(YamlTraversal.class);
        final YAMLQuotedText selectedElement = mock(YAMLQuotedText.class);
        final YAMLKeyValue parameterKeyValue = mock(YAMLKeyValue.class);
        final List<PsiElement> allParameters = Lists.newArrayList(parameterKeyValue);

        when(selectedElement.getContainingFile()).thenReturn(psiFile);
        when(yamlTraversal.getChildrenOfDefinition("parameters", psiFile)).thenReturn(allParameters);
        when(parameterKeyValue.getName()).thenReturn("parameterName");

        final YamlParameterReference yamlParameterReference =
                new YamlParameterReference(selectedElement, "parameterName", yamlTraversal);

        assertEquals(parameterKeyValue, yamlParameterReference.resolve());
    }

    @Test
    public void thatUnknownParameterElementIsResolvedToNull() {
        final PsiFile psiFile = mock(PsiFile.class);
        final YamlTraversal yamlTraversal = mock(YamlTraversal.class);
        final YAMLQuotedText selectedElement = mock(YAMLQuotedText.class);

        when(yamlTraversal.getChildrenOfDefinition("parameters", psiFile)).thenReturn(Lists.newArrayList());

        final YamlParameterReference yamlParameterReference =
                new YamlParameterReference(selectedElement, "parameterName", yamlTraversal);

        assertNull(yamlParameterReference.resolve());
    }
}
