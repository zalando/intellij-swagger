package org.zalando.intellij.swagger.file;

public enum OpenApiFileType implements SpecFileType {
  UNDEFINED,
  MAIN,

  SINGLE_PARAMETER,
  MULTIPLE_PARAMETERS,

  SINGLE_RESPONSE,
  MULTIPLE_RESPONSES,

  SINGLE_EXAMPLE,
  MULTIPLE_EXAMPLES,

  SINGLE_REQUEST_BODY,
  MULTIPLE_REQUEST_BODIES,

  SINGLE_HEADER,
  MULTIPLE_HEADERS,

  SINGLE_LINK,
  MULTIPLE_LINKS,

  SINGLE_CALLBACK,
  MULTIPLE_CALLBACKS,

  SINGLE_SCHEMA,
  MULTIPLE_SCHEMAS;

  public boolean isSingleDefinition() {
    return false;
  }

  public boolean isMultipleDefinitionsInRoot() {
    return false;
  }

  public boolean isMultipleDefinitionsNotInRoot() {
    return false;
  }

  public SpecFileType fromString(final String s) {
    return null;
  }

  @Override
  public String asSpecKey() {
    return null;
  }
}
