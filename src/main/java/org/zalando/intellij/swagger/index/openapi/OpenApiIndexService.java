package org.zalando.intellij.swagger.index.openapi;

import com.intellij.util.indexing.ID;
import java.util.Set;
import org.zalando.intellij.swagger.file.OpenApiFileType;
import org.zalando.intellij.swagger.index.IndexService;

public class OpenApiIndexService extends IndexService<OpenApiFileType> {

  @Override
  public ID<String, Set<String>> getIndexId() {
    return OpenApiFileIndex.OPEN_API_INDEX_ID;
  }

  @Override
  protected OpenApiFileType toSpecType(final String raw) {
    return OpenApiFileType.valueOf(raw);
  }

  @Override
  protected OpenApiFileType getMainSpecType() {
    return OpenApiFileType.MAIN;
  }

  @Override
  protected OpenApiFileType getUndefinedSpecType() {
    return OpenApiFileType.UNDEFINED;
  }
}
