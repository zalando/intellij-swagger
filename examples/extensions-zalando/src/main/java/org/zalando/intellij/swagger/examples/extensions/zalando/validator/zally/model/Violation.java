package org.zalando.intellij.swagger.examples.extensions.zalando.validator.zally.model;

import com.google.common.base.Objects;

public class Violation {

    final private String title;
    final private String violationType;
    final private String ruleLink;

    public Violation(String title, String violationType, String ruleLink) {
        this.title = title;
        this.violationType = violationType;
        this.ruleLink = ruleLink;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Violation violation = (Violation) o;
        return Objects.equal(title, violation.title) &&
                Objects.equal(violationType, violation.violationType) &&
                Objects.equal(ruleLink, violation.ruleLink);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(title, violationType, ruleLink);
    }
}
