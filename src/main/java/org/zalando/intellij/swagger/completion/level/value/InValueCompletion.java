package org.zalando.intellij.swagger.completion.level.value;

import static org.zalando.intellij.swagger.completion.level.LookupElementBuilderFactory.create;
import static org.zalando.intellij.swagger.completion.style.CompletionStyleFactory.optional;
import com.intellij.codeInsight.completion.CompletionResultSet;
import com.intellij.codeInsight.completion.InsertHandler;
import com.intellij.codeInsight.lookup.LookupElement;
import org.jetbrains.annotations.NotNull;
import org.zalando.intellij.swagger.completion.traversal.PositionResolver;

class InValueCompletion extends ValueCompletion {

    InValueCompletion(final PositionResolver positionResolver) {
        super(positionResolver);
    }

    @Override
    public void fill(@NotNull final CompletionResultSet result, @NotNull final InsertHandler<LookupElement> insertHandler) {
        result.addElement(create("query", optional(positionResolver)));
        result.addElement(create("header", optional(positionResolver)));
        result.addElement(create("path", optional(positionResolver)));
        result.addElement(create("formData", optional(positionResolver)));
        result.addElement(create("body", optional(positionResolver)));
    }
}
