package org.zalando.intellij.swagger.examples.extensions.zalando.validator.zally;

import com.google.common.collect.ImmutableList;
import org.zalando.intellij.swagger.examples.extensions.zalando.validator.zally.model.LintingResponse;
import org.zalando.intellij.swagger.examples.extensions.zalando.validator.zally.model.Violation;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MockZallyService implements ZallyService {

    public LintingResponse lint(final String spec) {
        final List<String> paths = ImmutableList.of("a/path");

        final List<Violation> violations = ImmutableList.of(
                new Violation("Violation 1", "MUST", "Violation link", paths)
        );

        return new LintingResponse(violations);
    }

}
