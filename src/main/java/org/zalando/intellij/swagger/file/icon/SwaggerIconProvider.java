package org.zalando.intellij.swagger.file.icon;

import javax.swing.*;
import org.zalando.intellij.swagger.index.swagger.SwaggerIndexService;

public class SwaggerIconProvider extends SpecIconProvider {

  protected SwaggerIconProvider(final SwaggerIndexService swaggerIndexService) {
    super(swaggerIndexService);
  }

  @Override
  protected Icon getIcon() {
    return Icons.SWAGGER_API_ICON;
  }
}
