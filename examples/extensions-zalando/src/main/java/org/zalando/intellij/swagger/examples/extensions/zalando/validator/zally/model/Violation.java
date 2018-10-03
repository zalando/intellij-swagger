package org.zalando.intellij.swagger.examples.extensions.zalando.validator.zally.model;

import java.util.List;

public class Violation {

    final private String title;
    final private String violationType;
    final private String ruleLink;
    final private List<String> paths;

    public Violation(String title, String violationType, String ruleLink, List<String> paths) {
        this.title = title;
        this.violationType = violationType;
        this.ruleLink = ruleLink;
        this.paths = paths;
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

    public List<String> getPaths() {
        return paths;
    }
}
