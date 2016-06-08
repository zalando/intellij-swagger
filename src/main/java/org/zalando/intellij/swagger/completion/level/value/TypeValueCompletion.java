package org.zalando.intellij.swagger.completion.level.value;

import static org.zalando.intellij.swagger.completion.level.LookupElementBuilderFactory.create;
import static org.zalando.intellij.swagger.completion.style.CompletionStyleFactory.optional;
import com.intellij.codeInsight.completion.CompletionResultSet;
import com.intellij.codeInsight.completion.InsertHandler;
import com.intellij.codeInsight.lookup.LookupElement;
import org.jetbrains.annotations.NotNull;
import org.zalando.intellij.swagger.completion.traversal.PositionResolver;

class TypeValueCompletion extends ValueCompletion {

    TypeValueCompletion(final PositionResolver positionResolver) {
        super(positionResolver);
    }

    @Override
    public void fill(@NotNull final CompletionResultSet result, @NotNull final InsertHandler<LookupElement> insertHandler) {
        result.addElement(create("string", optional(positionResolver)));
        result.addElement(create("number", optional(positionResolver)));
        result.addElement(create("integer", optional(positionResolver)));
        result.addElement(create("boolean", optional(positionResolver)));
        result.addElement(create("array", optional(positionResolver)));
    }
}
