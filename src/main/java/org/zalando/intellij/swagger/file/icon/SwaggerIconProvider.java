package org.zalando.intellij.swagger.file.icon;

import javax.swing.Icon;

import com.intellij.openapi.components.ServiceManager;
import org.zalando.intellij.swagger.index.IndexService;
import org.zalando.intellij.swagger.index.swagger.SwaggerIndexService;

public class SwaggerIconProvider extends SpecIconProvider {

  @Override
  protected Icon getIcon() {
    return Icons.SWAGGER_API_ICON;
  }

  @Override
  protected IndexService getIndexService() {
    return ServiceManager.getService(SwaggerIndexService.class);
  }
}
