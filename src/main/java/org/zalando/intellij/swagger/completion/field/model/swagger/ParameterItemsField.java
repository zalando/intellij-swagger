package org.zalando.intellij.swagger.completion.field.model.swagger;

import com.google.common.collect.ImmutableList;
import java.util.List;
import org.zalando.intellij.swagger.completion.field.model.common.Field;
import org.zalando.intellij.swagger.completion.field.model.common.ObjectField;
import org.zalando.intellij.swagger.completion.field.model.common.StringField;

class ParameterItemsField extends ObjectField {

  private static final List<Field> REQUIRED_FIELDS = ImmutableList.of(new StringField("type"));

  public ParameterItemsField() {
    super("items");
  }

  @Override
  public List<Field> getChildren() {
    return REQUIRED_FIELDS;
  }
}
