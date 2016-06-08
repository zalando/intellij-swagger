package org.zalando.intellij.swagger.completion.level;

import static org.zalando.intellij.swagger.completion.style.CompletionStyleFactory.optional;
import static org.zalando.intellij.swagger.completion.style.CompletionStyleFactory.required;
import com.intellij.codeInsight.completion.CompletionResultSet;
import org.zalando.intellij.swagger.completion.level.field.ExternalDocsField;
import org.zalando.intellij.swagger.completion.level.field.StringField;
import org.zalando.intellij.swagger.completion.traversal.PositionResolver;

class TagsLevelCompletion extends LevelCompletion {

    TagsLevelCompletion(final PositionResolver positionResolver, final CompletionResultSet completionResultSet) {
        super(positionResolver, completionResultSet);
    }

    public void fill() {
        addUnique(new StringField("name"), required(positionResolver));
        addUnique(new StringField("description"), optional(positionResolver));
        addUnique(new ExternalDocsField(), optional(positionResolver));
    }

}
