package org.zalando.intellij.swagger.examples.extensions.zalando.validator.zally;

import org.zalando.intellij.swagger.examples.extensions.zalando.validator.ZallySettings;

public class MockZallySettings implements ZallySettings {

  @Override
  public String getZallyUrl() {
    return "http://localhost";
  }

  @Override
  public void setZallyUrl(String zallyUrl) {
    // Not needed for a mock, no test cases for it.
  }
}
