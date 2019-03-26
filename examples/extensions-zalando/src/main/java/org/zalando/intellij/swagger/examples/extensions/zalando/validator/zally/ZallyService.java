package org.zalando.intellij.swagger.examples.extensions.zalando.validator.zally;

import org.zalando.intellij.swagger.examples.extensions.zalando.validator.zally.model.LintingResponse;

public interface ZallyService {
  LintingResponse lint(final String spec) throws Exception;
}
