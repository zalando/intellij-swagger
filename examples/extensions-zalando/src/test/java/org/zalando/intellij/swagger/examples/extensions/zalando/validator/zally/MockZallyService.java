package org.zalando.intellij.swagger.examples.extensions.zalando.validator.zally;

import org.zalando.intellij.swagger.examples.extensions.zalando.validator.zally.model.LintingResponse;
import org.zalando.intellij.swagger.examples.extensions.zalando.validator.zally.model.Violation;

import java.util.HashSet;
import java.util.Set;

public class MockZallyService implements ZallyService {

    public LintingResponse validate(final String spec) {
        final Set<Violation> violations = new HashSet<>();
        violations.add(new Violation("Violation 1", "MUST", "Violation link"));

        return new LintingResponse(violations);
    }

}
