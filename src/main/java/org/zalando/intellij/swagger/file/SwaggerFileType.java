package org.zalando.intellij.swagger.file;

import java.util.EnumSet;

public enum SwaggerFileType implements SpecFileType {
  UNDEFINED,
  MAIN,
  DEFINITIONS,
  DEFINITIONS_MULTIPLE_IN_ROOT,
  DEFINITIONS_MULTIPLE_NOT_IN_ROOT,
  PARAMETERS,
  PARAMETERS_MULTIPLE_IN_ROOT,
  PARAMETERS_MULTIPLE_NOT_IN_ROOT,
  RESPONSES,
  RESPONSES_MULTIPLE_IN_ROOT,
  RESPONSES_MULTIPLE_NOT_IN_ROOT,
  PATHS,
  PATHS_MULTIPLE_IN_ROOT,
  PATHS_MULTIPLE_NOT_IN_ROOT;

  public static EnumSet<SwaggerFileType> singleDefinition =
      EnumSet.of(DEFINITIONS, PARAMETERS, RESPONSES, PATHS);

  public static EnumSet<SwaggerFileType> multipleDefinitionsInRoot =
      EnumSet.of(
          DEFINITIONS_MULTIPLE_IN_ROOT,
          PARAMETERS_MULTIPLE_IN_ROOT,
          RESPONSES_MULTIPLE_IN_ROOT,
          PATHS_MULTIPLE_IN_ROOT);

  public static EnumSet<SwaggerFileType> multipleDefinitionsNotInRoot =
      EnumSet.of(
          DEFINITIONS_MULTIPLE_NOT_IN_ROOT,
          PARAMETERS_MULTIPLE_NOT_IN_ROOT,
          RESPONSES_MULTIPLE_NOT_IN_ROOT,
          PATHS_MULTIPLE_NOT_IN_ROOT);

  public boolean isSingleDefinition() {
    return singleDefinition.contains(this);
  }

  public boolean isMultipleDefinitionsInRoot() {
    return multipleDefinitionsInRoot.contains(this);
  }

  public boolean isMultipleDefinitionsNotInRoot() {
    return multipleDefinitionsNotInRoot.contains(this);
  }

  public SwaggerFileType fromString(final String s) {
    return valueOf(s);
  }

  @Override
  public String asSpecKey() {
    return this.name().toLowerCase();
  }
}
