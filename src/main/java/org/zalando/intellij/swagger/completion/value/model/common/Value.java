package org.zalando.intellij.swagger.completion.value.model.common;

public abstract class Value {

  final String value;

  Value(final String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }

  public abstract boolean isQuotable();
}
