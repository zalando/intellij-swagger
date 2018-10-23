package org.zalando.intellij.swagger.examples.extensions.zalando.validator.zally.model;

import java.util.List;

public class LintingResponse {

  private final List<Violation> violations;

  public LintingResponse(List<Violation> violations) {
    this.violations = violations;
  }

  public List<Violation> getViolations() {
    return violations;
  }
}
