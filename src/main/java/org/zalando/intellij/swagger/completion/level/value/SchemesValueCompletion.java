package org.zalando.intellij.swagger.completion.level.value;

import com.intellij.codeInsight.completion.CompletionResultSet;
import org.jetbrains.annotations.NotNull;
import org.zalando.intellij.swagger.completion.traversal.PositionResolver;

import static org.zalando.intellij.swagger.completion.level.LookupElementBuilderFactory.create;
import static org.zalando.intellij.swagger.completion.style.CompletionStyleFactory.optional;

class SchemesValueCompletion extends ValueCompletion {

    SchemesValueCompletion(final PositionResolver positionResolver) {
        super(positionResolver);
    }

    @Override
    public void fill(@NotNull final CompletionResultSet result) {
        result.addElement(create("http", optional(positionResolver)));
        result.addElement(create("https", optional(positionResolver)));
        result.addElement(create("ws", optional(positionResolver)));
        result.addElement(create("wss", optional(positionResolver)));
    }
}
