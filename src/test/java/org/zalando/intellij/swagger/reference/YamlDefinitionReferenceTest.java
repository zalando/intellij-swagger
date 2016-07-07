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

public class YamlDefinitionReferenceTest {

    @Test
    public void thatDefinitionElementIsResolved() {
        final PsiFile psiFile = mock(PsiFile.class);
        final YamlTraversal yamlTraversal = mock(YamlTraversal.class);
        final YAMLQuotedText selectedElement = mock(YAMLQuotedText.class);
        final YAMLKeyValue definitionKeyValue = mock(YAMLKeyValue.class);
        final List<PsiElement> allDefinitions = Lists.newArrayList(definitionKeyValue);

        when(selectedElement.getContainingFile()).thenReturn(psiFile);
        when(yamlTraversal.getChildrenOfDefinition("definitions", psiFile)).thenReturn(allDefinitions);
        when(definitionKeyValue.getName()).thenReturn("definitionName");

        final YamlDefinitionReference yamlDefinitionReference =
                new YamlDefinitionReference(selectedElement, "definitionName", yamlTraversal);

        assertEquals(definitionKeyValue, yamlDefinitionReference.resolve());
    }

    @Test
    public void thatUnknownDefinitionElementIsResolvedToNull() {
        final PsiFile psiFile = mock(PsiFile.class);
        final YamlTraversal yamlTraversal = mock(YamlTraversal.class);
        final YAMLQuotedText selectedElement = mock(YAMLQuotedText.class);

        when(yamlTraversal.getChildrenOfDefinition("definitions", psiFile)).thenReturn(Lists.newArrayList());

        YamlDefinitionReference yamlDefinitionReference =
                new YamlDefinitionReference(selectedElement, "definitionName", yamlTraversal);

        assertNull(yamlDefinitionReference.resolve());
    }
}
