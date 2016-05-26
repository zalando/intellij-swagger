package org.zalando.intellij.swagger.completion.level;

import com.intellij.codeInsight.completion.CompletionResultSet;
import com.intellij.codeInsight.completion.InsertHandler;
import com.intellij.codeInsight.lookup.LookupElement;
import org.jetbrains.annotations.NotNull;
import org.zalando.intellij.swagger.completion.traversal.PositionResolver;

import static org.zalando.intellij.swagger.completion.style.CompletionStyleFactory.optional;
import static org.zalando.intellij.swagger.completion.style.CompletionStyleFactory.required;

class OperationLevelCompletion extends LevelCompletion {

    OperationLevelCompletion(final PositionResolver positionResolver, final CompletionResultSet completionResultSet) {
        super(positionResolver, completionResultSet);
    }

    public void fill(@NotNull final InsertHandler<LookupElement> insertHandler) {
        addUnique("tags", optional(positionResolver), insertHandler);
        addUnique("summary", optional(positionResolver), insertHandler);
        addUnique("description", optional(positionResolver), insertHandler);
        addUnique("externalDocs", optional(positionResolver), insertHandler);
        addUnique("operationId", optional(positionResolver), insertHandler);
        addUnique("consumes", optional(positionResolver), insertHandler);
        addUnique("produces", optional(positionResolver), insertHandler);
        addUnique("parameters", optional(positionResolver), insertHandler);
        addUnique("responses", required(positionResolver), insertHandler);
        addUnique("schemes", optional(positionResolver), insertHandler);
        addUnique("deprecated", optional(positionResolver), insertHandler);
        addUnique("security", optional(positionResolver), insertHandler);
    }

}
