package org.zalando.intellij.swagger.completion.field.model;

import static org.zalando.intellij.swagger.file.FileConstants.CARET;

public class ArrayField extends Field {

    public ArrayField(final String name) {
        super(name, false);
    }

    @Override
    public String getJsonPlaceholderSuffix(final int indentation) {
        final String indentationPadding = org.apache.commons.lang.StringUtils.repeat(" ", indentation);
        final String nextLineIndentationPadding = org.apache.commons.lang.StringUtils.repeat(" ", indentation + 2);
        return ": [\n" + nextLineIndentationPadding + CARET + "\n" + indentationPadding + "]";
    }

    @Override
    public String getYamlPlaceholderSuffix(final int indentation) {
        final String nextLineIndentationPadding = org.apache.commons.lang.StringUtils.repeat(" ", indentation + 2);
        return ":\n" + nextLineIndentationPadding + "- " + CARET;
    }

    @Override
    public String getCompleteJson(final int indentation) {
        final String indentationPadding = org.apache.commons.lang.StringUtils.repeat(" ", indentation);
        return indentationPadding + "\"" + getName() + "\"" + getJsonPlaceholderSuffix(indentation);
    }

    @Override
    public String getCompleteYaml(final int indentation) {
        final String indentationPadding = org.apache.commons.lang.StringUtils.repeat(" ", indentation);
        return indentationPadding + getName() + getYamlPlaceholderSuffix(indentation);
    }
}
