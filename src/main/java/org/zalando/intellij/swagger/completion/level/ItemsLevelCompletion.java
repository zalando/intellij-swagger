package org.zalando.intellij.swagger.completion.level;

import com.intellij.codeInsight.completion.CompletionResultSet;
import org.zalando.intellij.swagger.completion.level.field.ArrayField;
import org.zalando.intellij.swagger.completion.level.field.ObjectField;
import org.zalando.intellij.swagger.completion.level.field.StringField;
import org.zalando.intellij.swagger.completion.traversal.PositionResolver;

import static org.zalando.intellij.swagger.completion.style.CompletionStyleFactory.optional;
import static org.zalando.intellij.swagger.completion.style.CompletionStyleFactory.required;

class ItemsLevelCompletion extends LevelCompletion {

    ItemsLevelCompletion(final PositionResolver positionResolver, final CompletionResultSet completionResultSet) {
        super(positionResolver, completionResultSet);
    }

    public void fill() {
        addUnique(new StringField("type"), required(positionResolver));
        addUnique(new StringField("format"), optional(positionResolver));
        addUnique(new ObjectField("items"), optional(positionResolver));
        addUnique(new StringField("collectionFormat"), optional(positionResolver));
        addUnique(new StringField("default"), optional(positionResolver));
        addUnique(new StringField("maximum"), optional(positionResolver));
        addUnique(new StringField("exclusiveMaximum"), optional(positionResolver));
        addUnique(new StringField("minimum"), optional(positionResolver));
        addUnique(new StringField("exclusiveMinimum"), optional(positionResolver));
        addUnique(new StringField("maxLength"), optional(positionResolver));
        addUnique(new StringField("minLength"), optional(positionResolver));
        addUnique(new StringField("pattern"), optional(positionResolver));
        addUnique(new StringField("maxItems"), optional(positionResolver));
        addUnique(new StringField("minItems"), optional(positionResolver));
        addUnique(new StringField("uniqueItems"), optional(positionResolver));
        addUnique(new ArrayField("enum"), optional(positionResolver));
        addUnique(new StringField("multipleOf"), optional(positionResolver));
    }

}
