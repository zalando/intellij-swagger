package org.zalando.intellij.swagger.examples.extensions.zalando.validator.zally;

import com.google.common.collect.ImmutableList;
import org.zalando.intellij.swagger.examples.extensions.zalando.validator.zally.model.LintingResponse;
import org.zalando.intellij.swagger.examples.extensions.zalando.validator.zally.model.Violation;

import java.util.List;

public class MockZallyService implements ZallyService {

    public LintingResponse lint(final String spec) {
        final List<Violation> violations = ImmutableList.of(
                new Violation("Violation 1", "Description", "MUST", "Violation link", "a/path")
        );

        return new LintingResponse(violations);
    }

}
