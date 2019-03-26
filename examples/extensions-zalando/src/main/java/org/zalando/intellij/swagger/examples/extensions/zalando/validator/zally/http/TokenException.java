package org.zalando.intellij.swagger.examples.extensions.zalando.validator.zally.http;

public class TokenException extends RuntimeException {

  public TokenException(final String message) {
    super(message);
  }
}
