package org.zalando.intellij.swagger.completion.field.model;

import com.google.common.collect.ImmutableList;

import java.util.List;

class ContactField extends ObjectField {

    private static final List<Field> OPTIONAL_FIELDS =
            ImmutableList.of(new StringField("name"), new StringField("url"), new StringField("email"));

    ContactField() {
        super("contact");
    }

    @Override
    public List<Field> getChildren() {
        return OPTIONAL_FIELDS;
    }

}
