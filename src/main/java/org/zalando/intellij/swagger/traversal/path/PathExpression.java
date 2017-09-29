package org.zalando.intellij.swagger.traversal.path;

import org.apache.commons.lang.StringUtils;

public class PathExpression {

    private static final String ANY_KEY = "*";
    private static final String ANY_KEYS = "**";
    private static final String SEPARATOR = ".";

    private final String path;

    PathExpression(final String path) {
        this.path = path;
    }

    String getCurrentPath() {
        return StringUtils.substringBefore(path, SEPARATOR);
    }

    PathExpression afterFirst() {
        return new PathExpression(StringUtils.substringAfter(path, SEPARATOR));
    }

    PathExpression beforeLast() {
        return new PathExpression(StringUtils.substringBeforeLast(path, SEPARATOR));
    }

    boolean isEmpty() {
        return "".equals(path);
    }

    String last() {
        String[] paths = splitToPaths();
        return paths[paths.length - 1];
    }

    String secondLast() {
        String[] paths = splitToPaths();
        return paths[paths.length - 2];
    }

    boolean hasOnePath() {
        return splitToPaths().length == 1;
    }

    private String[] splitToPaths() {
        return StringUtils.split(path, SEPARATOR);
    }

    public boolean isRoot() {
        return "$".equals(path);
    }

    boolean isAnyKey() {
        return ANY_KEY.equals(last());
    }

    boolean isAnyKeys() {
        return ANY_KEYS.equals(last());
    }
}
