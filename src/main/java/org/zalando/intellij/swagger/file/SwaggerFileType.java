package org.zalando.intellij.swagger.file;

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
  PATHS_MULTIPLE_NOT_IN_ROOT
}
