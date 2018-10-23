package org.zalando.intellij.swagger.examples.extensions.zalando.validator.zally.model;

public class Violation {

  private final String title;
  private final String description;
  private final String violationType;
  private final String ruleLink;
  private final String pointer;

  public Violation(
      String title, String description, String violationType, String ruleLink, String pointer) {
    this.title = title;
    this.description = description;
    this.violationType = violationType;
    this.ruleLink = ruleLink;
    this.pointer = pointer;
  }

  public String getTitle() {
    return title;
  }

  public String getViolationType() {
    return violationType;
  }

  public String getRuleLink() {
    return ruleLink;
  }

  public String getPointer() {
    return pointer;
  }

  public String getDescription() {
    return description;
  }
}
