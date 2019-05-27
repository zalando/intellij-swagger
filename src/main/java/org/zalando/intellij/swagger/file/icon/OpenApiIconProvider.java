package org.zalando.intellij.swagger.file.icon;

import javax.swing.*;
import org.zalando.intellij.swagger.index.openapi.OpenApiIndexService;

public class OpenApiIconProvider extends SpecIconProvider {

  protected OpenApiIconProvider(final OpenApiIndexService openApiIndexService) {
    super(openApiIndexService);
  }

  @Override
  protected Icon getIcon() {
    return Icons.OPEN_API_ICON;
  }
}
