package org.zalando.intellij.swagger.completion.field.model;

import com.google.common.collect.ImmutableList;

import java.util.List;

public class ResponseField extends ObjectField {

    private static final List<Field> REQUIRED_FIELDS =
            ImmutableList.of(new StringField("description"));

    public ResponseField(final String name) {
        super(name);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public List<Field> getChildren() {
        return REQUIRED_FIELDS;
    }
}
