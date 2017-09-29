package org.zalando.intellij.swagger.traversal.path;

import com.intellij.json.psi.JsonObject;
import com.intellij.json.psi.JsonStringLiteral;
import com.intellij.psi.NavigatablePsiElement;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiNamedElement;
import org.jetbrains.yaml.psi.YAMLKeyValue;
import org.jetbrains.yaml.psi.YAMLMapping;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class PathFinder {

    private static final String DUMMY_IDENTIFIER = "IntellijIdeaRulezzz";
    private static final String ROOT_PATH = "$";
    private static final PathExpression ROOT_PATH_EXPRESSION = new PathExpression(ROOT_PATH);

    public List<? extends PsiNamedElement> findChildrenByPathFrom(final String path, final PsiElement psiElement) {
        return findChildrenByPathFrom(new PathExpression(path), psiElement);
    }

    public Optional<PsiElement> findByPathFrom(final String path, final PsiElement psiElement) {
        return findByPathFrom(new PathExpression(path), psiElement);
    }

    public boolean isInsidePath(final PsiElement psiElement, final String path) {
        return isInsidePath(psiElement, new PathExpression(path));
    }

    private boolean isInsidePath(final PsiElement psiElement, PathExpression pathExpression) {
        if (psiElement == null) {
            return false;
        }

        final PsiNamedElement nextNamedParent = getNextNamedParent(psiElement);
        final String targetKeyName = pathExpression.last();

        if (pathExpression.isAnyKey()) {
            return isInsidePath(getNextNamedParent(nextNamedParent.getParent()), pathExpression.beforeLast());
        }

        if (pathExpression.isAnyKeys()) {
            return isInsidePath(goUpToElementWithParentName(psiElement, pathExpression.secondLast()),
                    pathExpression.beforeLast().beforeLast());
        }

        if (targetKeyName.equals(ROOT_PATH)) {
            return nextNamedParent instanceof PsiFile;
        }

        return targetKeyName.equals(nextNamedParent.getName()) &&
                (pathExpression.hasOnePath() ||
                        isInsidePath(nextNamedParent.getParent(), pathExpression.beforeLast()));
    }

    private boolean isRoot(final PsiElement psiElement) {
        return psiElement != null && psiElement instanceof PsiFile;
    }

    private PsiNamedElement goUpToElementWithParentName(final PsiElement psiElement, final String keyName) {
        if (psiElement == null) {
            return null;
        }

        if (psiElement instanceof PsiNamedElement) {
            final PsiNamedElement psiNamedElement = (PsiNamedElement) psiElement;

            if (keyName.equals(psiNamedElement.getName())) {
                return (PsiNamedElement) psiElement;
            } else if (keyName.equals(ROOT_PATH)) {
                return isRoot(psiElement) ?
                        (PsiNamedElement) psiElement :
                        goUpToElementWithParentName(psiElement.getParent(), keyName);
            }
        }

        return goUpToElementWithParentName(psiElement.getParent(), keyName);
    }

    private PsiNamedElement getNextNamedParent(final PsiElement psiElement) {
        if (psiElement == null) {
            return null;
        }

        if (psiElement instanceof PsiNamedElement) {
            final PsiNamedElement namedElement = (PsiNamedElement) psiElement;

            if (namedElement.getName() != null && !namedElement.getName().contains(DUMMY_IDENTIFIER)) {
                return namedElement;
            }
        }

        return getNextNamedParent(psiElement.getParent());
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

    private Optional<PsiElement> findByPathFrom(final PathExpression pathExpression, final PsiElement psiElement) {
        if (pathExpression.isEmpty()) {
            return Optional.of(psiElement);
        }

        final String currentNodeName = pathExpression.getCurrentPath();
        final PathExpression remainingPathExpression = pathExpression.afterFirst();

        Optional<? extends PsiElement> childByName = getChildByName(psiElement, currentNodeName);

        if (!childByName.isPresent()) {
            return Optional.empty();
        }

        return findByPathFrom(remainingPathExpression, childByName.get());
    }

    private List<? extends PsiNamedElement> findChildrenByPathFrom(final PathExpression pathExpression, final PsiElement psiElement) {
        if (psiElement == null) {
            return new ArrayList<>();
        }

        if (pathExpression.isEmpty()) {
            return getNamedChildren(psiElement);
        }

        final String currentNodeName = pathExpression.getCurrentPath();
        final PathExpression remainingPathExpression = pathExpression.afterFirst();

        if ("parent".equals(currentNodeName)) {
            return findChildrenByPathFrom(ROOT_PATH_EXPRESSION, getNextObjectParent(psiElement));
        }

        Optional<? extends PsiElement> childByName = getChildByName(psiElement, currentNodeName);

        if (!childByName.isPresent()) {
            return new ArrayList<>();
        }

        return findChildrenByPathFrom(remainingPathExpression, childByName.get());
    }

    private Optional<? extends PsiElement> getChildByName(final PsiElement psiElement, final String name) {
        if (ROOT_PATH.equals(name)) {
            return Optional.of(psiElement);
        }

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

    private PsiElement getNextObjectParent(final PsiElement psiElement) {
        if (psiElement == null) {
            return null;
        }

        if (psiElement instanceof JsonObject ||
                (psiElement instanceof YAMLKeyValue || psiElement instanceof YAMLMapping) &&
                        !(psiElement instanceof JsonStringLiteral)) {
            return psiElement;
        }

        return getNextObjectParent(psiElement.getParent());
    }
}
