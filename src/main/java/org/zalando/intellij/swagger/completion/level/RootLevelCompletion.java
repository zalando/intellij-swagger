package org.zalando.intellij.swagger.completion.level;

import com.intellij.codeInsight.completion.CompletionResultSet;
import org.jetbrains.annotations.NotNull;
import org.zalando.intellij.swagger.completion.traversal.PositionResolver;

import static org.zalando.intellij.swagger.completion.level.LookupElementBuilderFactory.create;
import static org.zalando.intellij.swagger.completion.style.CompletionStyleFactory.optional;
import static org.zalando.intellij.swagger.completion.style.CompletionStyleFactory.required;

class RootLevelCompletion extends LevelCompletion {

    RootLevelCompletion(final PositionResolver positionResolver) {
        super(positionResolver);
    }

    public void fill(@NotNull final CompletionResultSet result) {
        result.addElement(create("swagger", required(positionResolver)));
        result.addElement(create("info", required(positionResolver)));
        result.addElement(create("host", optional(positionResolver)));
        result.addElement(create("basePath", optional(positionResolver)));
        result.addElement(create("schemes", optional(positionResolver)));
        result.addElement(create("consumes", optional(positionResolver)));
        result.addElement(create("produces", optional(positionResolver)));
        result.addElement(create("paths", required(positionResolver)));
        result.addElement(create("definitions", optional(positionResolver)));
        result.addElement(create("parameters", optional(positionResolver)));
        result.addElement(create("responses", optional(positionResolver)));
        result.addElement(create("securityDefinitions", optional(positionResolver)));
        result.addElement(create("security", optional(positionResolver)));
        result.addElement(create("tags", optional(positionResolver)));
        result.addElement(create("externalDocs", optional(positionResolver)));
    }

}
