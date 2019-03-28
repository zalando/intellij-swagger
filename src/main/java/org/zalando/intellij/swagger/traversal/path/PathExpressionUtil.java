package org.zalando.intellij.swagger.traversal.path;

public class PathExpressionUtil {

  public static String unescape(final String part) {

    // Unescape JSON pointer (https://tools.ietf.org/html/rfc6901)
    final String result = part.replace("~1", "/").replace("~0", "~");

    // Unescape dots that are used for splitting parts
    return result.replace("\\.", ".");
  }

  public static String escape(final String part) {
    return part.replace(".", "\\.");
  }
}
