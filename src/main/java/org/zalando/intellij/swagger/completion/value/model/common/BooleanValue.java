package org.zalando.intellij.swagger.completion.value.model.common;

public class BooleanValue extends Value {

  public BooleanValue(final String value) {
    super(value);
  }

  public String getValue() {
    return value;
  }

  @Override
  public boolean isQuotable() {
    return false;
  }
}
