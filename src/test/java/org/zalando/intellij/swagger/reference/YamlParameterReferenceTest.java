package org.zalando.intellij.swagger.reference;

import com.google.common.collect.Lists;
import org.jetbrains.yaml.psi.YAMLDocument;
import org.jetbrains.yaml.psi.YAMLKeyValue;
import org.jetbrains.yaml.psi.YAMLPsiElement;
import org.jetbrains.yaml.psi.YAMLQuotedText;
import org.junit.Test;
import org.zalando.intellij.swagger.completion.traversal.YamlTraversal;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class YamlParameterReferenceTest {

    @Test
    public void thatParameterElementIsResolved() throws Exception {
        final YamlTraversal yamlTraversal = mock(YamlTraversal.class);
        final YAMLQuotedText selectedElement = mock(YAMLQuotedText.class);
        final YAMLKeyValue parameterKeyValue = mock(YAMLKeyValue.class);
        final List<YAMLPsiElement> allParamaters = Lists.newArrayList(parameterKeyValue);
        final YAMLDocument yamlDocument = mock(YAMLDocument.class);
        final YAMLKeyValue rootElement = mock(YAMLKeyValue.class);
        final List<YAMLPsiElement> yamlElements = Lists.newArrayList(rootElement);

        when(rootElement.getYAMLElements()).thenReturn(Lists.newArrayList(parameterKeyValue));
        when(yamlDocument.getYAMLElements()).thenReturn(yamlElements);
        when(selectedElement.getParent()).thenReturn(yamlDocument);
        when(yamlTraversal.getChildPropertiesByName(rootElement, "parameters")).thenReturn(allParamaters);
        when(parameterKeyValue.getName()).thenReturn("parameterName");

        final YamlParameterReference yamlParameterReference =
                new YamlParameterReference(selectedElement, "parameterName", yamlTraversal);

        assertEquals(parameterKeyValue, yamlParameterReference.resolve());
    }

    @Test
    public void thatUnknownParameterElementIsResolvedToNull() throws Exception {
        final YamlTraversal yamlTraversal = mock(YamlTraversal.class);
        final YAMLQuotedText selectedElement = mock(YAMLQuotedText.class);
        final YAMLKeyValue parameterKeyValue = mock(YAMLKeyValue.class);
        final YAMLDocument yamlDocument = mock(YAMLDocument.class);
        final YAMLKeyValue rootElement = mock(YAMLKeyValue.class);
        final List<YAMLPsiElement> yamlElements = Lists.newArrayList(rootElement);

        when(rootElement.getYAMLElements()).thenReturn(Lists.newArrayList(parameterKeyValue));
        when(yamlDocument.getYAMLElements()).thenReturn(yamlElements);
        when(selectedElement.getParent()).thenReturn(yamlDocument);
        when(yamlTraversal.getChildPropertiesByName(rootElement, "parameters")).thenReturn(Lists.newArrayList());
        when(parameterKeyValue.getName()).thenReturn("parameterName");

        final YamlParameterReference yamlParameterReference =
                new YamlParameterReference(selectedElement, "parameterName", yamlTraversal);

        assertNull(yamlParameterReference.resolve());
    }
}
