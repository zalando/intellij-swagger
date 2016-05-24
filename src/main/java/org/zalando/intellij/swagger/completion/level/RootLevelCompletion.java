package org.zalando.intellij.swagger.completion.level;

import com.intellij.codeInsight.completion.CompletionResultSet;
import com.intellij.codeInsight.completion.InsertHandler;
import com.intellij.codeInsight.lookup.LookupElement;
import org.jetbrains.annotations.NotNull;
import org.zalando.intellij.swagger.completion.traversal.PositionResolver;

import static org.zalando.intellij.swagger.completion.level.LookupElementBuilderFactory.create;
import static org.zalando.intellij.swagger.completion.style.CompletionStyleFactory.optional;
import static org.zalando.intellij.swagger.completion.style.CompletionStyleFactory.required;

class RootLevelCompletion extends LevelCompletion {

    RootLevelCompletion(final PositionResolver positionResolver) {
        super(positionResolver);
    }

    public void fill(@NotNull final CompletionResultSet result,
                     @NotNull final InsertHandler<LookupElement> insertHandler) {
        result.addElement(create("swagger", required(positionResolver), insertHandler));
        result.addElement(create("info", required(positionResolver), insertHandler));
        result.addElement(create("host", optional(positionResolver), insertHandler));
        result.addElement(create("basePath", optional(positionResolver), insertHandler));
        result.addElement(create("schemes", optional(positionResolver), insertHandler));
        result.addElement(create("consumes", optional(positionResolver), insertHandler));
        result.addElement(create("produces", optional(positionResolver), insertHandler));
        result.addElement(create("paths", required(positionResolver), insertHandler));
        result.addElement(create("definitions", optional(positionResolver), insertHandler));
        result.addElement(create("parameters", optional(positionResolver), insertHandler));
        result.addElement(create("responses", optional(positionResolver), insertHandler));
        result.addElement(create("securityDefinitions", optional(positionResolver), insertHandler));
        result.addElement(create("security", optional(positionResolver), insertHandler));
        result.addElement(create("tags", optional(positionResolver), insertHandler));
        result.addElement(create("externalDocs", optional(positionResolver), insertHandler));
    }

}
