package org.zalando.intellij.swagger.completion.level;

import com.intellij.codeInsight.completion.CompletionResultSet;
import com.intellij.codeInsight.completion.InsertHandler;
import com.intellij.codeInsight.lookup.LookupElement;
import org.jetbrains.annotations.NotNull;
import org.zalando.intellij.swagger.completion.traversal.PositionResolver;

import static org.zalando.intellij.swagger.completion.style.CompletionStyleFactory.optional;

class ResponsesLevelCompletion extends LevelCompletion {

    ResponsesLevelCompletion(final PositionResolver positionResolver, final CompletionResultSet completionResultSet) {
        super(positionResolver, completionResultSet);
    }

    public void fill(@NotNull final InsertHandler<LookupElement> insertHandler) {
        addUnique("default", optional(positionResolver), insertHandler);
        addUnique("200", optional(positionResolver), insertHandler);
        addUnique("201", optional(positionResolver), insertHandler);
        addUnique("202", optional(positionResolver), insertHandler);
        addUnique("203", optional(positionResolver), insertHandler);
        addUnique("204", optional(positionResolver), insertHandler);
        addUnique("205", optional(positionResolver), insertHandler);
        addUnique("206", optional(positionResolver), insertHandler);
        addUnique("207", optional(positionResolver), insertHandler);
        addUnique("208", optional(positionResolver), insertHandler);
        addUnique("226", optional(positionResolver), insertHandler);

        addUnique("300", optional(positionResolver), insertHandler);
        addUnique("301", optional(positionResolver), insertHandler);
        addUnique("302", optional(positionResolver), insertHandler);
        addUnique("303", optional(positionResolver), insertHandler);
        addUnique("304", optional(positionResolver), insertHandler);
        addUnique("305", optional(positionResolver), insertHandler);
        addUnique("306", optional(positionResolver), insertHandler);
        addUnique("307", optional(positionResolver), insertHandler);
        addUnique("308", optional(positionResolver), insertHandler);

        addUnique("400", optional(positionResolver), insertHandler);
        addUnique("401", optional(positionResolver), insertHandler);
        addUnique("402", optional(positionResolver), insertHandler);
        addUnique("403", optional(positionResolver), insertHandler);
        addUnique("404", optional(positionResolver), insertHandler);
        addUnique("405", optional(positionResolver), insertHandler);
        addUnique("406", optional(positionResolver), insertHandler);
        addUnique("407", optional(positionResolver), insertHandler);
        addUnique("408", optional(positionResolver), insertHandler);
        addUnique("409", optional(positionResolver), insertHandler);
        addUnique("410", optional(positionResolver), insertHandler);
        addUnique("411", optional(positionResolver), insertHandler);
        addUnique("412", optional(positionResolver), insertHandler);
        addUnique("413", optional(positionResolver), insertHandler);
        addUnique("414", optional(positionResolver), insertHandler);
        addUnique("415", optional(positionResolver), insertHandler);
        addUnique("416", optional(positionResolver), insertHandler);
        addUnique("417", optional(positionResolver), insertHandler);
        addUnique("418", optional(positionResolver), insertHandler);
        addUnique("421", optional(positionResolver), insertHandler);
        addUnique("422", optional(positionResolver), insertHandler);
        addUnique("423", optional(positionResolver), insertHandler);
        addUnique("424", optional(positionResolver), insertHandler);
        addUnique("426", optional(positionResolver), insertHandler);
        addUnique("428", optional(positionResolver), insertHandler);
        addUnique("429", optional(positionResolver), insertHandler);
        addUnique("431", optional(positionResolver), insertHandler);
        addUnique("451", optional(positionResolver), insertHandler);

        addUnique("500", optional(positionResolver), insertHandler);
        addUnique("501", optional(positionResolver), insertHandler);
        addUnique("502", optional(positionResolver), insertHandler);
        addUnique("503", optional(positionResolver), insertHandler);
        addUnique("504", optional(positionResolver), insertHandler);
        addUnique("505", optional(positionResolver), insertHandler);
        addUnique("506", optional(positionResolver), insertHandler);
        addUnique("507", optional(positionResolver), insertHandler);
        addUnique("508", optional(positionResolver), insertHandler);
        addUnique("510", optional(positionResolver), insertHandler);
        addUnique("511", optional(positionResolver), insertHandler);
    }

}
