package org.zalando.intellij.swagger.completion.level;

import com.intellij.codeInsight.completion.CompletionResultSet;
import com.intellij.codeInsight.completion.InsertHandler;
import com.intellij.codeInsight.lookup.LookupElement;
import org.jetbrains.annotations.NotNull;
import org.zalando.intellij.swagger.completion.traversal.PositionResolver;

import static org.zalando.intellij.swagger.completion.style.CompletionStyleFactory.optional;
import static org.zalando.intellij.swagger.completion.style.CompletionStyleFactory.required;

class InfoLevelCompletion extends LevelCompletion {

    InfoLevelCompletion(final PositionResolver positionResolver, final CompletionResultSet completionResultSet) {
        super(positionResolver, completionResultSet);
    }

    public void fill(@NotNull final InsertHandler<LookupElement> insertHandler) {
        addUnique("title", required(positionResolver), insertHandler);
        addUnique("description", optional(positionResolver), insertHandler);
        addUnique("termsOfService", optional(positionResolver), insertHandler);
        addUnique("contact", optional(positionResolver), insertHandler);
        addUnique("license", optional(positionResolver), insertHandler);
        addUnique("version", required(positionResolver), insertHandler);
    }

}
