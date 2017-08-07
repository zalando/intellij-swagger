package org.zalando.intellij.swagger.traversal.path;

import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiNamedElement;
import org.apache.commons.lang.StringUtils;

import java.util.Optional;

public class Path {

    private static final String ANY_KEY = "*";
    private static final String ANY_KEYS = "**";

    private final PsiElement psiElement;
    private final String pathExpression;

    public Path(final PsiElement psiElement, final String pathExpression) {
        this.psiElement = psiElement;
        this.pathExpression = pathExpression;
    }

    public boolean exists() {
        String parentKeyName = getNthPathItem(1);

        if (parentKeyName.equals("$")) {
            return childOfRoot(psiElement);
        }

        if (parentKeyName.equals(ANY_KEY)) {
            final Optional<PsiNamedElement> immediateNamedParent = getNextNamedParent(psiElement);
            return immediateNamedParent.isPresent() &&
                    new Path(immediateNamedParent.get().getParent(), reducePath(pathExpression)).exists();
        }

        final Optional<PsiNamedElement> immediateParentElement = getNextNamedParent(psiElement);
        final String immediateParentName = immediateParentElement
                .map(PsiNamedElement::getName)
                .orElse(null);

        if (getNthPathItem(2).equals(ANY_KEYS)) {
            if (parentKeyName.equals(immediateParentName)) {
                return goUpToElementWithParentName(psiElement, getNthPathItem(3))
                        .map(parent -> new Path(parent, reducePath(reducePath(pathExpression))).exists())
                        .orElse(false);
            }
        }

        return parentKeyName.equals(immediateParentName) &&
                (getPathLength() == 1 || immediateParentElement
                        .map(parent -> new Path(parent.getParent(), reducePath(pathExpression)).exists())
                        .orElse(false));
    }

    private boolean childOfRoot(final PsiElement psiElement) {
        return psiElement != null && (
                psiElement.getParent() instanceof PsiFile ||
                        psiElement.getParent().getParent() instanceof PsiFile ||
                        psiElement.getParent().getParent().getParent() instanceof PsiFile ||
                        psiElement.getParent().getParent().getParent().getParent() instanceof PsiFile);
    }

    private String getNthPathItem(final int nth) {
        final String[] paths = pathExpression.split("\\.");
        return paths[paths.length - nth];
    }

    private int getPathLength() {
        return pathExpression.split("\\.").length;
    }

    private String reducePath(final String path) {
        return StringUtils.substringBeforeLast(path, ".");
    }

    private Optional<PsiElement> goUpToElementWithParentName(final PsiElement psiElement, final String keyName) {
        if (psiElement == null) {
            return Optional.empty();
        }

        if (psiElement instanceof PsiNamedElement) {
            final PsiNamedElement psiNamedElement = (PsiNamedElement) psiElement;

            if (keyName.equals(psiNamedElement.getName())) {
                return Optional.of(psiElement);
            } else if (keyName.equals("$")) {
                return childOfRoot(psiElement) ?
                        Optional.of(psiElement) :
                        goUpToElementWithParentName(psiElement.getParent(), keyName);
            }
        }

        return goUpToElementWithParentName(psiElement.getParent(), keyName);
    }

    private Optional<PsiNamedElement> getNextNamedParent(final PsiElement psiElement) {
        if (psiElement == null) {
            return Optional.empty();
        }

        if (psiElement instanceof PsiNamedElement) {
            final PsiNamedElement namedElement = (PsiNamedElement) psiElement;
            if (namedElement.getName() != null &&
                    !namedElement.getName().contains("IntellijIdeaRulezzz")) {
                return Optional.of(namedElement);
            }
        }

        return getNextNamedParent(psiElement.getParent());
    }
}
