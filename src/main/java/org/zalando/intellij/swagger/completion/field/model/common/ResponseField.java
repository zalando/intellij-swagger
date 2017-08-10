package org.zalando.intellij.swagger.completion.field.model.common;

import com.google.common.collect.ImmutableList;
import org.zalando.intellij.swagger.completion.field.model.common.Field;
import org.zalando.intellij.swagger.completion.field.model.common.ObjectField;
import org.zalando.intellij.swagger.completion.field.model.common.StringField;

import java.util.List;

class ResponseField extends ObjectField {

    private static final List<Field> REQUIRED_FIELDS =
            ImmutableList.of(new StringField("description"));

    public ResponseField(final String name) {
        super(name);
    }

    @Override
    public List<Field> getChildren() {
        return REQUIRED_FIELDS;
    }
}
