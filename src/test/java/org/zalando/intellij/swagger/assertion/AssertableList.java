package org.zalando.intellij.swagger.assertion;

import static org.hamcrest.MatcherAssert.assertThat;

import java.util.ArrayList;
import java.util.List;
import org.hamcrest.core.Is;
import org.hamcrest.core.IsCollectionContaining;
import org.hamcrest.core.IsNot;

public class AssertableList {
  private final List<String> myActualList;

  public AssertableList(List<String> list) {
    myActualList = new ArrayList<>(list);
  }

  public AssertableList assertContains(Iterable<String> elements) {
    for (String next : elements) {
      assertContainsOne(next);
    }
    return this;
  }

  public AssertableList assertContains(String... elements) {
    for (String next : elements) {
      assertContainsOne(next);
    }
    return this;
  }

  public AssertableList assertContainsOne(String element) {
    assertThat(myActualList, IsCollectionContaining.hasItem(element));
    return this;
  }

  public AssertableList assertNotContains(Iterable<String> badElements) {
    for (String nextBad : badElements) {
      assertThat(myActualList, IsNot.not(IsCollectionContaining.hasItem(nextBad)));
    }
    return this;
  }

  public AssertableList assertNotContains(String... badElements) {
    for (String nextBad : badElements) {
      assertThat(myActualList, IsNot.not(IsCollectionContaining.hasItem(nextBad)));
    }
    return this;
  }

  public AssertableList isOfSize(final int size) {
    assertThat(myActualList.size(), Is.is(size));
    return this;
  }
}
