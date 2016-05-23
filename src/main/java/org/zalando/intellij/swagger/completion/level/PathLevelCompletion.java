package org.zalando.intellij.swagger.completion.level;

import com.intellij.codeInsight.completion.CompletionResultSet;
import com.intellij.codeInsight.completion.InsertHandler;
import com.intellij.codeInsight.lookup.LookupElement;
import org.jetbrains.annotations.NotNull;
import org.zalando.intellij.swagger.completion.traversal.PositionResolver;

import static org.zalando.intellij.swagger.completion.level.LookupElementBuilderFactory.create;
import static org.zalando.intellij.swagger.completion.style.CompletionStyleFactory.optional;

class PathLevelCompletion extends LevelCompletion {

    PathLevelCompletion(final PositionResolver positionResolver) {
        super(positionResolver);
    }

    public void fill(@NotNull final CompletionResultSet result,
                     @NotNull final InsertHandler<LookupElement> insertHandler) {
        result.addElement(create("$ref", optional(positionResolver), insertHandler));
        result.addElement(create("get", optional(positionResolver), insertHandler));
        result.addElement(create("put", optional(positionResolver), insertHandler));
        result.addElement(create("post", optional(positionResolver), insertHandler));
        result.addElement(create("delete", optional(positionResolver), insertHandler));
        result.addElement(create("options", optional(positionResolver), insertHandler));
        result.addElement(create("head", optional(positionResolver), insertHandler));
        result.addElement(create("patch", optional(positionResolver), insertHandler));
        result.addElement(create("parameters", optional(positionResolver), insertHandler));
    }

}
