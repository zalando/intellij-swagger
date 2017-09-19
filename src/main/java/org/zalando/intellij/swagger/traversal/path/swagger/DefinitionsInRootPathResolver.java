package org.zalando.intellij.swagger.traversal.path.swagger;

import com.intellij.psi.PsiElement;

public class DefinitionsInRootPathResolver implements PathResolver {

    @Override
    public final boolean childOfDefinitions(final PsiElement psiElement) {
        return hasPath(psiElement, "$.*");
    }

}
