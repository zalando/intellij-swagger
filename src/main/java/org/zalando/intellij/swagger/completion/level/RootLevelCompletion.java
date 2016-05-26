package org.zalando.intellij.swagger.completion.level;

import com.intellij.codeInsight.completion.CompletionResultSet;
import com.intellij.codeInsight.completion.InsertHandler;
import com.intellij.codeInsight.lookup.LookupElement;
import org.jetbrains.annotations.NotNull;
import org.zalando.intellij.swagger.completion.traversal.PositionResolver;

import static org.zalando.intellij.swagger.completion.style.CompletionStyleFactory.optional;
import static org.zalando.intellij.swagger.completion.style.CompletionStyleFactory.required;

class RootLevelCompletion extends LevelCompletion {

    RootLevelCompletion(final PositionResolver positionResolver, final CompletionResultSet completionResultSet) {
        super(positionResolver, completionResultSet);
    }

    public void fill(@NotNull final InsertHandler<LookupElement> insertHandler) {
        addUnique("swagger", required(positionResolver), insertHandler);
        addUnique("info", required(positionResolver), insertHandler);
        addUnique("host", optional(positionResolver), insertHandler);
        addUnique("basePath", optional(positionResolver), insertHandler);
        addUnique("schemes", optional(positionResolver), insertHandler);
        addUnique("consumes", optional(positionResolver), insertHandler);
        addUnique("produces", optional(positionResolver), insertHandler);
        addUnique("paths", required(positionResolver), insertHandler);
        addUnique("definitions", optional(positionResolver), insertHandler);
        addUnique("parameters", optional(positionResolver), insertHandler);
        addUnique("responses", optional(positionResolver), insertHandler);
        addUnique("securityDefinitions", optional(positionResolver), insertHandler);
        addUnique("security", optional(positionResolver), insertHandler);
        addUnique("tags", optional(positionResolver), insertHandler);
        addUnique("externalDocs", optional(positionResolver), insertHandler);
    }

}
