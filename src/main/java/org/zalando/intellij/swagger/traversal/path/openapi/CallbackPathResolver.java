package org.zalando.intellij.swagger.traversal.path.openapi;

import com.intellij.psi.PsiElement;

public class CallbackPathResolver implements PathResolver {

    @Override
    public final boolean childOfCallback(final PsiElement psiElement) {
        return hasPath(psiElement, "$");
    }

}
