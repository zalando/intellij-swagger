package org.zalando.intellij.swagger.traversal.path.openapi;

import com.intellij.psi.PsiElement;

public class ExamplePathResolver implements PathResolver {

    @Override
    public final boolean childOfExample(final PsiElement psiElement) {
        return hasPath(psiElement, "$");
    }
}
