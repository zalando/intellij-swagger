package org.zalando.intellij.swagger.completion;

import com.intellij.codeInsight.completion.CompletionResultSet;
import org.jetbrains.annotations.NotNull;

public interface LevelCompletion {

    void fill(@NotNull final CompletionResultSet result, final boolean shouldQuote);
}
