package org.zalando.intellij.swagger.completion.field.model;

import com.google.common.collect.ImmutableList;

import java.util.List;

class InfoField extends ObjectField {

    private static final List<Field> REQUIRED_FIELDS =
            ImmutableList.of(new StringField("version"), new StringField("title"), new LicenseField());

    public InfoField() {
        super("info", true);
    }

    @Override
    public List<Field> getChildren() {
        return REQUIRED_FIELDS;
    }
}
