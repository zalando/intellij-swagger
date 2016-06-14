package org.zalando.intellij.swagger.completion.field.model;

import com.google.common.collect.ImmutableList;

import java.util.List;

public class HeadersField extends ObjectField {

    private static final List<Field> FIELDS =
            ImmutableList.of(new StringField("description"), new StringField("type"));

    public HeadersField(final String name) {
        super(name);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public List<Field> getChildren() {
        return FIELDS;
    }
}
