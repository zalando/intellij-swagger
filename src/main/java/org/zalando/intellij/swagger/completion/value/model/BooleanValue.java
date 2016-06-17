package org.zalando.intellij.swagger.completion.value.model;

class BooleanValue extends Value {

    BooleanValue(final String value) {
        super(value);
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean isQuotable() {
        return false;
    }
}
