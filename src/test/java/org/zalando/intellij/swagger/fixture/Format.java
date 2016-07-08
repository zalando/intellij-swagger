package org.zalando.intellij.swagger.fixture;

import org.jetbrains.annotations.NotNull;

public enum Format {

    JSON("json"), YAML("yaml");

    private final String myFileExtension;

    Format(String fileExtension) {
        myFileExtension = fileExtension;
    }

    @NotNull
    public String getFileNameWithExtension(@NotNull String base) {
        return base + "." + myFileExtension;
    }
}
