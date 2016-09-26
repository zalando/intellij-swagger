package org.zalando.intellij.swagger.traversal;

import org.zalando.intellij.swagger.file.SwaggerFileType;

public class PathResolverFactory {

    public static PathResolver forMainSwaggerFile() {
        return new MainPathResolver();
    }

    public static PathResolver forDefinitionsSwaggerFile() {
        return new DefinitionPathResolver();
    }

    public static PathResolver fromSwaggerFileType(final SwaggerFileType swaggerFileType) {
        switch (swaggerFileType) {
            case DEFINITIONS:
                return new DefinitionPathResolver();
            case PARAMETERS:
                return new ParameterPathResolver();
            default:
                return new MainPathResolver();
        }
    }
}
