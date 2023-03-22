package org.zalando.intellij.swagger.traversal.path;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Test;

public class PathExpressionTest {

  @Test
  public void thatCurrentPathIsReturned() {
    final PathExpression pathExpression = new PathExpression("$.path");

    final String currentPath = pathExpression.getCurrentPath();

    assertThat(currentPath, equalTo("$"));
  }

  @Test
  public void thatCurrentPathIsReturnedWithWithSingleValue() {
    final PathExpression pathExpression = new PathExpression("path");

    final String currentPath = pathExpression.getCurrentPath();

    assertThat(currentPath, equalTo("path"));
  }

  @Test
  public void thatCurrentPathIsReturnedWithDot() {
    final PathExpression pathExpression = new PathExpression("\\.test");

    final String currentPath = pathExpression.getCurrentPath();

    assertThat(currentPath, equalTo("\\.test"));
  }

  @Test
  public void thatPathAfterFirstPartIsReturned() {
    final PathExpression pathExpression = new PathExpression("test.path");

    final PathExpression afterFirst = pathExpression.afterFirst();

    assertThat(afterFirst.getPath(), equalTo("path"));
  }

  @Test
  public void thatPathAfterFirstPartIsReturnedWithSingleValue() {
    final PathExpression pathExpression = new PathExpression("path");

    final PathExpression afterFirst = pathExpression.afterFirst();

    assertThat(afterFirst.getPath(), equalTo(""));
  }

  @Test
  public void thatPathAfterFirstPartIsReturnedWithDot() {
    final PathExpression pathExpression = new PathExpression("test\\..path");

    final PathExpression afterFirst = pathExpression.afterFirst();

    assertThat(afterFirst.getPath(), equalTo("path"));
  }

  @Test
  public void thatPathBeforeLastPartIsReturned() {
    final PathExpression pathExpression = new PathExpression("test.path");

    final PathExpression beforeLast = pathExpression.beforeLast();

    assertThat(beforeLast.getPath(), equalTo("test"));
  }

  @Test
  public void thatPathBeforeLastPartIsReturnedWithSingleValue() {
    final PathExpression pathExpression = new PathExpression("path");

    final PathExpression beforeLast = pathExpression.beforeLast();

    assertThat(beforeLast.getPath(), equalTo("path"));
  }

  @Test
  public void thatPathBeforeLastPartIsReturnedWithDot() {
    final PathExpression pathExpression = new PathExpression("test.\\.path");

    final PathExpression beforeLast = pathExpression.beforeLast();

    assertThat(beforeLast.getPath(), equalTo("test"));
  }
}
