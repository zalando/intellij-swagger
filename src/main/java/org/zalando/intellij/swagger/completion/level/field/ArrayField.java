package org.zalando.intellij.swagger.completion.level.field;

public class ArrayField extends Field {

    public ArrayField(final String name) {
        super(name);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getJsonPlaceholderSuffix(final int indentation) {
        final String indentationPadding = org.apache.commons.lang.StringUtils.repeat(" ", indentation);
        final String nextLineIndentationPadding = org.apache.commons.lang.StringUtils.repeat(" ", indentation + 2);
        return ": [\n" + nextLineIndentationPadding + "<caret>" + "\n" + indentationPadding + "]";
    }

    @Override
    public String getYamlPlaceholderSuffix(final int indentation) {
        final String indentationPadding = org.apache.commons.lang.StringUtils.repeat(" ", indentation);
        final String nextLineIndentationPadding = org.apache.commons.lang.StringUtils.repeat(" ", indentation + 2);

        return ":\n" + "  " + "- <caret>";
    }
}
