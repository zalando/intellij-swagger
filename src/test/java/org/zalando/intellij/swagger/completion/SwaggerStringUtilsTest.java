package org.zalando.intellij.swagger.completion;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class SwaggerStringUtilsTest {

    @Test
    public void thatReturnsTrueWhenNextCharIsColon() throws Exception {
        assertTrue(SwaggerStringUtils.nextCharAfterSpacesAndQuotesIsColon(":"));
    }

    @Test
    public void thatReturnsTrueWhenQuoteIsFollowedByColon() throws Exception {
        assertTrue(SwaggerStringUtils.nextCharAfterSpacesAndQuotesIsColon("\":"));
    }

    @Test
    public void thatReturnsTrueWhenSpaceIsFollowedByColon() throws Exception {
        assertTrue(SwaggerStringUtils.nextCharAfterSpacesAndQuotesIsColon(" :"));
    }

    @Test
    public void thatReturnsTrueWhenSpaceAndQuoteIsFollowedByColon() throws Exception {
        assertTrue(SwaggerStringUtils.nextCharAfterSpacesAndQuotesIsColon(" \":"));
    }

    @Test
    public void thatReturnsFalseWhenQuotesNorSpacesBeforeColon() throws Exception {
        assertFalse(SwaggerStringUtils.nextCharAfterSpacesAndQuotesIsColon("a:"));
    }

    @Test
    public void thatReturnsFalseWhenOnlySpace() throws Exception {
        assertFalse(SwaggerStringUtils.nextCharAfterSpacesAndQuotesIsColon(" "));
    }

    @Test
    public void thatStringHasTwoSpacesInRow() throws Exception {
        assertEquals(2, SwaggerStringUtils.getNumberOfSpacesInRowStartingFromEnd("text  "));
    }

    @Test
    public void thatStringHasNoSpacesInRow() throws Exception {
        assertEquals(0, SwaggerStringUtils.getNumberOfSpacesInRowStartingFromEnd("text"));
    }

    @Test
    public void thatEmptyStringHasNoSpacesInRow() throws Exception {
        assertEquals(0, SwaggerStringUtils.getNumberOfSpacesInRowStartingFromEnd(""));
    }

    @Test
    public void thatStringHasSingleQuoteIsBeforeColon() throws Exception {
        assertTrue(SwaggerStringUtils.hasSingleQuoteBeforeColonStartingFromEnd("$ref: 'somevalue"));
    }

    @Test
    public void thatStringDoesNotHaveSingleQuoteIsBeforeColon() throws Exception {
        assertFalse(SwaggerStringUtils.hasSingleQuoteBeforeColonStartingFromEnd("$ref: somevalue"));
    }

}
