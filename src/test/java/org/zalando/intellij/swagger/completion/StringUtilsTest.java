package org.zalando.intellij.swagger.completion;

import static org.junit.Assert.*;

import org.junit.Test;
import org.zalando.intellij.swagger.StringUtils;

public class StringUtilsTest {

  @Test
  public void thatReturnsTrueWhenNextCharIsColon() {
    assertTrue(StringUtils.nextCharAfterSpacesAndQuotesIsColon(":"));
  }

  @Test
  public void thatReturnsTrueWhenQuoteIsFollowedByColon() {
    assertTrue(StringUtils.nextCharAfterSpacesAndQuotesIsColon("\":"));
  }

  @Test
  public void thatReturnsTrueWhenSpaceIsFollowedByColon() {
    assertTrue(StringUtils.nextCharAfterSpacesAndQuotesIsColon(" :"));
  }

  @Test
  public void thatReturnsTrueWhenSpaceAndQuoteIsFollowedByColon() {
    assertTrue(StringUtils.nextCharAfterSpacesAndQuotesIsColon(" \":"));
  }

  @Test
  public void thatReturnsFalseWhenQuotesNorSpacesBeforeColon() {
    assertFalse(StringUtils.nextCharAfterSpacesAndQuotesIsColon("a:"));
  }

  @Test
  public void thatReturnsFalseWhenOnlySpace() {
    assertFalse(StringUtils.nextCharAfterSpacesAndQuotesIsColon(" "));
  }

  @Test
  public void thatStringHasTwoSpacesInRow() {
    assertEquals(2, StringUtils.getNumberOfSpacesInRowStartingFromEnd("text  "));
  }

  @Test
  public void thatStringHasNoSpacesInRow() {
    assertEquals(0, StringUtils.getNumberOfSpacesInRowStartingFromEnd("text"));
  }

  @Test
  public void thatEmptyStringHasNoSpacesInRow() {
    assertEquals(0, StringUtils.getNumberOfSpacesInRowStartingFromEnd(""));
  }

  @Test
  public void thatStringHasSingleQuoteIsBeforeColon() {
    assertTrue(StringUtils.hasSingleQuoteBeforeColonStartingFromEnd("$ref: 'somevalue"));
  }

  @Test
  public void thatStringDoesNotHaveSingleQuoteIsBeforeColon() {
    assertFalse(StringUtils.hasSingleQuoteBeforeColonStartingFromEnd("$ref: somevalue"));
  }
}
