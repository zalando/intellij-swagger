package org.zalando.intellij.swagger.traversal;

import org.zalando.intellij.swagger.file.SwaggerFileType;

public class PathResolverFactory {

    public static PathResolver fromSwaggerFileType(final SwaggerFileType swaggerFileType) {
        switch (swaggerFileType) {
            case DEFINITIONS:
                return new DefinitionPathResolver();
            case DEFINITIONS_MULTIPLE_IN_ROOT:
                return new DefinitionMultipleInRootPathResolver();
            case DEFINITIONS_MULTIPLE_NOT_IN_ROOT:
                return new DefinitionMultipleNotInRootPathResolver();
            case PARAMETERS:
                return new ParameterPathResolver();
            default:
                return new MainPathResolver();
        }
    }
}
