package org.zalando.intellij.swagger.completion.field.model;

import com.google.common.collect.ImmutableList;

import java.util.List;

class XmlField extends ObjectField {

    private static final List<Field> OPTIONAL_FIELDS =
            ImmutableList.of(new StringField("name"));

    public XmlField() {
        super("xml");
    }

    @Override
    public List<Field> getChildren() {
        return OPTIONAL_FIELDS;
    }
}
