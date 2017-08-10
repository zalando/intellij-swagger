package org.zalando.intellij.swagger.completion.field.model.common;

import com.google.common.collect.ImmutableList;
import org.apache.commons.lang.StringUtils;

import java.util.List;

import static org.zalando.intellij.swagger.file.FileConstants.CARET;

public class ObjectField extends Field {

    public ObjectField(final String name) {
        super(name, false);
    }

    public ObjectField(final String name, final boolean required) {
        super(name, required);
    }

    @Override
    public String getJsonPlaceholderSuffix(final int indentation) {
        final StringBuilder sb = new StringBuilder();
        final String indentationPadding = StringUtils.repeat(" ", indentation);

        sb.append(": {\n")
                .append(printJsonChildren(indentation + 2))
                .append("\n")
                .append(indentationPadding)
                .append("}");

        return sb.toString();
    }

    @Override
    public String getYamlPlaceholderSuffix(final int indentation) {
        return ":\n" + printYamlChildren(indentation + 2);
    }

    @Override
    public String getCompleteJson(final int indentation) {
        final String indentationPadding = StringUtils.repeat(" ", indentation);
        return indentationPadding + "\"" + getName() + "\"" + getJsonPlaceholderSuffix(indentation);
    }

    @Override
    public String getCompleteYaml(final int indentation) {
        final String indentationPadding = StringUtils.repeat(" ", indentation);
        return indentationPadding + getName() + getYamlPlaceholderSuffix(indentation);
    }

    private String printJsonChildren(final int indentation) {
        if (getChildren().isEmpty()) {
            return StringUtils.repeat(" ", indentation) + CARET;
        }

        final StringBuilder sb = new StringBuilder();

        for (final Field field : getChildren()) {
            sb.append(field.getCompleteJson(indentation)).append(",\n");
        }

        if (sb.length() > 1) {
            sb.deleteCharAt(sb.length() - 1);
            sb.deleteCharAt(sb.length() - 1);
        }

        return sb.toString();
    }

    private String printYamlChildren(final int indentation) {
        if (getChildren().isEmpty()) {
            return StringUtils.repeat(" ", indentation) + CARET;
        }

        final StringBuilder sb = new StringBuilder();

        for (final Field field : getChildren()) {
            sb.append(field.getCompleteYaml(indentation)).append("\n");
        }

        if (sb.length() > 0) {
            sb.deleteCharAt(sb.length() - 1);
        }

        return sb.toString();
    }

    public List<Field> getChildren() {
        return ImmutableList.of();
    }

}
