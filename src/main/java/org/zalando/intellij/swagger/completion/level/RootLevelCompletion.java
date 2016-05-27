package org.zalando.intellij.swagger.completion.level;

import com.intellij.codeInsight.completion.CompletionResultSet;
import org.zalando.intellij.swagger.completion.level.field.ArrayField;
import org.zalando.intellij.swagger.completion.level.field.ObjectField;
import org.zalando.intellij.swagger.completion.level.field.StringField;
import org.zalando.intellij.swagger.completion.traversal.PositionResolver;

import static org.zalando.intellij.swagger.completion.style.CompletionStyleFactory.optional;
import static org.zalando.intellij.swagger.completion.style.CompletionStyleFactory.required;

class RootLevelCompletion extends LevelCompletion {

    RootLevelCompletion(final PositionResolver positionResolver, final CompletionResultSet completionResultSet) {
        super(positionResolver, completionResultSet);
    }

    public void fill() {
        addUnique(new StringField("swagger"), required(positionResolver));
        addUnique(new ObjectField("info"), required(positionResolver));
        addUnique(new StringField("host"), optional(positionResolver));
        addUnique(new StringField("basePath"), optional(positionResolver));
        addUnique(new ArrayField("schemes"), optional(positionResolver));
        addUnique(new ArrayField("consumes"), optional(positionResolver));
        addUnique(new ArrayField("produces"), optional(positionResolver));
        addUnique(new ObjectField("paths"), required(positionResolver));
        addUnique(new ObjectField("definitions"), optional(positionResolver));
        addUnique(new ObjectField("parameters"), optional(positionResolver));
        addUnique(new ObjectField("responses"), optional(positionResolver));
        addUnique(new ObjectField("securityDefinitions"), optional(positionResolver));
        addUnique(new ArrayField("security"), optional(positionResolver));
        addUnique(new ArrayField("tags"), optional(positionResolver));
        addUnique(new ObjectField("externalDocs"), optional(positionResolver));
    }

}
