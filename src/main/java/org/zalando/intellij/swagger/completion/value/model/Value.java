package org.zalando.intellij.swagger.completion.value.model;

public abstract class Value {

    protected final String value;

    public Value(final String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public abstract boolean isQuotable();
}
