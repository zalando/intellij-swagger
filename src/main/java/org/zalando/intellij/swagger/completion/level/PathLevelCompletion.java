package org.zalando.intellij.swagger.completion.level;

import com.intellij.codeInsight.completion.CompletionResultSet;
import com.intellij.codeInsight.completion.InsertHandler;
import com.intellij.codeInsight.lookup.LookupElement;
import org.jetbrains.annotations.NotNull;
import org.zalando.intellij.swagger.completion.traversal.PositionResolver;

import static org.zalando.intellij.swagger.completion.style.CompletionStyleFactory.optional;

class PathLevelCompletion extends LevelCompletion {

    PathLevelCompletion(final PositionResolver positionResolver, final CompletionResultSet completionResultSet) {
        super(positionResolver, completionResultSet);
    }

    public void fill(@NotNull final InsertHandler<LookupElement> insertHandler) {
        addUnique("$ref", optional(positionResolver), insertHandler);
        addUnique("get", optional(positionResolver), insertHandler);
        addUnique("put", optional(positionResolver), insertHandler);
        addUnique("post", optional(positionResolver), insertHandler);
        addUnique("delete", optional(positionResolver), insertHandler);
        addUnique("options", optional(positionResolver), insertHandler);
        addUnique("head", optional(positionResolver), insertHandler);
        addUnique("patch", optional(positionResolver), insertHandler);
        addUnique("parameters", optional(positionResolver), insertHandler);
    }

}
