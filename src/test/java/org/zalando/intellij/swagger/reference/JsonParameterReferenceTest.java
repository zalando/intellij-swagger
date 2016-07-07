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

public class JsonParameterReferenceTest {

    @Test
    public void thatParameterElementIsResolved() {
        final PsiFile psiFile = mock(PsiFile.class);
        final JsonStringLiteral jsonStringLiteral = mock(JsonStringLiteral.class);
        final JsonTraversal jsonTraversal = mock(JsonTraversal.class);
        final JsonProperty parameterProperty = mock(JsonProperty.class);
        List<PsiElement> allParameters = Lists.newArrayList(parameterProperty);

        when(jsonStringLiteral.getContainingFile()).thenReturn(psiFile);
        when(parameterProperty.getName()).thenReturn("parameterName");
        when(jsonTraversal.getChildrenOfDefinition("parameters", psiFile)).thenReturn(allParameters);

        final JsonParameterReference jsonParameterReference =
                new JsonParameterReference(jsonStringLiteral, "parameterName", jsonTraversal);

        assertEquals(parameterProperty, jsonParameterReference.resolve());
    }

    @Test
    public void thatUnknownParameterElementIsResolvedToNull() {
        final PsiFile psiFile = mock(PsiFile.class);
        final JsonStringLiteral jsonStringLiteral = mock(JsonStringLiteral.class);
        final JsonTraversal jsonTraversal = mock(JsonTraversal.class);

        when(jsonStringLiteral.getContainingFile()).thenReturn(psiFile);
        when(jsonTraversal.getChildrenOfDefinition("parameters", psiFile)).thenReturn(Lists.newArrayList());

        final JsonParameterReference jsonParameterReference =
                new JsonParameterReference(jsonStringLiteral, "parameterName", jsonTraversal);

        assertNull(jsonParameterReference.resolve());
    }
}
