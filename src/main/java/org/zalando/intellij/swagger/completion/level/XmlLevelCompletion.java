package org.zalando.intellij.swagger.completion.level;

import com.intellij.codeInsight.completion.CompletionResultSet;
import org.zalando.intellij.swagger.completion.level.field.StringField;
import org.zalando.intellij.swagger.completion.traversal.PositionResolver;

import static org.zalando.intellij.swagger.completion.style.CompletionStyleFactory.optional;

class XmlLevelCompletion extends LevelCompletion {

    XmlLevelCompletion(final PositionResolver positionResolver, final CompletionResultSet completionResultSet) {
        super(positionResolver, completionResultSet);
    }

    public void fill() {
        addUnique(new StringField("name"), optional(positionResolver));
        addUnique(new StringField("namespace"), optional(positionResolver));
        addUnique(new StringField("prefix"), optional(positionResolver));
        addUnique(new StringField("attribute"), optional(positionResolver));
        addUnique(new StringField("wrapped"), optional(positionResolver));
    }

}
