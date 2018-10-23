package org.zalando.intellij.swagger.examples.extensions.zalando.validator.zally.model;

import com.google.gson.annotations.SerializedName;

public class LintingRequest {

  @SerializedName("api_definition_string")
  public final String spec;

  public LintingRequest(String spec) {
    this.spec = spec;
  }
}
