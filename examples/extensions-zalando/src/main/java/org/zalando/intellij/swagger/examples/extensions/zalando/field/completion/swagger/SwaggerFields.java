package org.zalando.intellij.swagger.examples.extensions.zalando.field.completion.swagger;

import com.google.common.collect.ImmutableList;
import java.util.List;
import org.zalando.intellij.swagger.completion.field.model.common.Field;
import org.zalando.intellij.swagger.completion.field.model.common.StringField;

class SwaggerFields {

  static List<Field> info() {
    return ImmutableList.of(new StringField("x-api-id", true), new StringField("x-audience", true));
  }
}
