package org.zalando.intellij.swagger.completion.level;

import com.intellij.codeInsight.completion.CompletionResultSet;
import org.zalando.intellij.swagger.completion.level.field.StringField;
import org.zalando.intellij.swagger.completion.traversal.PositionResolver;

import static org.zalando.intellij.swagger.completion.style.CompletionStyleFactory.optional;

class ExternalDocsLevelCompletion extends LevelCompletion {

    ExternalDocsLevelCompletion(final PositionResolver positionResolver, final CompletionResultSet completionResultSet) {
        super(positionResolver, completionResultSet);
    }

    @Override
    public void fill() {
        addUnique(new StringField("description"), optional(positionResolver));
        addUnique(new StringField("url"), optional(positionResolver));
    }
}
