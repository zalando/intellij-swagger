package org.zalando.intellij.swagger.traversal.path.openapi;

import com.intellij.psi.PsiElement;

public class SchemaPathResolver implements PathResolver {

    @Override
    public final boolean childOfSchema(final PsiElement psiElement) {
        return hasPath(psiElement, "$");
    }

}
