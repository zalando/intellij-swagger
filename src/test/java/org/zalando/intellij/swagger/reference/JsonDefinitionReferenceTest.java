package org.zalando.intellij.swagger.reference;

import com.google.common.collect.Lists;
import com.intellij.json.psi.JsonProperty;
import com.intellij.json.psi.JsonStringLiteral;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import org.junit.Test;
import org.zalando.intellij.swagger.completion.traversal.JsonTraversal;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class JsonDefinitionReferenceTest {

    @Test
    public void thatDefinitionElementIsResolved() throws Exception {
        final PsiFile psiFile = mock(PsiFile.class);
        final PsiElement firstFileChild = mock(PsiElement.class);
        final JsonStringLiteral jsonStringLiteral = mock(JsonStringLiteral.class);
        final JsonTraversal jsonTraversal = mock(JsonTraversal.class);
        final JsonProperty definitionProperty = mock(JsonProperty.class);
        List<JsonProperty> allDefinitions = Lists.newArrayList(definitionProperty);

        final PsiElement[] fileChildren = new PsiElement[1];
        fileChildren[0] = firstFileChild;

        when(jsonStringLiteral.getContainingFile()).thenReturn(psiFile);
        when(psiFile.getChildren()).thenReturn(fileChildren);
        when(definitionProperty.getName()).thenReturn("definitionName");
        when(jsonTraversal.getChildPropertiesByName(firstFileChild, "definitions")).thenReturn(allDefinitions);

        final JsonDefinitionReference jsonDefinitionReference =
                new JsonDefinitionReference(jsonStringLiteral, "definitionName", jsonTraversal);

        assertEquals(definitionProperty, jsonDefinitionReference.resolve());
    }

    @Test
    public void thatUnknownDefinitionElementIsResolvedToNull() throws Exception {
        final PsiFile psiFile = mock(PsiFile.class);
        final PsiElement firstFileChild = mock(PsiElement.class);
        final JsonStringLiteral jsonStringLiteral = mock(JsonStringLiteral.class);
        final JsonTraversal jsonTraversal = mock(JsonTraversal.class);

        final PsiElement[] fileChildren = new PsiElement[1];
        fileChildren[0] = firstFileChild;

        when(jsonStringLiteral.getContainingFile()).thenReturn(psiFile);
        when(psiFile.getChildren()).thenReturn(fileChildren);
        when(jsonTraversal.getChildPropertiesByName(firstFileChild, "definitions")).thenReturn(Lists.newArrayList());

        final JsonDefinitionReference jsonDefinitionReference =
                new JsonDefinitionReference(jsonStringLiteral, "definitionName", jsonTraversal);

        assertNull(jsonDefinitionReference.resolve());
    }
}
