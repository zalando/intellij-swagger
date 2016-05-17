package org.zalando.intellij.swagger.completion.traversal;

import com.google.common.collect.Lists;
import com.intellij.json.psi.JsonFile;
import com.intellij.json.psi.JsonProperty;
import com.intellij.json.psi.JsonValue;
import com.intellij.psi.PsiElement;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class JsonTraversalTest {

    private JsonTraversal jsonTraversal;

    @Before
    public void setUp() throws Exception {
        jsonTraversal = new JsonTraversal();
    }

    @Test
    public void thatRootIsResolved() {
        final PsiElement psiElement1 = mock(PsiElement.class);
        final PsiElement psiElement2 = mock(PsiElement.class);
        final PsiElement psiElement3 = mock(PsiElement.class);
        final PsiElement psiElement4 = mock(PsiElement.class);
        final JsonFile jsonFile = mock(JsonFile.class);

        when(psiElement1.getParent()).thenReturn(psiElement2);
        when(psiElement2.getParent()).thenReturn(psiElement3);
        when(psiElement3.getParent()).thenReturn(psiElement4);
        when(psiElement4.getParent()).thenReturn(jsonFile);

        assertTrue(jsonTraversal.isRoot(psiElement1));
    }

    @Test
    public void thatKeyIsResolved() {
        final PsiElement psiElement = mock(PsiElement.class);
        final JsonProperty jsonProperty = mock(JsonProperty.class);
        final JsonValue jsonValue = mock(JsonValue.class);

        when(psiElement.getParent()).thenReturn(jsonValue);
        when(jsonValue.getParent()).thenReturn(jsonProperty);
        when(jsonProperty.getNameElement()).thenReturn(jsonValue);

        assertTrue(jsonTraversal.isKey(psiElement));
    }

    @Test
    public void thatInfoIsResolved() {
        final PsiElement psiElement1 = mock(PsiElement.class);
        final PsiElement psiElement2 = mock(PsiElement.class);
        final PsiElement psiElement3 = mock(PsiElement.class);
        final PsiElement psiElement4 = mock(PsiElement.class);
        final JsonProperty jsonProperty = mock(JsonProperty.class);

        when(psiElement1.getParent()).thenReturn(psiElement2);
        when(psiElement2.getParent()).thenReturn(psiElement3);
        when(psiElement3.getParent()).thenReturn(psiElement4);
        when(psiElement4.getParent()).thenReturn(jsonProperty);
        when(jsonProperty.getName()).thenReturn("info");

        assertTrue(jsonTraversal.isInfo(psiElement1));
    }

    @Test
    public void thatLicenseIsResolved() {
        final PsiElement psiElement1 = mock(PsiElement.class);
        final PsiElement psiElement2 = mock(PsiElement.class);
        final PsiElement psiElement3 = mock(PsiElement.class);
        final PsiElement psiElement4 = mock(PsiElement.class);
        final JsonProperty jsonProperty = mock(JsonProperty.class);

        when(psiElement1.getParent()).thenReturn(psiElement2);
        when(psiElement2.getParent()).thenReturn(psiElement3);
        when(psiElement3.getParent()).thenReturn(psiElement4);
        when(psiElement4.getParent()).thenReturn(jsonProperty);
        when(jsonProperty.getName()).thenReturn("license");

        assertTrue(jsonTraversal.isLicense(psiElement1));
    }

    @Test
    public void thatPathIsResolved() {
        final PsiElement psiElement1 = mock(PsiElement.class);
        final PsiElement psiElement2 = mock(PsiElement.class);
        final PsiElement psiElement3 = mock(PsiElement.class);
        final PsiElement psiElement4 = mock(PsiElement.class);
        final PsiElement psiElement5 = mock(PsiElement.class);
        final PsiElement psiElement6 = mock(PsiElement.class);
        final JsonProperty jsonProperty = mock(JsonProperty.class);

        when(psiElement1.getParent()).thenReturn(psiElement2);
        when(psiElement2.getParent()).thenReturn(psiElement3);
        when(psiElement3.getParent()).thenReturn(psiElement4);
        when(psiElement4.getParent()).thenReturn(psiElement5);
        when(psiElement5.getParent()).thenReturn(psiElement6);
        when(psiElement6.getParent()).thenReturn(jsonProperty);
        when(jsonProperty.getName()).thenReturn("paths");

        assertTrue(jsonTraversal.isPath(psiElement1));
    }

    @Test
    public void thatExternalDocsIsResolved() {
        final PsiElement psiElement1 = mock(PsiElement.class);
        final PsiElement psiElement2 = mock(PsiElement.class);
        final PsiElement psiElement3 = mock(PsiElement.class);
        final PsiElement psiElement4 = mock(PsiElement.class);
        final JsonProperty jsonProperty = mock(JsonProperty.class);

        when(psiElement1.getParent()).thenReturn(psiElement2);
        when(psiElement2.getParent()).thenReturn(psiElement3);
        when(psiElement3.getParent()).thenReturn(psiElement4);
        when(psiElement4.getParent()).thenReturn(jsonProperty);
        when(jsonProperty.getName()).thenReturn("externalDocs");

        assertTrue(jsonTraversal.isExternalDocs(psiElement1));
    }

    @Test
    public void thatParametersIsResolved() {
        final PsiElement psiElement1 = mock(PsiElement.class);
        final PsiElement psiElement2 = mock(PsiElement.class);
        final PsiElement psiElement3 = mock(PsiElement.class);
        final PsiElement psiElement4 = mock(PsiElement.class);
        final PsiElement psiElement5 = mock(PsiElement.class);
        final JsonProperty jsonProperty = mock(JsonProperty.class);

        when(psiElement1.getParent()).thenReturn(psiElement2);
        when(psiElement2.getParent()).thenReturn(psiElement3);
        when(psiElement3.getParent()).thenReturn(psiElement4);
        when(psiElement4.getParent()).thenReturn(psiElement5);
        when(psiElement5.getParent()).thenReturn(jsonProperty);
        when(jsonProperty.getName()).thenReturn("parameters");

        assertTrue(jsonTraversal.isParameters(psiElement1));
    }

    @Test
    public void thatItemsIsResolved() {
        final PsiElement psiElement1 = mock(PsiElement.class);
        final PsiElement psiElement2 = mock(PsiElement.class);
        final PsiElement psiElement3 = mock(PsiElement.class);
        final PsiElement psiElement4 = mock(PsiElement.class);
        final JsonProperty jsonProperty = mock(JsonProperty.class);

        when(psiElement1.getParent()).thenReturn(psiElement2);
        when(psiElement2.getParent()).thenReturn(psiElement3);
        when(psiElement3.getParent()).thenReturn(psiElement4);
        when(psiElement4.getParent()).thenReturn(jsonProperty);
        when(jsonProperty.getName()).thenReturn("items");

        assertTrue(jsonTraversal.isItems(psiElement1));
    }

    @Test
    public void thatResponsesIsResolved() {
        final PsiElement psiElement1 = mock(PsiElement.class);
        final PsiElement psiElement2 = mock(PsiElement.class);
        final PsiElement psiElement3 = mock(PsiElement.class);
        final PsiElement psiElement4 = mock(PsiElement.class);
        final JsonProperty jsonProperty = mock(JsonProperty.class);

        when(psiElement1.getParent()).thenReturn(psiElement2);
        when(psiElement2.getParent()).thenReturn(psiElement3);
        when(psiElement3.getParent()).thenReturn(psiElement4);
        when(psiElement4.getParent()).thenReturn(jsonProperty);
        when(jsonProperty.getName()).thenReturn("responses");

        assertTrue(jsonTraversal.isResponses(psiElement1));
    }

    @Test
    public void thatResponseIsResolved() {
        final PsiElement psiElement1 = mock(PsiElement.class);
        final PsiElement psiElement2 = mock(PsiElement.class);
        final PsiElement psiElement3 = mock(PsiElement.class);
        final PsiElement psiElement4 = mock(PsiElement.class);
        final PsiElement psiElement5 = mock(PsiElement.class);
        final PsiElement psiElement6 = mock(PsiElement.class);
        final JsonProperty jsonProperty = mock(JsonProperty.class);

        when(psiElement1.getParent()).thenReturn(psiElement2);
        when(psiElement2.getParent()).thenReturn(psiElement3);
        when(psiElement3.getParent()).thenReturn(psiElement4);
        when(psiElement4.getParent()).thenReturn(psiElement5);
        when(psiElement5.getParent()).thenReturn(psiElement6);
        when(psiElement6.getParent()).thenReturn(jsonProperty);
        when(jsonProperty.getName()).thenReturn("responses");

        assertTrue(jsonTraversal.isResponse(psiElement1));
    }

    @Test
    public void thatHeaderIsResolved() {
        final PsiElement psiElement1 = mock(PsiElement.class);
        final PsiElement psiElement2 = mock(PsiElement.class);
        final PsiElement psiElement3 = mock(PsiElement.class);
        final PsiElement psiElement4 = mock(PsiElement.class);
        final PsiElement psiElement5 = mock(PsiElement.class);
        final PsiElement psiElement6 = mock(PsiElement.class);
        final JsonProperty jsonProperty = mock(JsonProperty.class);

        when(psiElement1.getParent()).thenReturn(psiElement2);
        when(psiElement2.getParent()).thenReturn(psiElement3);
        when(psiElement3.getParent()).thenReturn(psiElement4);
        when(psiElement4.getParent()).thenReturn(psiElement5);
        when(psiElement5.getParent()).thenReturn(psiElement6);
        when(psiElement6.getParent()).thenReturn(jsonProperty);
        when(jsonProperty.getName()).thenReturn("headers");

        assertTrue(jsonTraversal.isHeader(psiElement1));
    }

    @Test
    public void thatTagIsResolved() {
        final PsiElement psiElement1 = mock(PsiElement.class);
        final PsiElement psiElement2 = mock(PsiElement.class);
        final PsiElement psiElement3 = mock(PsiElement.class);
        final PsiElement psiElement4 = mock(PsiElement.class);
        final PsiElement psiElement5 = mock(PsiElement.class);
        final JsonProperty jsonProperty = mock(JsonProperty.class);

        when(psiElement1.getParent()).thenReturn(psiElement2);
        when(psiElement2.getParent()).thenReturn(psiElement3);
        when(psiElement3.getParent()).thenReturn(psiElement4);
        when(psiElement4.getParent()).thenReturn(psiElement5);
        when(psiElement5.getParent()).thenReturn(jsonProperty);
        when(jsonProperty.getName()).thenReturn("tags");

        assertTrue(jsonTraversal.isTag(psiElement1));
    }

    @Test
    public void thatSecurityDefinitionsIsResolved() {
        final PsiElement psiElement1 = mock(PsiElement.class);
        final PsiElement psiElement2 = mock(PsiElement.class);
        final PsiElement psiElement3 = mock(PsiElement.class);
        final PsiElement psiElement4 = mock(PsiElement.class);
        final PsiElement psiElement5 = mock(PsiElement.class);
        final PsiElement psiElement6 = mock(PsiElement.class);
        final JsonProperty jsonProperty = mock(JsonProperty.class);

        when(psiElement1.getParent()).thenReturn(psiElement2);
        when(psiElement2.getParent()).thenReturn(psiElement3);
        when(psiElement3.getParent()).thenReturn(psiElement4);
        when(psiElement4.getParent()).thenReturn(psiElement5);
        when(psiElement5.getParent()).thenReturn(psiElement6);
        when(psiElement6.getParent()).thenReturn(jsonProperty);
        when(jsonProperty.getName()).thenReturn("securityDefinitions");

        assertTrue(jsonTraversal.isSecurityDefinition(psiElement1));
    }

    @Test
    public void thatSchemaIsResolved() {
        final PsiElement psiElement1 = mock(PsiElement.class);
        final PsiElement psiElement2 = mock(PsiElement.class);
        final PsiElement psiElement3 = mock(PsiElement.class);
        final PsiElement psiElement4 = mock(PsiElement.class);
        final JsonProperty jsonProperty = mock(JsonProperty.class);

        when(psiElement1.getParent()).thenReturn(psiElement2);
        when(psiElement2.getParent()).thenReturn(psiElement3);
        when(psiElement3.getParent()).thenReturn(psiElement4);
        when(psiElement4.getParent()).thenReturn(jsonProperty);
        when(jsonProperty.getName()).thenReturn("schema");

        assertTrue(jsonTraversal.isSchema(psiElement1));
    }

    @Test
    public void thatDefinitionsIsResolved() {
        final PsiElement psiElement1 = mock(PsiElement.class);
        final PsiElement psiElement2 = mock(PsiElement.class);
        final PsiElement psiElement3 = mock(PsiElement.class);
        final PsiElement psiElement4 = mock(PsiElement.class);
        final PsiElement psiElement5 = mock(PsiElement.class);
        final PsiElement psiElement6 = mock(PsiElement.class);
        final JsonProperty jsonProperty = mock(JsonProperty.class);

        when(psiElement1.getParent()).thenReturn(psiElement2);
        when(psiElement2.getParent()).thenReturn(psiElement3);
        when(psiElement3.getParent()).thenReturn(psiElement4);
        when(psiElement4.getParent()).thenReturn(psiElement5);
        when(psiElement5.getParent()).thenReturn(psiElement6);
        when(psiElement6.getParent()).thenReturn(jsonProperty);
        when(jsonProperty.getName()).thenReturn("definitions");

        assertTrue(jsonTraversal.isDefinitions(psiElement1));
    }

    @Test
    public void thatMimeValueIsResolvedForConsumesObject() {
        final PsiElement psiElement1 = mock(PsiElement.class);
        final PsiElement psiElement2 = mock(PsiElement.class);
        final PsiElement psiElement3 = mock(PsiElement.class);
        final JsonProperty jsonProperty = mock(JsonProperty.class);

        when(psiElement1.getParent()).thenReturn(psiElement2);
        when(psiElement2.getParent()).thenReturn(psiElement3);
        when(psiElement3.getParent()).thenReturn(jsonProperty);
        when(jsonProperty.getName()).thenReturn("consumes");

        assertTrue(jsonTraversal.isMimeValue(psiElement1));
    }

    @Test
    public void thatMimeValueIsResolvedForProducesObject() {
        final PsiElement psiElement1 = mock(PsiElement.class);
        final PsiElement psiElement2 = mock(PsiElement.class);
        final PsiElement psiElement3 = mock(PsiElement.class);
        final JsonProperty jsonProperty = mock(JsonProperty.class);

        when(psiElement1.getParent()).thenReturn(psiElement2);
        when(psiElement2.getParent()).thenReturn(psiElement3);
        when(psiElement3.getParent()).thenReturn(jsonProperty);
        when(jsonProperty.getName()).thenReturn("produces");

        assertTrue(jsonTraversal.isMimeValue(psiElement1));
    }

    @Test
    public void thatShouldQuote() {
        final PsiElement psiElement = mock(PsiElement.class);
        when(psiElement.toString()).thenReturn("ELEMENT");

        assertTrue(jsonTraversal.shouldQuote(psiElement));
    }

    @Test
    public void thatShouldNotQuote() {
        final PsiElement psiElement = mock(PsiElement.class);
        when(psiElement.toString()).thenReturn("DOUBLE_QUOTED_STRING");

        assertFalse(jsonTraversal.shouldQuote(psiElement));
    }

    @Test
    public void thatSchemesValueIsResolved() {
        final PsiElement psiElement1 = mock(PsiElement.class);
        final PsiElement psiElement2 = mock(PsiElement.class);
        final PsiElement psiElement3 = mock(PsiElement.class);
        final JsonProperty jsonProperty = mock(JsonProperty.class);

        when(psiElement1.getParent()).thenReturn(psiElement2);
        when(psiElement2.getParent()).thenReturn(psiElement3);
        when(psiElement3.getParent()).thenReturn(jsonProperty);
        when(jsonProperty.getName()).thenReturn("schemes");

        assertTrue(jsonTraversal.isSchemesValue(psiElement1));
    }

    @Test
    public void thatChildPropertiesAreResolved() throws Exception {
        final PsiElement rootElement = mock(PsiElement.class);
        final JsonProperty rootProperty = mock(JsonProperty.class);
        final JsonValue rootPropertyValue = mock(JsonValue.class);
        final JsonProperty rootPropertyChild = mock(JsonProperty.class);
        final List<JsonProperty> rootPropertyChildren = Lists.newArrayList(rootPropertyChild);
        final List<PsiElement> rootProperties = Lists.newArrayList(rootProperty);

        when(rootPropertyValue.getChildren()).thenReturn(rootPropertyChildren.toArray(new JsonProperty[1]));
        when(rootProperty.getValue()).thenReturn(rootPropertyValue);
        when(rootProperty.getName()).thenReturn("definitions");
        when(rootElement.getChildren()).thenReturn(rootProperties.toArray(new PsiElement[1]));

        final List<JsonProperty> properties = jsonTraversal.getChildPropertiesByName(rootElement, "definitions");

        assertEquals(1, properties.size());
        assertEquals(rootPropertyChild, properties.get(0));
    }
}
