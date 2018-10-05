package org.zalando.intellij.swagger.examples.extensions.zalando.validator.zally.model;

public class Violation {

    final private String title;
    final private String description;
    final private String violationType;
    final private String ruleLink;
    final private String pointer;

    public Violation(String title, String description, String violationType, String ruleLink, String pointer) {
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
