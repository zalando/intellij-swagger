package org.zalando.intellij.swagger.traversal.path.openapi;

import com.intellij.psi.PsiElement;

public class HeadersPathResolver implements PathResolver {

    @Override
    public final boolean childOfHeader(final PsiElement psiElement) {
        return hasPath(psiElement, "$.*");
    }
}
