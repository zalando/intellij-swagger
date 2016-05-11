package org.zalando.intellij.swagger.completion;

import com.intellij.psi.PsiElement;
import org.junit.Before;
import org.junit.Test;
import org.zalando.intellij.swagger.completion.traversal.JsonTraversal;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PositionResolverTest {

    private PsiElement psiElement;
    private JsonTraversal jsonTraversal;
    private PositionResolver positionResolver;

    @Before
    public void setUp() throws Exception {
        psiElement = mock(PsiElement.class);
        jsonTraversal = mock(JsonTraversal.class);
        positionResolver = new PositionResolver(psiElement, jsonTraversal);
    }

    @Test
    public void thatCompletionShouldBeQuoted() throws Exception {
        when(psiElement.toString()).thenReturn("PsiElement(DOUBLE_QUOTED_STRING)");

        assertFalse(positionResolver.shouldQuote());
    }

    @Test
    public void thatCompletesRootKey() {
        when(jsonTraversal.isRoot(psiElement)).thenReturn(true);

        assertTrue(positionResolver.completeRootKey());
    }

    @Test
    public void thatDoesNotCompleteRootKey() {
        when(jsonTraversal.isKey(psiElement)).thenReturn(false);
        when(jsonTraversal.isRoot(psiElement)).thenReturn(false);

        assertFalse(positionResolver.completeRootKey());
    }

    @Test
    public void thatDoesNotCompleteRootKeyWhenKeyIsNotRoot() {
        when(jsonTraversal.isRoot(psiElement)).thenReturn(false);

        assertFalse(positionResolver.completeRootKey());
    }

    @Test
    public void thatCompletesInfoKey() {
        when(jsonTraversal.isInfo(psiElement)).thenReturn(true);

        assertTrue(positionResolver.completeInfoKey());
    }

    @Test
    public void thatCompletesContactKey() {
        when(jsonTraversal.isContact(psiElement)).thenReturn(true);

        assertTrue(positionResolver.completeContactKey());
    }

    @Test
    public void thatCompletesLicenseKey() {
        when(jsonTraversal.isLicense(psiElement)).thenReturn(true);

        assertTrue(positionResolver.completeLicenseKey());
    }

    @Test
    public void thatCompletesPathKey() {
        when(jsonTraversal.isPath(psiElement)).thenReturn(true);

        assertTrue(positionResolver.completePathKey());
    }

    @Test
    public void thatCompletesOperationKey() {
        when(jsonTraversal.isOperation(psiElement)).thenReturn(true);

        assertTrue(positionResolver.completeOperationKey());
    }

    @Test
    public void thatCompletesExternalDocsKey() {
        when(jsonTraversal.isExternalDocs(psiElement)).thenReturn(true);

        assertTrue(positionResolver.completeExternalDocsKey());
    }

    @Test
    public void thatCompletesParametersKey() {
        when(jsonTraversal.isParameters(psiElement)).thenReturn(true);

        assertTrue(positionResolver.completeParametersKey());
    }

    @Test
    public void thatCompletesItemsKey() {
        when(jsonTraversal.isItems(psiElement)).thenReturn(true);

        assertTrue(positionResolver.completeItemsKey());
    }

    @Test
    public void thatCompletesResponsesKey() {
        when(jsonTraversal.isResponses(psiElement)).thenReturn(true);

        assertTrue(positionResolver.completeResponsesKey());
    }

    @Test
    public void thatCompletesResponseKey() {
        when(jsonTraversal.isResponse(psiElement)).thenReturn(true);

        assertTrue(positionResolver.completeResponseKey());
    }

    @Test
    public void thatCompletesHeaderKey() {
        when(jsonTraversal.isHeader(psiElement)).thenReturn(true);

        assertTrue(positionResolver.completeHeaderKey());
    }

    @Test
    public void thatCompletesTagKey() {
        when(jsonTraversal.isTag(psiElement)).thenReturn(true);

        assertTrue(positionResolver.completeTagKey());
    }

    @Test
    public void thatCompletesSecurityDefinitionsKey() {
        when(jsonTraversal.isSecurityDefinition(psiElement)).thenReturn(true);

        assertTrue(positionResolver.completeSecurityDefinitionKey());
    }

    @Test
    public void thatCompletesSchemaKey() {
        when(jsonTraversal.isSchema(psiElement)).thenReturn(true);

        assertTrue(positionResolver.completeSchemaKey());
    }

    @Test
    public void thatCompletesXmlKey() {
        when(jsonTraversal.isXml(psiElement)).thenReturn(true);

        assertTrue(positionResolver.completeXmlKey());
    }

    @Test
    public void thatCompletesDefinitionsKey() {
        when(jsonTraversal.isDefinitions(psiElement)).thenReturn(true);

        assertTrue(positionResolver.completeDefinitionsKey());
    }
}
