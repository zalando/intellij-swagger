package org.zalando.intellij.swagger.completion.field.model.common;

import com.google.common.collect.ImmutableList;

import java.util.List;

public class ExternalDocsField extends ObjectField {

    private static final List<Field> FIELDS =
            ImmutableList.of(new StringField("description"), new StringField("url"));

    public ExternalDocsField() {
        super("externalDocs");
    }

    @Override
    public List<Field> getChildren() {
        return FIELDS;
    }
}
