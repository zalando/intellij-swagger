package org.zalando.intellij.swagger.completion.field.model;

public class StringField extends Field {

    public StringField(final String name, final boolean required) {
        super(name, required);
    }

    public StringField(final String name) {
        super(name, false);
    }

    @Override
    public String getJsonPlaceholderSuffix(final int indentation) {
        return ": \"<caret>\"";
    }

    @Override
    public String getYamlPlaceholderSuffix(final int indentation) {
        return ": <caret>";
    }

    @Override
    public String getCompleteJson(final int indentation) {
        final String leftPadding = org.apache.commons.lang.StringUtils.repeat(" ", indentation);
        return leftPadding + "\"" + getName() + "\": \"<caret>\"";
    }

    @Override
    public String getCompleteYaml(final int indentation) {
        final String leftPadding = org.apache.commons.lang.StringUtils.repeat(" ", indentation);
        return leftPadding + getName() + ": <caret>";
    }
}
