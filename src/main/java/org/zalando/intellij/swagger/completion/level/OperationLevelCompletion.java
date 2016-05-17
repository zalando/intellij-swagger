package org.zalando.intellij.swagger.completion.level;

import com.intellij.codeInsight.completion.CompletionResultSet;
import org.jetbrains.annotations.NotNull;
import org.zalando.intellij.swagger.completion.traversal.PositionResolver;

import static org.zalando.intellij.swagger.completion.style.CompletionStyleFactory.optional;
import static org.zalando.intellij.swagger.completion.style.CompletionStyleFactory.required;

class OperationLevelCompletion extends LevelCompletion {

    OperationLevelCompletion(final PositionResolver positionResolver) {
        super(positionResolver);
    }

    public void fill(@NotNull final CompletionResultSet result) {
        result.addElement(LookupElementBuilderFactory.create("tags", optional(positionResolver)));
        result.addElement(LookupElementBuilderFactory.create("summary", optional(positionResolver)));
        result.addElement(LookupElementBuilderFactory.create("description", optional(positionResolver)));
        result.addElement(LookupElementBuilderFactory.create("externalDocs", optional(positionResolver)));
        result.addElement(LookupElementBuilderFactory.create("operationId", optional(positionResolver)));
        result.addElement(LookupElementBuilderFactory.create("consumes", optional(positionResolver)));
        result.addElement(LookupElementBuilderFactory.create("produces", optional(positionResolver)));
        result.addElement(LookupElementBuilderFactory.create("parameters", optional(positionResolver)));
        result.addElement(LookupElementBuilderFactory.create("responses", required(positionResolver)));
        result.addElement(LookupElementBuilderFactory.create("schemes", optional(positionResolver)));
        result.addElement(LookupElementBuilderFactory.create("deprecated", optional(positionResolver)));
        result.addElement(LookupElementBuilderFactory.create("security", optional(positionResolver)));
    }

}
