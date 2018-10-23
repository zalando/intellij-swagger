package org.zalando.intellij.swagger.completion.field.model.common;

import static org.zalando.intellij.swagger.file.FileConstants.CARET;

import org.apache.commons.lang.StringUtils;

public class StringField extends Field {

  public StringField(final String name, final boolean required) {
    super(name, required);
  }

  public StringField(final String name) {
    super(name, false);
  }

  @Override
  public String getJsonPlaceholderSuffix(final int indentation) {
    return ": \"" + CARET + "\"";
  }

  @Override
  public String getYamlPlaceholderSuffix(final int indentation) {
    return ": " + CARET;
  }

  @Override
  public String getCompleteJson(final int indentation) {
    final String leftPadding = StringUtils.repeat(" ", indentation);
    return leftPadding + "\"" + getName() + "\": \"" + CARET + "\"";
  }

  @Override
  public String getCompleteYaml(final int indentation) {
    final String leftPadding = StringUtils.repeat(" ", indentation);
    return leftPadding + getName() + ": " + CARET;
  }
}
