package org.zalando.intellij.swagger.completion.level;

import com.intellij.codeInsight.completion.CompletionResultSet;
import com.intellij.codeInsight.completion.InsertHandler;
import com.intellij.codeInsight.lookup.LookupElement;
import org.jetbrains.annotations.NotNull;
import org.zalando.intellij.swagger.completion.traversal.PositionResolver;

import static org.zalando.intellij.swagger.completion.level.LookupElementBuilderFactory.*;
import static org.zalando.intellij.swagger.completion.style.CompletionStyleFactory.optional;
import static org.zalando.intellij.swagger.completion.style.CompletionStyleFactory.required;

class InfoLevelCompletion extends LevelCompletion {

    InfoLevelCompletion(final PositionResolver positionResolver) {
        super(positionResolver);
    }

    public void fill(@NotNull final CompletionResultSet result,
                     @NotNull final InsertHandler<LookupElement> insertHandler) {
        result.addElement(create("title", required(positionResolver), insertHandler));
        result.addElement(create("description", optional(positionResolver), insertHandler));
        result.addElement(create("termsOfService", optional(positionResolver), insertHandler));
        result.addElement(create("contact", optional(positionResolver), insertHandler));
        result.addElement(create("license", optional(positionResolver), insertHandler));
        result.addElement(create("version", required(positionResolver), insertHandler));
    }

}
