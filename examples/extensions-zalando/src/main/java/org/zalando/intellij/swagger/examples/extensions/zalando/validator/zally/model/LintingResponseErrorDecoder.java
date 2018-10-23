package org.zalando.intellij.swagger.examples.extensions.zalando.validator.zally.model;

import feign.Response;
import feign.codec.ErrorDecoder;

public class LintingResponseErrorDecoder implements ErrorDecoder {

  @Override
  public Exception decode(String methodKey, Response response) {
    return new ZallyClientError(response.reason());
  }
}
