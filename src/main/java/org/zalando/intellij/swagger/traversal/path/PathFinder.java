package org.zalando.intellij.swagger.traversal.path;

import com.intellij.json.psi.JsonStringLiteral;
import com.intellij.psi.NavigatablePsiElement;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiNamedElement;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class PathFinder {

    private static final String ANY_KEY = "*";
    private static final String ANY_KEYS = "**";

    public boolean isInsidePath(final PsiElement psiElement, final String pathExpression) {
        String parentKeyName = getNthPathItemFromEnd(1, pathExpression);

        if (parentKeyName.equals("$")) {
            return childOfRoot(psiElement);
        }

        if (parentKeyName.equals(ANY_KEY)) {
            final Optional<PsiNamedElement> immediateNamedParent = getNextNamedParent(psiElement);
            return immediateNamedParent.isPresent() &&
                    isInsidePath(immediateNamedParent.get().getParent(), removeLastPath(pathExpression));
        }

        final Optional<PsiNamedElement> immediateParentElement = getNextNamedParent(psiElement);
        final String immediateParentName = immediateParentElement
                .map(PsiNamedElement::getName)
                .orElse(null);

        if (getNthPathItemFromEnd(2, pathExpression).equals(ANY_KEYS)) {
            if (parentKeyName.equals(immediateParentName)) {
                return goUpToElementWithParentName(psiElement, getNthPathItemFromEnd(3, pathExpression))
                        .map(parent -> isInsidePath(parent, removeLastPath(removeLastPath(pathExpression))))
                        .orElse(false);
            }
        }

        return parentKeyName.equals(immediateParentName) &&
                (getPathLength(pathExpression) == 1 || immediateParentElement
                        .map(parent -> isInsidePath(parent.getParent(), removeLastPath(pathExpression)))
                        .orElse(false));
    }

    private boolean childOfRoot(final PsiElement psiElement) {
        return psiElement != null && (
                psiElement.getParent() instanceof PsiFile ||
                        psiElement.getParent().getParent() instanceof PsiFile ||
                        psiElement.getParent().getParent().getParent() instanceof PsiFile ||
                        psiElement.getParent().getParent().getParent().getParent() instanceof PsiFile);
    }

    private String getNthPathItemFromEnd(final int nth, final String pathExpression) {
        final String[] paths = pathExpression.split("\\.");
        return paths[paths.length - nth];
    }

    private String getNthPathItemFromStart(final int nth, final String pathExpression) {
        final String[] paths = pathExpression.split("\\.");
        return paths[nth];
    }

    private int getPathLength(final String pathExpression) {
        return pathExpression.split("\\.").length;
    }

    private String removeLastPath(final String path) {
        return StringUtils.substringBeforeLast(path, ".");
    }

    private String removeFirstPath(final String path) {
        return StringUtils.substringAfter(path, ".");
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

    private Optional<? extends PsiElement> getChildByName(final PsiElement psiElement, final String name) {
        List<PsiNamedElement> children = Arrays.stream(psiElement.getChildren())
                .filter(child -> child instanceof PsiNamedElement)
                .map(child -> (PsiNamedElement) child)
                .collect(Collectors.toList());

        if (children.isEmpty()) {
            Optional<PsiElement> navigatablePsiElement = Arrays.stream(psiElement.getChildren())
                    .filter(child -> child instanceof NavigatablePsiElement)
                    .filter(child -> !(child instanceof JsonStringLiteral))
                    .findFirst();

            return navigatablePsiElement.isPresent() ? getChildByName(navigatablePsiElement.get(), name) : Optional.empty();
        }

        return children.stream()
                .filter(child -> name.equals(child.getName()))
                .findFirst();
    }

    private List<? extends PsiNamedElement> getNamedChildren(final PsiElement psiElement) {
        List<PsiNamedElement> children = Arrays.stream(psiElement.getChildren())
                .filter(child -> child instanceof PsiNamedElement)
                .map(child -> (PsiNamedElement) child)
                .collect(Collectors.toList());

        if (children.isEmpty()) {
            Optional<PsiElement> navigatablePsiElement = Arrays.stream(psiElement.getChildren())
                    .filter(child -> child instanceof NavigatablePsiElement)
                    .filter(child -> !(child instanceof JsonStringLiteral))
                    .findFirst();

            return navigatablePsiElement.isPresent() ? getNamedChildren(navigatablePsiElement.get()) : new ArrayList<>();
        }

        return new ArrayList<>(children);
    }

    public Optional<PsiElement> findByPathFrom(String pathExpression, final PsiElement psiElement) {
        if ("".equals(pathExpression)) {
            return Optional.of(psiElement);
        }

        String currentNodeName = getNthPathItemFromStart(0, pathExpression);

        if ("$".equals(currentNodeName)) {
            currentNodeName = getNthPathItemFromStart(1, pathExpression);
            pathExpression = removeFirstPath(pathExpression);
        }

        Optional<? extends PsiElement> childByName = getChildByName(psiElement, currentNodeName);

        if (!childByName.isPresent()) {
            return Optional.empty();
        }

        return findByPathFrom(removeFirstPath(pathExpression), childByName.get());
    }

    public List<? extends PsiNamedElement> findChildrenByPathFrom(String pathExpression, final PsiElement psiElement) {
        if ("".equals(pathExpression)) {
            return getNamedChildren(psiElement);
        }

        String currentNodeName = getNthPathItemFromStart(0, pathExpression);

        if ("$".equals(currentNodeName)) {
            currentNodeName = getNthPathItemFromStart(1, pathExpression);
            pathExpression = removeFirstPath(pathExpression);
        }

        Optional<? extends PsiElement> childByName = getChildByName(psiElement, currentNodeName);

        if (!childByName.isPresent()) {
            return new ArrayList<>();
        }

        return findChildrenByPathFrom(removeFirstPath(pathExpression), childByName.get());
    }
}
