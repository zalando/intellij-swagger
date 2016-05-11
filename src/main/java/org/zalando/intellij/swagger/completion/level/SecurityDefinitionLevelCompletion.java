package org.zalando.intellij.swagger.completion.level;

import com.intellij.codeInsight.completion.CompletionResultSet;
import org.jetbrains.annotations.NotNull;
import org.zalando.intellij.swagger.completion.traversal.PositionResolver;

import static org.zalando.intellij.swagger.completion.level.LookupElementBuilderFactory.create;
import static org.zalando.intellij.swagger.completion.style.CompletionStyleFactory.optional;
import static org.zalando.intellij.swagger.completion.style.CompletionStyleFactory.required;

class SecurityDefinitionLevelCompletion extends LevelCompletion {

    SecurityDefinitionLevelCompletion(final PositionResolver positionResolver) {
        super(positionResolver);
    }

    public void fill(@NotNull final CompletionResultSet result) {
        result.addElement(create("type", required(positionResolver)));
        result.addElement(create("description", optional(positionResolver)));
        result.addElement(create("name", required(positionResolver)));
        result.addElement(create("in", required(positionResolver)));
        result.addElement(create("flow", required(positionResolver)));
        result.addElement(create("authorizationUrl", required(positionResolver)));
        result.addElement(create("tokenUrl", required(positionResolver)));
        result.addElement(create("scopes", required(positionResolver)));

    }

}
