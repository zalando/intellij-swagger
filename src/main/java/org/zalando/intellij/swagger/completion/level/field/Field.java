package org.zalando.intellij.swagger.completion.level.field;

public abstract class Field {

    protected final String name;

    protected Field(final String name) {
        this.name = name;
    }

    public abstract String getName();

    public abstract String getJsonPlaceholderSuffix(final int indentation);

    public abstract String getYamlPlaceholderSuffix(final int indentation);
}
