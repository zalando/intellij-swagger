package org.zalando.intellij.swagger.completion.traversal;

import com.intellij.psi.PsiElement;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PositionResolverTest {

    private PsiElement psiElement;
    private Traversal traversal;
    private PositionResolver positionResolver;

    @Before
    public void setUp() throws Exception {
        psiElement = mock(PsiElement.class);
        traversal = mock(Traversal.class);
        positionResolver = new PositionResolver(psiElement, traversal);
    }

    @Test
    public void thatCompletionShouldBeQuoted() throws Exception {
        when(psiElement.toString()).thenReturn("PsiElement(DOUBLE_QUOTED_STRING)");

        assertFalse(positionResolver.shouldQuote());
    }

    @Test
    public void thatCompletesRootKey() {
        when(traversal.isRoot(psiElement)).thenReturn(true);

        assertTrue(positionResolver.completeRootKey());
    }

    @Test
    public void thatDoesNotCompleteRootKey() {
        when(traversal.isKey(psiElement)).thenReturn(false);
        when(traversal.isRoot(psiElement)).thenReturn(false);

        assertFalse(positionResolver.completeRootKey());
    }

    @Test
    public void thatDoesNotCompleteRootKeyWhenKeyIsNotRoot() {
        when(traversal.isRoot(psiElement)).thenReturn(false);

        assertFalse(positionResolver.completeRootKey());
    }

    @Test
    public void thatCompletesInfoKey() {
        when(traversal.isInfo(psiElement)).thenReturn(true);

        assertTrue(positionResolver.completeInfoKey());
    }

    @Test
    public void thatCompletesContactKey() {
        when(traversal.isContact(psiElement)).thenReturn(true);

        assertTrue(positionResolver.completeContactKey());
    }

    @Test
    public void thatCompletesLicenseKey() {
        when(traversal.isLicense(psiElement)).thenReturn(true);

        assertTrue(positionResolver.completeLicenseKey());
    }

    @Test
    public void thatCompletesPathKey() {
        when(traversal.isPath(psiElement)).thenReturn(true);

        assertTrue(positionResolver.completePathKey());
    }

    @Test
    public void thatCompletesOperationKey() {
        when(traversal.isOperation(psiElement)).thenReturn(true);

        assertTrue(positionResolver.completeOperationKey());
    }

    @Test
    public void thatCompletesExternalDocsKey() {
        when(traversal.isExternalDocs(psiElement)).thenReturn(true);

        assertTrue(positionResolver.completeExternalDocsKey());
    }

    @Test
    public void thatCompletesParametersKey() {
        when(traversal.isParameters(psiElement)).thenReturn(true);

        assertTrue(positionResolver.completeParametersKey());
    }

    @Test
    public void thatCompletesItemsKey() {
        when(traversal.isItems(psiElement)).thenReturn(true);

        assertTrue(positionResolver.completeItemsKey());
    }

    @Test
    public void thatCompletesResponsesKey() {
        when(traversal.isResponses(psiElement)).thenReturn(true);

        assertTrue(positionResolver.completeResponsesKey());
    }

    @Test
    public void thatCompletesResponseKey() {
        when(traversal.isResponse(psiElement)).thenReturn(true);

        assertTrue(positionResolver.completeResponseKey());
    }

    @Test
    public void thatCompletesHeaderKey() {
        when(traversal.isHeader(psiElement)).thenReturn(true);

        assertTrue(positionResolver.completeHeaderKey());
    }

    @Test
    public void thatCompletesTagKey() {
        when(traversal.isTag(psiElement)).thenReturn(true);

        assertTrue(positionResolver.completeTagKey());
    }

    @Test
    public void thatCompletesSecurityDefinitionsKey() {
        when(traversal.isSecurityDefinition(psiElement)).thenReturn(true);

        assertTrue(positionResolver.completeSecurityDefinitionKey());
    }

    @Test
    public void thatCompletesSchemaKey() {
        when(traversal.isSchema(psiElement)).thenReturn(true);

        assertTrue(positionResolver.completeSchemaKey());
    }

    @Test
    public void thatCompletesXmlKey() {
        when(traversal.isXml(psiElement)).thenReturn(true);

        assertTrue(positionResolver.completeXmlKey());
    }

    @Test
    public void thatCompletesDefinitionsKey() {
        when(traversal.isDefinitions(psiElement)).thenReturn(true);

        assertTrue(positionResolver.completeDefinitionsKey());
    }

    @Test
    public void thatCompletesMimeValue() {
        when(traversal.isMimeValue(psiElement)).thenReturn(true);

        assertTrue(positionResolver.completeMimeValue());
    }

    @Test
    public void thatCompletesSchemesValue() {
        when(traversal.isSchemesValue(psiElement)).thenReturn(true);

        assertTrue(positionResolver.completeSchemesValue());
    }
}
