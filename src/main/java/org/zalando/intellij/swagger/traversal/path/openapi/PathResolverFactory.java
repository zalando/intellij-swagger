package org.zalando.intellij.swagger.traversal.path.openapi;


import org.zalando.intellij.swagger.file.OpenApiFileType;

public class PathResolverFactory {

    public static PathResolver fromOpenApiFileType(final OpenApiFileType openApiFileType) {
        return new MainPathResolver();
    }

}
