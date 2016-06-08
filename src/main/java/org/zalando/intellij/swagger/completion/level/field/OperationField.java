package org.zalando.intellij.swagger.completion.level.field;

import com.google.common.collect.ImmutableList;

import java.util.List;

public class OperationField extends ObjectField {

    private static final List<Field> REQUIRED_FIELDS =
            ImmutableList.of(new StringField("summary"),
                    new StringField("operationId"),
                    new ArrayField("parameters"),
                    new ObjectField("responses"));

    public OperationField(final String name) {
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
