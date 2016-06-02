package org.zalando.intellij.swagger.completion.level.field;

public class RefField extends Field {

    public RefField() {
        super("$ref");
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
        return ": '<caret>'";
    }
}
