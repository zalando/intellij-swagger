package org.zalando.intellij.swagger.completion.level;

import com.intellij.codeInsight.completion.CompletionResultSet;
import org.zalando.intellij.swagger.completion.level.field.ArrayField;
import org.zalando.intellij.swagger.completion.level.field.ObjectField;
import org.zalando.intellij.swagger.completion.level.field.RefField;
import org.zalando.intellij.swagger.completion.level.field.StringField;
import org.zalando.intellij.swagger.completion.traversal.PositionResolver;

import static org.zalando.intellij.swagger.completion.style.CompletionStyleFactory.optional;

public class SchemaLevelCompletion extends LevelCompletion {

    protected SchemaLevelCompletion(final PositionResolver positionResolver, final CompletionResultSet completionResultSet) {
        super(positionResolver, completionResultSet);
    }

    @Override
    public void fill() {
        addUnique(new RefField(), optional(positionResolver));
        addUnique(new StringField("format"), optional(positionResolver));
        addUnique(new StringField("title"), optional(positionResolver));
        addUnique(new StringField("description"), optional(positionResolver));
        addUnique(new StringField("default"), optional(positionResolver));
        addUnique(new StringField("multipleOf"), optional(positionResolver));
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
        addUnique(new StringField("maxProperties"), optional(positionResolver));
        addUnique(new StringField("minProperties"), optional(positionResolver));
        addUnique(new ArrayField("required"), optional(positionResolver));
        addUnique(new ArrayField("enum"), optional(positionResolver));
        addUnique(new StringField("type"), optional(positionResolver));
        addUnique(new ObjectField("items"), optional(positionResolver));
        addUnique(new ArrayField("allOf"), optional(positionResolver));
        addUnique(new ObjectField("properties"), optional(positionResolver));
        addUnique(new ObjectField("additionalProperties"), optional(positionResolver));
        addUnique(new StringField("discriminator"), optional(positionResolver));
        addUnique(new StringField("readOnly"), optional(positionResolver));
        addUnique(new ObjectField("xml"), optional(positionResolver));
        addUnique(new ObjectField("externalDocs"), optional(positionResolver));
        addUnique(new ObjectField("example"), optional(positionResolver));
    }
}
