package org.zalando.intellij.swagger.completion.level;

import com.intellij.codeInsight.completion.CompletionResultSet;
import com.intellij.codeInsight.completion.InsertHandler;
import com.intellij.codeInsight.lookup.LookupElement;
import org.jetbrains.annotations.NotNull;
import org.zalando.intellij.swagger.completion.traversal.PositionResolver;

import static org.zalando.intellij.swagger.completion.style.CompletionStyleFactory.optional;
import static org.zalando.intellij.swagger.completion.style.CompletionStyleFactory.required;

class SecurityDefinitionLevelCompletion extends LevelCompletion {

    SecurityDefinitionLevelCompletion(final PositionResolver positionResolver, final CompletionResultSet completionResultSet) {
        super(positionResolver, completionResultSet);
    }

    public void fill(@NotNull final InsertHandler<LookupElement> insertHandler) {
        addUnique("type", required(positionResolver), insertHandler);
        addUnique("description", optional(positionResolver), insertHandler);
        addUnique("name", required(positionResolver), insertHandler);
        addUnique("in", required(positionResolver), insertHandler);
        addUnique("flow", required(positionResolver), insertHandler);
        addUnique("authorizationUrl", required(positionResolver), insertHandler);
        addUnique("tokenUrl", required(positionResolver), insertHandler);
        addUnique("scopes", required(positionResolver), insertHandler);

    }

}
