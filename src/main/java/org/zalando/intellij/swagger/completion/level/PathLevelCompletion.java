package org.zalando.intellij.swagger.completion.level;

import com.intellij.codeInsight.completion.CompletionResultSet;
import org.jetbrains.annotations.NotNull;
import org.zalando.intellij.swagger.completion.traversal.PositionResolver;

import static org.zalando.intellij.swagger.completion.level.LookupElementBuilderFactory.create;
import static org.zalando.intellij.swagger.completion.style.CompletionStyleFactory.optional;

class PathLevelCompletion extends LevelCompletion {

    PathLevelCompletion(final PositionResolver positionResolver) {
        super(positionResolver);
    }

    public void fill(@NotNull final CompletionResultSet result) {
        result.addElement(create("$ref", optional(positionResolver)));
        result.addElement(create("get", optional(positionResolver)));
        result.addElement(create("put", optional(positionResolver)));
        result.addElement(create("post", optional(positionResolver)));
        result.addElement(create("delete", optional(positionResolver)));
        result.addElement(create("options", optional(positionResolver)));
        result.addElement(create("head", optional(positionResolver)));
        result.addElement(create("patch", optional(positionResolver)));
        result.addElement(create("parameters", optional(positionResolver)));
    }

}
