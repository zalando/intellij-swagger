package org.zalando.intellij.swagger.completion.field.model;

import com.google.common.collect.ImmutableList;

import java.util.List;

class ItemsField extends ObjectField {

    private static final List<Field> REQUIRED_FIELDS =
            ImmutableList.of(new StringField("type"));

    public ItemsField() {
        super("items");
    }

    @Override
    public List<Field> getChildren() {
        return REQUIRED_FIELDS;
    }
}
