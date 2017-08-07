package org.zalando.intellij.swagger.completion;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import org.zalando.intellij.swagger.StringUtils;

public class StringUtilsTest {

    @Test
    public void thatReturnsTrueWhenNextCharIsColon() throws Exception {
        assertTrue(StringUtils.nextCharAfterSpacesAndQuotesIsColon(":"));
    }

    @Test
    public void thatReturnsTrueWhenQuoteIsFollowedByColon() throws Exception {
        assertTrue(StringUtils.nextCharAfterSpacesAndQuotesIsColon("\":"));
    }

    @Test
    public void thatReturnsTrueWhenSpaceIsFollowedByColon() throws Exception {
        assertTrue(StringUtils.nextCharAfterSpacesAndQuotesIsColon(" :"));
    }

    @Test
    public void thatReturnsTrueWhenSpaceAndQuoteIsFollowedByColon() throws Exception {
        assertTrue(StringUtils.nextCharAfterSpacesAndQuotesIsColon(" \":"));
    }

    @Test
    public void thatReturnsFalseWhenQuotesNorSpacesBeforeColon() throws Exception {
        assertFalse(StringUtils.nextCharAfterSpacesAndQuotesIsColon("a:"));
    }

    @Test
    public void thatReturnsFalseWhenOnlySpace() throws Exception {
        assertFalse(StringUtils.nextCharAfterSpacesAndQuotesIsColon(" "));
    }

    @Test
    public void thatStringHasTwoSpacesInRow() throws Exception {
        assertEquals(2, StringUtils.getNumberOfSpacesInRowStartingFromEnd("text  "));
    }

    @Test
    public void thatStringHasNoSpacesInRow() throws Exception {
        assertEquals(0, StringUtils.getNumberOfSpacesInRowStartingFromEnd("text"));
    }

    @Test
    public void thatEmptyStringHasNoSpacesInRow() throws Exception {
        assertEquals(0, StringUtils.getNumberOfSpacesInRowStartingFromEnd(""));
    }

    @Test
    public void thatStringHasSingleQuoteIsBeforeColon() throws Exception {
        assertTrue(StringUtils.hasSingleQuoteBeforeColonStartingFromEnd("$ref: 'somevalue"));
    }

    @Test
    public void thatStringDoesNotHaveSingleQuoteIsBeforeColon() throws Exception {
        assertFalse(StringUtils.hasSingleQuoteBeforeColonStartingFromEnd("$ref: somevalue"));
    }

}
