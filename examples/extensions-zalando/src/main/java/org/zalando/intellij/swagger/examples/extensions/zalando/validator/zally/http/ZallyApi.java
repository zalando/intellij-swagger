package org.zalando.intellij.swagger.examples.extensions.zalando.validator.zally.http;

import feign.HeaderMap;
import feign.RequestLine;
import java.util.Map;
import org.zalando.intellij.swagger.examples.extensions.zalando.validator.zally.model.LintingRequest;
import org.zalando.intellij.swagger.examples.extensions.zalando.validator.zally.model.LintingResponse;

public interface ZallyApi {

  @RequestLine("POST /api-violations")
  LintingResponse lint(@HeaderMap Map<String, Object> headerMap, LintingRequest request);
}
