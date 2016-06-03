package org.zalando.intellij.swagger.completion.level.value;

import com.intellij.codeInsight.completion.CompletionResultSet;
import com.intellij.codeInsight.completion.InsertHandler;
import com.intellij.codeInsight.lookup.LookupElement;
import org.jetbrains.annotations.NotNull;
import org.zalando.intellij.swagger.completion.traversal.PositionResolver;

import static org.zalando.intellij.swagger.completion.level.LookupElementBuilderFactory.create;
import static org.zalando.intellij.swagger.completion.style.CompletionStyleFactory.optional;

class BooleanValueCompletion extends ValueCompletion {

    BooleanValueCompletion(final PositionResolver positionResolver) {
        super(positionResolver);
    }

    @Override
    public void fill(@NotNull final CompletionResultSet result, @NotNull final InsertHandler<LookupElement> insertHandler) {
        result.addElement(create("true", optional(positionResolver)));
        result.addElement(create("false", optional(positionResolver)));
    }
}
