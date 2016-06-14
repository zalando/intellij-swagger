package org.zalando.intellij.swagger.completion.field.model;

import com.google.common.collect.ImmutableList;

import java.util.List;

public class ExternalDocsField extends ObjectField {

    private static final List<Field> OPTIONAL_FIELDS =
            ImmutableList.of(new StringField("description"), new StringField("url"));

    public ExternalDocsField() {
        super("externalDocs");
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public List<Field> getChildren() {
        return OPTIONAL_FIELDS;
    }
}
