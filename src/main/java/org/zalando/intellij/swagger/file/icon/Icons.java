package org.zalando.intellij.swagger.file.icon;

import com.intellij.openapi.util.IconLoader;
import com.intellij.util.ReflectionUtil;
import java.util.Objects;
import javax.swing.Icon;

public class Icons {
  public static final Icon OPEN_API_ICON =
      IconLoader.getIcon(
          "/icons/openapi.png", Objects.requireNonNull(ReflectionUtil.getGrandCallerClass()));
  public static final Icon SWAGGER_API_ICON =
      IconLoader.getIcon(
          "/icons/swagger.png", Objects.requireNonNull(ReflectionUtil.getGrandCallerClass()));
}
