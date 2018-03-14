package org.zalando.intellij.swagger.traversal.path;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.stream.Collectors;

public class PathExpression {

    private static final String ANY_KEY = "*";
    private static final String ANY_KEYS = "**";
    private static final String SEPARATOR = ".";

    private final String path;

    PathExpression(final String path) {
        this.path = path;
    }

    String getCurrentPath() {
        return splitPath()[0];
    }

    @NotNull
    private String[] splitPath() {
        return path.split("(?<!\\\\)\\.");
    }

    PathExpression afterFirst() {
        final String[] parts = splitPath();

        final String afterFirst = Arrays.stream(parts)
                .skip(1)
                .collect(Collectors.joining(SEPARATOR));

        return new PathExpression(afterFirst);
    }

    PathExpression beforeLast() {
        final String[] parts = splitPath();

        final String beforeLast = Arrays.stream(parts)
                .limit(parts.length == 1 ? 1 : parts.length - 1)
                .collect(Collectors.joining(SEPARATOR));

        return new PathExpression(beforeLast);
    }

    boolean isEmpty() {
        return path.isEmpty();
    }

    String last() {
        String[] paths = splitPath();
        return paths[paths.length - 1];
    }

    String secondLast() {
        String[] paths = splitPath();
        return paths[paths.length - 2];
    }

    boolean hasOnePath() {
        return splitPath().length == 1;
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

    String getPath() {
        return path;
    }
}
