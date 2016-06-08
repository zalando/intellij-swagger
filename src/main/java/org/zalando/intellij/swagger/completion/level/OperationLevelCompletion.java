package org.zalando.intellij.swagger.completion.level;

import static org.zalando.intellij.swagger.completion.style.CompletionStyleFactory.optional;
import static org.zalando.intellij.swagger.completion.style.CompletionStyleFactory.required;
import com.intellij.codeInsight.completion.CompletionResultSet;
import org.zalando.intellij.swagger.completion.level.field.ArrayField;
import org.zalando.intellij.swagger.completion.level.field.ExternalDocsField;
import org.zalando.intellij.swagger.completion.level.field.ObjectField;
import org.zalando.intellij.swagger.completion.level.field.StringField;
import org.zalando.intellij.swagger.completion.traversal.PositionResolver;

class OperationLevelCompletion extends LevelCompletion {

    OperationLevelCompletion(final PositionResolver positionResolver, final CompletionResultSet completionResultSet) {
        super(positionResolver, completionResultSet);
    }

    public void fill() {
        addUnique(new ArrayField("tags"), optional(positionResolver));
        addUnique(new StringField("summary"), optional(positionResolver));
        addUnique(new StringField("description"), optional(positionResolver));
        addUnique(new ExternalDocsField(), optional(positionResolver));
        addUnique(new StringField("operationId"), optional(positionResolver));
        addUnique(new ArrayField("consumes"), optional(positionResolver));
        addUnique(new ArrayField("produces"), optional(positionResolver));
        addUnique(new ArrayField("parameters"), optional(positionResolver));
        addUnique(new ObjectField("responses"), required(positionResolver));
        addUnique(new ArrayField("schemes"), optional(positionResolver));
        addUnique(new StringField("deprecated"), optional(positionResolver));
        addUnique(new ArrayField("security"), optional(positionResolver));
    }

}
