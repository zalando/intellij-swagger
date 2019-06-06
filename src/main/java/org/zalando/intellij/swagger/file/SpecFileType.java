package org.zalando.intellij.swagger.file;

public interface SpecFileType {

  boolean isSingleDefinition();

  boolean isMultipleDefinitionsInRoot();

  boolean isMultipleDefinitionsNotInRoot();

  SpecFileType fromString(String s);

  String asSpecKey();
}
