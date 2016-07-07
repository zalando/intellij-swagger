package org.zalando.intellij.swagger.reference;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import com.google.common.collect.Lists;
import com.intellij.json.psi.JsonProperty;
import com.intellij.json.psi.JsonStringLiteral;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import org.junit.Test;
import org.zalando.intellij.swagger.traversal.JsonTraversal;

import java.util.List;

public class JsonDefinitionReferenceTest {

    @Test
    public void thatDefinitionElementIsResolved() {
        final PsiFile psiFile = mock(PsiFile.class);
        final JsonStringLiteral jsonStringLiteral = mock(JsonStringLiteral.class);
        final JsonTraversal jsonTraversal = mock(JsonTraversal.class);
        final JsonProperty definitionProperty = mock(JsonProperty.class);
        List<PsiElement> allDefinitions = Lists.newArrayList(definitionProperty);

        when(jsonStringLiteral.getContainingFile()).thenReturn(psiFile);
        when(definitionProperty.getName()).thenReturn("definitionName");
        when(jsonTraversal.getChildrenOfDefinition("definitions", psiFile)).thenReturn(allDefinitions);

        final JsonDefinitionReference jsonDefinitionReference =
                new JsonDefinitionReference(jsonStringLiteral, "definitionName", jsonTraversal);

        assertEquals(definitionProperty, jsonDefinitionReference.resolve());
    }

    @Test
    public void thatUnknownDefinitionElementIsResolvedToNull() {
        final PsiFile psiFile = mock(PsiFile.class);
        final JsonStringLiteral jsonStringLiteral = mock(JsonStringLiteral.class);
        final JsonTraversal jsonTraversal = mock(JsonTraversal.class);

        when(jsonStringLiteral.getContainingFile()).thenReturn(psiFile);
        when(jsonTraversal.getChildrenOfDefinition("definitions", psiFile)).thenReturn(Lists.newArrayList());

        final JsonDefinitionReference jsonDefinitionReference =
                new JsonDefinitionReference(jsonStringLiteral, "definitionName", jsonTraversal);

        assertNull(jsonDefinitionReference.resolve());
    }
}
