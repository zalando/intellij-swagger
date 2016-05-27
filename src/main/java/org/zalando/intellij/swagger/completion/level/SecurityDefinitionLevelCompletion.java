package org.zalando.intellij.swagger.completion.level;

import com.intellij.codeInsight.completion.CompletionResultSet;
import org.zalando.intellij.swagger.completion.level.field.ObjectField;
import org.zalando.intellij.swagger.completion.level.field.StringField;
import org.zalando.intellij.swagger.completion.traversal.PositionResolver;

import static org.zalando.intellij.swagger.completion.style.CompletionStyleFactory.optional;
import static org.zalando.intellij.swagger.completion.style.CompletionStyleFactory.required;

class SecurityDefinitionLevelCompletion extends LevelCompletion {

    SecurityDefinitionLevelCompletion(final PositionResolver positionResolver, final CompletionResultSet completionResultSet) {
        super(positionResolver, completionResultSet);
    }

    public void fill() {
        addUnique(new StringField("type"), required(positionResolver));
        addUnique(new StringField("description"), optional(positionResolver));
        addUnique(new StringField("name"), required(positionResolver));
        addUnique(new StringField("in"), required(positionResolver));
        addUnique(new StringField("flow"), required(positionResolver));
        addUnique(new StringField("authorizationUrl"), required(positionResolver));
        addUnique(new StringField("tokenUrl"), required(positionResolver));
        addUnique(new ObjectField("scopes"), required(positionResolver));

    }

}
