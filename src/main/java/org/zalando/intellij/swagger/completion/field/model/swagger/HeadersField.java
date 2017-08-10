package org.zalando.intellij.swagger.completion.field.model.swagger;

import com.google.common.collect.ImmutableList;
import org.zalando.intellij.swagger.completion.field.model.common.Field;
import org.zalando.intellij.swagger.completion.field.model.common.ObjectField;
import org.zalando.intellij.swagger.completion.field.model.common.StringField;

import java.util.List;

class HeadersField extends ObjectField {

    private static final List<Field> FIELDS =
            ImmutableList.of(new StringField("description"), new StringField("type"));

    public HeadersField(final String name) {
        super(name);
    }

    @Override
    public List<Field> getChildren() {
        return FIELDS;
    }
}
