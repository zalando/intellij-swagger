package org.zalando.intellij.swagger.completion.level;

import com.intellij.codeInsight.completion.CompletionResultSet;
import org.jetbrains.annotations.NotNull;
import org.zalando.intellij.swagger.completion.traversal.PositionResolver;

import static org.zalando.intellij.swagger.completion.style.CompletionStyleFactory.optional;
import static org.zalando.intellij.swagger.completion.style.CompletionStyleFactory.required;

class InfoLevelCompletion extends LevelCompletion {

    InfoLevelCompletion(final PositionResolver positionResolver) {
        super(positionResolver);
    }

    public void fill(@NotNull final CompletionResultSet result) {
        result.addElement(LookupElementBuilderFactory.create("title", required(positionResolver)));
        result.addElement(LookupElementBuilderFactory.create("description", optional(positionResolver)));
        result.addElement(LookupElementBuilderFactory.create("termsOfService", optional(positionResolver)));
        result.addElement(LookupElementBuilderFactory.create("contact", optional(positionResolver)));
        result.addElement(LookupElementBuilderFactory.create("license", optional(positionResolver)));
        result.addElement(LookupElementBuilderFactory.create("version", required(positionResolver)));
    }

}
