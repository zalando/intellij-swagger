package org.zalando.intellij.swagger.file;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import org.jetbrains.annotations.NotNull;

public class FileContentManipulator {

  private static final String SPEC_START_TOKEN = "/*intellij-swagger-spec-start*/";
  private static final String SPEC_END_TOKEN = "/*intellij-swagger-spec-end*/";

  void setJsonToIndexFile(final String specJson, final File indexFile) {
    try {
      final String originalContent = Files.readString(indexFile.toPath(), StandardCharsets.UTF_8);
      final String newContent =
          insertContentBetween(specJson, originalContent, SPEC_START_TOKEN, SPEC_END_TOKEN);

      Files.writeString(indexFile.toPath(), newContent, StandardCharsets.UTF_8);
    } catch (final IOException e) {
      throw new RuntimeException(e);
    }
  }

  @NotNull
  private String insertContentBetween(
      final String contentToBeInserted,
      final String originalContent,
      final String startToken,
      final String endToken) {
    final int startIndex = originalContent.indexOf(startToken) + startToken.length();
    final int endIndex = originalContent.indexOf(endToken);

    final String before = originalContent.substring(0, startIndex);
    final String end = originalContent.substring(endIndex);

    return before + contentToBeInserted + end;
  }
}
