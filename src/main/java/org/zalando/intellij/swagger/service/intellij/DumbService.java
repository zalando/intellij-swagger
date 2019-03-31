package org.zalando.intellij.swagger.service.intellij;

import com.intellij.openapi.project.Project;

public class DumbService {

  public boolean isDumb(final Project project) {
    return com.intellij.openapi.project.DumbService.isDumb(project);
  }
}
