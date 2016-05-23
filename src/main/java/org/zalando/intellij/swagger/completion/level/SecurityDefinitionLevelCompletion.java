package org.zalando.intellij.swagger.completion.level;

import com.intellij.codeInsight.completion.CompletionResultSet;
import com.intellij.codeInsight.completion.InsertHandler;
import com.intellij.codeInsight.lookup.LookupElement;
import org.jetbrains.annotations.NotNull;
import org.zalando.intellij.swagger.completion.traversal.PositionResolver;

import static org.zalando.intellij.swagger.completion.level.LookupElementBuilderFactory.create;
import static org.zalando.intellij.swagger.completion.style.CompletionStyleFactory.optional;
import static org.zalando.intellij.swagger.completion.style.CompletionStyleFactory.required;

class SecurityDefinitionLevelCompletion extends LevelCompletion {

    SecurityDefinitionLevelCompletion(final PositionResolver positionResolver) {
        super(positionResolver);
    }

    public void fill(@NotNull final CompletionResultSet result,
                     @NotNull final InsertHandler<LookupElement> insertHandler) {
        result.addElement(create("type", required(positionResolver), insertHandler));
        result.addElement(create("description", optional(positionResolver), insertHandler));
        result.addElement(create("name", required(positionResolver), insertHandler));
        result.addElement(create("in", required(positionResolver), insertHandler));
        result.addElement(create("flow", required(positionResolver), insertHandler));
        result.addElement(create("authorizationUrl", required(positionResolver), insertHandler));
        result.addElement(create("tokenUrl", required(positionResolver), insertHandler));
        result.addElement(create("scopes", required(positionResolver), insertHandler));

    }

}
