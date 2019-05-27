package org.zalando.intellij.swagger.index.swagger;

import com.intellij.util.indexing.ID;
import java.util.Set;
import org.zalando.intellij.swagger.file.SwaggerFileType;
import org.zalando.intellij.swagger.index.IndexService;

public class SwaggerIndexService extends IndexService<SwaggerFileType> {

  @Override
  public ID<String, Set<String>> getIndexId() {
    return SwaggerFileIndex.SWAGGER_INDEX_ID;
  }

  @Override
  protected SwaggerFileType toSpecType(final String raw) {
    return SwaggerFileType.valueOf(raw);
  }

  @Override
  protected SwaggerFileType getMainSpecType() {
    return SwaggerFileType.MAIN;
  }

  @Override
  protected SwaggerFileType getUndefinedSpecType() {
    return SwaggerFileType.UNDEFINED;
  }
}
