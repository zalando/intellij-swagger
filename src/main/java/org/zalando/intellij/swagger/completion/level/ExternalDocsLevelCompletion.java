package org.zalando.intellij.swagger.completion.level;

import com.intellij.codeInsight.completion.CompletionResultSet;
import org.jetbrains.annotations.NotNull;
import org.zalando.intellij.swagger.completion.traversal.PositionResolver;

import static org.zalando.intellij.swagger.completion.style.CompletionStyleFactory.optional;

class ExternalDocsLevelCompletion extends LevelCompletion {

    ExternalDocsLevelCompletion(final PositionResolver positionResolver) {
        super(positionResolver);
    }

    @Override
    public void fill(@NotNull final CompletionResultSet result) {
        result.addElement(LookupElementBuilderFactory.create("description", optional(positionResolver)));
        result.addElement(LookupElementBuilderFactory.create("url", optional(positionResolver)));
    }
}
