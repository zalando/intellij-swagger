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

public class JsonParameterReferenceTest {

    @Test
    public void thatParameterElementIsResolved() throws Exception {
        final PsiFile psiFile = mock(PsiFile.class);
        final PsiElement firstFileChild = mock(PsiElement.class);
        final JsonStringLiteral jsonStringLiteral = mock(JsonStringLiteral.class);
        final JsonTraversal jsonTraversal = mock(JsonTraversal.class);
        final JsonProperty parameterProperty = mock(JsonProperty.class);
        List<JsonProperty> allParameters = Lists.newArrayList(parameterProperty);

        final PsiElement[] fileChildren = new PsiElement[1];
        fileChildren[0] = firstFileChild;

        when(jsonStringLiteral.getContainingFile()).thenReturn(psiFile);
        when(psiFile.getChildren()).thenReturn(fileChildren);
        when(parameterProperty.getName()).thenReturn("parameterName");
        when(jsonTraversal.getChildPropertiesByName(firstFileChild, "parameters")).thenReturn(allParameters);

        final JsonParameterReference jsonParameterReference =
                new JsonParameterReference(jsonStringLiteral, "parameterName", jsonTraversal);

        assertEquals(parameterProperty, jsonParameterReference.resolve());
    }

    @Test
    public void thatUnknownParameterElementIsResolvedToNull() throws Exception {
        final PsiFile psiFile = mock(PsiFile.class);
        final PsiElement firstFileChild = mock(PsiElement.class);
        final JsonStringLiteral jsonStringLiteral = mock(JsonStringLiteral.class);
        final JsonTraversal jsonTraversal = mock(JsonTraversal.class);

        final PsiElement[] fileChildren = new PsiElement[1];
        fileChildren[0] = firstFileChild;

        when(jsonStringLiteral.getContainingFile()).thenReturn(psiFile);
        when(psiFile.getChildren()).thenReturn(fileChildren);
        when(jsonTraversal.getChildPropertiesByName(firstFileChild, "parameters")).thenReturn(Lists.newArrayList());

        final JsonParameterReference jsonParameterReference =
                new JsonParameterReference(jsonStringLiteral, "parameterName", jsonTraversal);

        assertNull(jsonParameterReference.resolve());
    }
}
