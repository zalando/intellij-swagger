package org.zalando.intellij.swagger.completion.level.field;

public abstract class Field {

    protected final String name;

    public Field() {
        this("unknown");
    }

    protected Field(final String name) {
        this.name = name;
    }

    public abstract String getName();

    public abstract String getJsonPlaceholderSuffix(final int indentation);

    public abstract String getYamlPlaceholderSuffix(final int indentation);

    public abstract String getCompleteJson(final int indentation);

    public abstract String getCompleteYaml(final int indentation);
}
