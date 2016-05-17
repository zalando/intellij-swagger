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

public class YamlDefinitionReferenceTest {

    @Test
    public void thatDefinitionElementIsResolved() throws Exception {
        final YamlTraversal yamlTraversal = mock(YamlTraversal.class);
        final YAMLQuotedText selectedElement = mock(YAMLQuotedText.class);
        final YAMLKeyValue definitionKeyValue = mock(YAMLKeyValue.class);
        final List<YAMLPsiElement> allDefinitions = Lists.newArrayList(definitionKeyValue);
        final YAMLDocument yamlDocument = mock(YAMLDocument.class);
        final YAMLKeyValue rootElement = mock(YAMLKeyValue.class);
        final List<YAMLPsiElement> yamlElements = Lists.newArrayList(rootElement);

        when(rootElement.getYAMLElements()).thenReturn(Lists.newArrayList(definitionKeyValue));
        when(yamlDocument.getYAMLElements()).thenReturn(yamlElements);
        when(selectedElement.getParent()).thenReturn(yamlDocument);
        when(yamlTraversal.getChildPropertiesByName(rootElement, "definitions")).thenReturn(allDefinitions);
        when(definitionKeyValue.getName()).thenReturn("definitionName");

        final YamlDefinitionReference yamlDefinitionReference =
                new YamlDefinitionReference(selectedElement, "definitionName", yamlTraversal);

        assertEquals(definitionKeyValue, yamlDefinitionReference.resolve());
    }

    @Test
    public void thatUnknownDefinitionElementIsResolvedToNull() throws Exception {
        final YamlTraversal yamlTraversal = mock(YamlTraversal.class);
        final YAMLQuotedText selectedElement = mock(YAMLQuotedText.class);
        final YAMLKeyValue definitionKeyValue = mock(YAMLKeyValue.class);
        final YAMLDocument yamlDocument = mock(YAMLDocument.class);
        final YAMLKeyValue rootElement = mock(YAMLKeyValue.class);
        final List<YAMLPsiElement> yamlElements = Lists.newArrayList(rootElement);

        when(rootElement.getYAMLElements()).thenReturn(Lists.newArrayList(definitionKeyValue));
        when(yamlDocument.getYAMLElements()).thenReturn(yamlElements);
        when(selectedElement.getParent()).thenReturn(yamlDocument);
        when(yamlTraversal.getChildPropertiesByName(rootElement, "definitions")).thenReturn(Lists.newArrayList());
        when(definitionKeyValue.getName()).thenReturn("definitionName");

        YamlDefinitionReference yamlDefinitionReference =
                new YamlDefinitionReference(selectedElement, "definitionName", yamlTraversal);

        assertNull(yamlDefinitionReference.resolve());
    }
}
