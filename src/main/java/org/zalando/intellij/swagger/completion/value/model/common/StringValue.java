package org.zalando.intellij.swagger.completion.value.model.common;

public class StringValue extends Value {

  public StringValue(final String value) {
    super(value);
  }

  public String getValue() {
    return value;
  }

  @Override
  public boolean isQuotable() {
    return true;
  }
}
