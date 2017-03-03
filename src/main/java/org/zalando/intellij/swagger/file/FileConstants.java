package org.zalando.intellij.swagger.file;

public class FileConstants {

    public static final String JSON_FILE_EXTENSION = "json";
    public static final String YML_FILE_EXTENSION = "yml";
    public static final String YAML_FILE_EXTENSION = "yaml";

    public static final String JSON_FILE_NAME_SUFFIX = "." + JSON_FILE_EXTENSION;
    public static final String YML_FILE_NAME_SUFFIX = "." + YML_FILE_EXTENSION;
    public static final String YAML_FILE_NAME_SUFFIX = "." + YAML_FILE_EXTENSION;

    private static final String MAIN_SWAGGER_FILE_NAME = "swagger";
    public static final String MAIN_SWAGGER_YAML_FILE = MAIN_SWAGGER_FILE_NAME + YAML_FILE_EXTENSION;
    public static final String MAIN_SWAGGER_YML_FILE = MAIN_SWAGGER_FILE_NAME + YML_FILE_EXTENSION;
    public static final String MAIN_SWAGGER_JSON_FILE = MAIN_SWAGGER_FILE_NAME + JSON_FILE_EXTENSION;

    public static final String CARET = "<caret>";
}
