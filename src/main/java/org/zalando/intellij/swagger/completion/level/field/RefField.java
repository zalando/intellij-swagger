package org.zalando.intellij.swagger.completion.level.field;

public class RefField extends Field {

    public RefField() {
        super("$ref", false);
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

    @Override
    public String getCompleteJson(final int indentation) {
        return "";
    }

    @Override
    public String getCompleteYaml(final int indentation) {
        return "";
    }
}
