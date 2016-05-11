package org.zalando.intellij.swagger.completion.level;

import com.intellij.codeInsight.completion.CompletionResultSet;
import org.jetbrains.annotations.NotNull;
import org.zalando.intellij.swagger.completion.traversal.PositionResolver;

import static org.zalando.intellij.swagger.completion.level.LookupElementBuilderFactory.create;
import static org.zalando.intellij.swagger.completion.style.CompletionStyleFactory.optional;

class XmlLevelCompletion extends LevelCompletion {

    XmlLevelCompletion(final PositionResolver positionResolver) {
        super(positionResolver);
    }

    public void fill(@NotNull final CompletionResultSet result) {
        result.addElement(create("name", optional(positionResolver)));
        result.addElement(create("namespace", optional(positionResolver)));
        result.addElement(create("prefix", optional(positionResolver)));
        result.addElement(create("attribute", optional(positionResolver)));
        result.addElement(create("wrapped", optional(positionResolver)));
    }

}
