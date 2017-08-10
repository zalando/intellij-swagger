package org.zalando.intellij.swagger.completion.field.model.common;

public abstract class Field {

    private final String name;
    private final boolean required;

    Field() {
        this("unknown", false);
    }

    Field(final String name, final boolean required) {
        this.name = name;
        this.required = required;
    }

    public String getName() {
        return name;
    }

    public abstract String getJsonPlaceholderSuffix(final int indentation);

    public abstract String getYamlPlaceholderSuffix(final int indentation);

    protected abstract String getCompleteJson(final int indentation);

    protected abstract String getCompleteYaml(final int indentation);

    public boolean isRequired() {
        return required;
    }
}
