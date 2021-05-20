package org.zalando.intellij.swagger.file.icon;

import com.intellij.openapi.components.ServiceManager;
import javax.swing.Icon;
import org.zalando.intellij.swagger.index.IndexService;
import org.zalando.intellij.swagger.index.openapi.OpenApiIndexService;

public class OpenApiIconProvider extends SpecIconProvider {

  @Override
  protected Icon getIcon() {
    return Icons.OPEN_API_ICON;
  }

  @Override
  protected IndexService getIndexService() {
    return ServiceManager.getService(OpenApiIndexService.class);
  }
}
