package org.zalando.intellij.swagger.examples.extensions.zalando.validator.zally.model;

import java.util.Set;

public class LintingResponse {

    private final Set<Violation> violations;

    public LintingResponse(Set<Violation> violations) {
        this.violations = violations;
    }

    public Set<Violation> getViolations() {
        return violations;
    }
}
