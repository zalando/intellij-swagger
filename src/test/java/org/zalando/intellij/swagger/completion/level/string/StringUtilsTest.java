package org.zalando.intellij.swagger.completion.level.string;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class StringUtilsTest {

    @Test
    public void thatReturnsTrueWhenNextCharIsColon() throws Exception {
        assertTrue(StringUtils.nextCharAfterSpacesIsColonOrQuote(":"));
    }

    @Test
    public void thatReturnsTrueWhenNextCharIsQuote() throws Exception {
        assertTrue(StringUtils.nextCharAfterSpacesIsColonOrQuote("\""));
    }

    @Test
    public void thatReturnsTrueWhenNextCharIsColonPrefixedBySpace() throws Exception {
        assertTrue(StringUtils.nextCharAfterSpacesIsColonOrQuote(" :"));
    }

    @Test
    public void thatReturnsTrueWhenNextCharIsQuotePrefixedBySpace() throws Exception {
        assertTrue(StringUtils.nextCharAfterSpacesIsColonOrQuote(" \""));
    }

    @Test
    public void thatReturnsFalseWhenNextCharIsNotColon() throws Exception {
        assertFalse(StringUtils.nextCharAfterSpacesIsColonOrQuote("a:"));
    }

    @Test
    public void thatReturnsFalseWhenOnlySpace() throws Exception {
        assertFalse(StringUtils.nextCharAfterSpacesIsColonOrQuote(" "));
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

}
