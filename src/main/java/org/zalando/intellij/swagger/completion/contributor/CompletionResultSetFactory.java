package org.zalando.intellij.swagger.completion.contributor;

import com.intellij.codeInsight.completion.CompletionParameters;
import com.intellij.codeInsight.completion.CompletionResultSet;
import com.intellij.codeInsight.completion.impl.CamelHumpMatcher;
import java.util.Optional;
import org.jetbrains.annotations.NotNull;

public class CompletionResultSetFactory {

  public static CompletionResultSet forValue(
      final @NotNull CompletionParameters parameters, final @NotNull CompletionResultSet result) {
    final boolean caseSensitive = false;

    return getPrefix(parameters.getOffset() - 1, parameters.getOriginalFile().getText())
        .map(prefix -> result.withPrefixMatcher(new CamelHumpMatcher(prefix, caseSensitive)))
        .orElse(result);
  }

  private static Optional<String> getPrefix(int offset, final String text) {
    final StringBuilder prefixBuilder = new StringBuilder();

    while (offset > 0
        && (Character.isLetterOrDigit(text.charAt(offset))
            || text.charAt(offset) == '#'
            || text.charAt(offset) == '/')) {
      prefixBuilder.insert(0, text.charAt(offset));
      offset--;
    }

    return prefixBuilder.length() > 0 ? Optional.of(prefixBuilder.toString()) : Optional.empty();
  }
}
