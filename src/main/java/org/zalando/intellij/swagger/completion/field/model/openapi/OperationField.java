package org.zalando.intellij.swagger.completion.field.model.openapi;

import com.google.common.collect.ImmutableList;
import org.zalando.intellij.swagger.completion.field.model.common.ArrayField;
import org.zalando.intellij.swagger.completion.field.model.common.Field;
import org.zalando.intellij.swagger.completion.field.model.common.ObjectField;
import org.zalando.intellij.swagger.completion.field.model.common.StringField;

import java.util.List;

class OperationField extends ObjectField {

    private static final List<Field> REQUIRED_FIELDS =
            ImmutableList.of(new StringField("summary"),
                    new StringField("operationId"),
                    new ArrayField("parameters"),
                    new ObjectField("responses"));

    OperationField(final String name) {
        super(name);
    }

    @Override
    public List<Field> getChildren() {
        return REQUIRED_FIELDS;
    }
}
