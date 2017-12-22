package org.zalando.intellij.swagger.traversal.path.swagger;

import org.zalando.intellij.swagger.file.SwaggerFileType;

public class PathResolverFactory {

    public static PathResolver fromSwaggerFileType(final SwaggerFileType swaggerFileType) {
        switch (swaggerFileType) {
            case DEFINITIONS:
                return new DefinitionPathResolver();
            case DEFINITIONS_MULTIPLE_IN_ROOT:
                return new DefinitionsInRootPathResolver();
            case DEFINITIONS_MULTIPLE_NOT_IN_ROOT:
                return new DefinitionsNotInRootPathResolver();
            case PARAMETERS:
                return new ParameterPathResolver();
            case PARAMETERS_MULTIPLE_IN_ROOT:
                return new ParameterDefinitionsInRootPathResolver();
            case PARAMETERS_MULTIPLE_NOT_IN_ROOT:
                return new ParameterDefinitionsNotInRootPathResolver();
            case RESPONSES:
                return new ResponsePathResolver();
            case RESPONSES_MULTIPLE_IN_ROOT:
                return new ResponseDefinitionsInRootPathResolver();
            case RESPONSES_MULTIPLE_NOT_IN_ROOT:
                return new ResponseDefinitionsNotInRootPathResolver();
            case PATHS:
                return new PathPathResolver();
            case PATHS_MULTIPLE_IN_ROOT:
                return new PathDefinitionsInRootPathResolver();
            case PATHS_MULTIPLE_NOT_IN_ROOT:
                return new PathDefinitionsNotInRootPathResolver();
            default:
                return new MainPathResolver();
        }
    }

}
