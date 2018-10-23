package org.zalando.intellij.swagger.traversal.path.openapi;

import org.zalando.intellij.swagger.file.OpenApiFileType;

public class PathResolverFactory {

  public static PathResolver fromOpenApiFileType(final OpenApiFileType openApiFileType) {
    switch (openApiFileType) {
      case SINGLE_SCHEMA:
        return new SchemaPathResolver();
      case MULTIPLE_SCHEMAS:
        return new SchemasPathResolver();
      case SINGLE_RESPONSE:
        return new ResponsePathResolver();
      case MULTIPLE_RESPONSES:
        return new ResponsesPathResolver();
      case SINGLE_PARAMETER:
        return new ParameterPathResolver();
      case MULTIPLE_PARAMETERS:
        return new ParametersPathResolver();
      case SINGLE_EXAMPLE:
        return new ExamplePathResolver();
      case MULTIPLE_EXAMPLES:
        return new ExamplesPathResolver();
      case SINGLE_REQUEST_BODY:
        return new RequestBodyPathResolver();
      case MULTIPLE_REQUEST_BODIES:
        return new RequestBodiesPathResolver();
      case SINGLE_HEADER:
        return new HeaderPathResolver();
      case MULTIPLE_HEADERS:
        return new HeadersPathResolver();
      case SINGLE_LINK:
        return new LinkPathResolver();
      case MULTIPLE_LINKS:
        return new LinksPathResolver();
      case SINGLE_CALLBACK:
        return new CallbackPathResolver();
      case MULTIPLE_CALLBACKS:
        return new CallbacksPathResolver();
      default:
        return new MainPathResolver();
    }
  }
}
