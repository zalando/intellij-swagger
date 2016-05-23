package org.zalando.intellij.swagger.completion.level;

import com.intellij.codeInsight.completion.CompletionResultSet;
import com.intellij.codeInsight.completion.InsertHandler;
import com.intellij.codeInsight.lookup.LookupElement;
import org.jetbrains.annotations.NotNull;
import org.zalando.intellij.swagger.completion.traversal.PositionResolver;

import static org.zalando.intellij.swagger.completion.level.LookupElementBuilderFactory.*;
import static org.zalando.intellij.swagger.completion.style.CompletionStyleFactory.optional;
import static org.zalando.intellij.swagger.completion.style.CompletionStyleFactory.required;

class OperationLevelCompletion extends LevelCompletion {

    OperationLevelCompletion(final PositionResolver positionResolver) {
        super(positionResolver);
    }

    public void fill(@NotNull final CompletionResultSet result,
                     @NotNull final InsertHandler<LookupElement> insertHandler) {
        result.addElement(create("tags", optional(positionResolver), insertHandler));
        result.addElement(create("summary", optional(positionResolver), insertHandler));
        result.addElement(create("description", optional(positionResolver), insertHandler));
        result.addElement(create("externalDocs", optional(positionResolver), insertHandler));
        result.addElement(create("operationId", optional(positionResolver), insertHandler));
        result.addElement(create("consumes", optional(positionResolver), insertHandler));
        result.addElement(create("produces", optional(positionResolver), insertHandler));
        result.addElement(create("parameters", optional(positionResolver), insertHandler));
        result.addElement(create("responses", required(positionResolver), insertHandler));
        result.addElement(create("schemes", optional(positionResolver), insertHandler));
        result.addElement(create("deprecated", optional(positionResolver), insertHandler));
        result.addElement(create("security", optional(positionResolver), insertHandler));
    }

}
