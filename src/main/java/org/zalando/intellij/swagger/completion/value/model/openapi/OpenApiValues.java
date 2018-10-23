package org.zalando.intellij.swagger.completion.value.model.openapi;

import com.google.common.collect.ImmutableList;
import java.util.List;
import org.zalando.intellij.swagger.completion.value.model.common.StringValue;
import org.zalando.intellij.swagger.completion.value.model.common.Value;

public class OpenApiValues {

  public static List<Value> in() {
    return ImmutableList.of(
        new StringValue("query"),
        new StringValue("header"),
        new StringValue("path"),
        new StringValue("cookie"));
  }

  public static List<Value> styles() {
    return ImmutableList.of(
        new StringValue("matrix"),
        new StringValue("label"),
        new StringValue("form"),
        new StringValue("simple"),
        new StringValue("spaceDelimited"),
        new StringValue("pipeDelimited"),
        new StringValue("deepObject"));
  }
}
