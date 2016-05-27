package org.zalando.intellij.swagger.completion.level.field;

public class StringField extends Field {

    public StringField(final String name) {
        super(name);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getJsonPlaceholderSuffix(final int indentation) {
        return ": \"<caret>\"";
    }

    @Override
    public String getYamlPlaceholderSuffix(final int indentation) {
        return ": <caret>";
    }
}
