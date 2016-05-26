package org.zalando.intellij.swagger.completion.level;

import com.intellij.codeInsight.completion.CompletionResultSet;
import com.intellij.codeInsight.completion.InsertHandler;
import com.intellij.codeInsight.lookup.LookupElement;
import org.jetbrains.annotations.NotNull;
import org.zalando.intellij.swagger.completion.traversal.PositionResolver;

import static org.zalando.intellij.swagger.completion.style.CompletionStyleFactory.optional;

class XmlLevelCompletion extends LevelCompletion {

    XmlLevelCompletion(final PositionResolver positionResolver, final CompletionResultSet completionResultSet) {
        super(positionResolver, completionResultSet);
    }

    public void fill(@NotNull final InsertHandler<LookupElement> insertHandler) {
        addUnique("name", optional(positionResolver), insertHandler);
        addUnique("namespace", optional(positionResolver), insertHandler);
        addUnique("prefix", optional(positionResolver), insertHandler);
        addUnique("attribute", optional(positionResolver), insertHandler);
        addUnique("wrapped", optional(positionResolver), insertHandler);
    }

}
