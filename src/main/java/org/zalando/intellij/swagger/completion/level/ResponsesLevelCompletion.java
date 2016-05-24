package org.zalando.intellij.swagger.completion.level;

import com.intellij.codeInsight.completion.CompletionResultSet;
import com.intellij.codeInsight.completion.InsertHandler;
import com.intellij.codeInsight.lookup.LookupElement;
import org.jetbrains.annotations.NotNull;
import org.zalando.intellij.swagger.completion.traversal.PositionResolver;

import static org.zalando.intellij.swagger.completion.level.LookupElementBuilderFactory.create;
import static org.zalando.intellij.swagger.completion.style.CompletionStyleFactory.optional;

class ResponsesLevelCompletion extends LevelCompletion {

    ResponsesLevelCompletion(final PositionResolver positionResolver) {
        super(positionResolver);
    }

    public void fill(@NotNull final CompletionResultSet result,
                     @NotNull final InsertHandler<LookupElement> insertHandler) {
        result.addElement(create("default", optional(positionResolver), insertHandler));
        result.addElement(create("200", optional(positionResolver), insertHandler));
        result.addElement(create("201", optional(positionResolver), insertHandler));
        result.addElement(create("202", optional(positionResolver), insertHandler));
        result.addElement(create("203", optional(positionResolver), insertHandler));
        result.addElement(create("204", optional(positionResolver), insertHandler));
        result.addElement(create("205", optional(positionResolver), insertHandler));
        result.addElement(create("206", optional(positionResolver), insertHandler));
        result.addElement(create("207", optional(positionResolver), insertHandler));
        result.addElement(create("208", optional(positionResolver), insertHandler));
        result.addElement(create("226", optional(positionResolver), insertHandler));

        result.addElement(create("300", optional(positionResolver), insertHandler));
        result.addElement(create("301", optional(positionResolver), insertHandler));
        result.addElement(create("302", optional(positionResolver), insertHandler));
        result.addElement(create("303", optional(positionResolver), insertHandler));
        result.addElement(create("304", optional(positionResolver), insertHandler));
        result.addElement(create("305", optional(positionResolver), insertHandler));
        result.addElement(create("306", optional(positionResolver), insertHandler));
        result.addElement(create("307", optional(positionResolver), insertHandler));
        result.addElement(create("308", optional(positionResolver), insertHandler));

        result.addElement(create("400", optional(positionResolver), insertHandler));
        result.addElement(create("401", optional(positionResolver), insertHandler));
        result.addElement(create("402", optional(positionResolver), insertHandler));
        result.addElement(create("403", optional(positionResolver), insertHandler));
        result.addElement(create("404", optional(positionResolver), insertHandler));
        result.addElement(create("405", optional(positionResolver), insertHandler));
        result.addElement(create("406", optional(positionResolver), insertHandler));
        result.addElement(create("407", optional(positionResolver), insertHandler));
        result.addElement(create("408", optional(positionResolver), insertHandler));
        result.addElement(create("409", optional(positionResolver), insertHandler));
        result.addElement(create("410", optional(positionResolver), insertHandler));
        result.addElement(create("411", optional(positionResolver), insertHandler));
        result.addElement(create("412", optional(positionResolver), insertHandler));
        result.addElement(create("413", optional(positionResolver), insertHandler));
        result.addElement(create("414", optional(positionResolver), insertHandler));
        result.addElement(create("415", optional(positionResolver), insertHandler));
        result.addElement(create("416", optional(positionResolver), insertHandler));
        result.addElement(create("417", optional(positionResolver), insertHandler));
        result.addElement(create("418", optional(positionResolver), insertHandler));
        result.addElement(create("421", optional(positionResolver), insertHandler));
        result.addElement(create("422", optional(positionResolver), insertHandler));
        result.addElement(create("423", optional(positionResolver), insertHandler));
        result.addElement(create("424", optional(positionResolver), insertHandler));
        result.addElement(create("426", optional(positionResolver), insertHandler));
        result.addElement(create("428", optional(positionResolver), insertHandler));
        result.addElement(create("429", optional(positionResolver), insertHandler));
        result.addElement(create("431", optional(positionResolver), insertHandler));
        result.addElement(create("451", optional(positionResolver), insertHandler));

        result.addElement(create("500", optional(positionResolver), insertHandler));
        result.addElement(create("501", optional(positionResolver), insertHandler));
        result.addElement(create("502", optional(positionResolver), insertHandler));
        result.addElement(create("503", optional(positionResolver), insertHandler));
        result.addElement(create("504", optional(positionResolver), insertHandler));
        result.addElement(create("505", optional(positionResolver), insertHandler));
        result.addElement(create("506", optional(positionResolver), insertHandler));
        result.addElement(create("507", optional(positionResolver), insertHandler));
        result.addElement(create("508", optional(positionResolver), insertHandler));
        result.addElement(create("510", optional(positionResolver), insertHandler));
        result.addElement(create("511", optional(positionResolver), insertHandler));
    }

}
