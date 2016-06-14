package org.zalando.intellij.swagger.completion.field.model;

public abstract class Field {

    protected final String name;
    protected final boolean required;

    public Field() {
        this("unknown", false);
    }

    protected Field(final String name, final boolean required) {
        this.name = name;
        this.required = required;
    }

    public abstract String getName();

    public abstract String getJsonPlaceholderSuffix(final int indentation);

    public abstract String getYamlPlaceholderSuffix(final int indentation);

    public abstract String getCompleteJson(final int indentation);

    public abstract String getCompleteYaml(final int indentation);

    public boolean isRequired() {
        return required;
    }
}
