package org.zalando.intellij.swagger.reference;

import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiNamedElement;
import com.intellij.psi.PsiReferenceBase;
import com.intellij.util.IncorrectOperationException;
import org.apache.commons.lang.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.zalando.intellij.swagger.traversal.Traversal;

import static org.zalando.intellij.swagger.reference.SwaggerConstants.REFERENCE_PREFIX;
import static org.zalando.intellij.swagger.reference.SwaggerConstants.SLASH;

public class LocalReference extends PsiReferenceBase<PsiElement> {

    private final String referenceType;
    private final String originalRefValue;
    private final Traversal traversal;

    public LocalReference(@NotNull final String referenceType,
                          @NotNull final PsiElement element,
                          @NotNull final String originalRefValue,
                          @NotNull final Traversal traversal) {
        super(element);
        this.referenceType = referenceType;
        this.originalRefValue = originalRefValue;
        this.traversal = traversal;
    }

    @Nullable
    @Override
    public PsiElement resolve() {
        final String referencedValue = extractReferenceValue();

        return traversal
                .getChildrenOfRootProperty(referenceType, getElement().getContainingFile()).stream()
                .filter(psiElement -> psiElement instanceof PsiNamedElement)
                .map(PsiNamedElement.class::cast)
                .filter(psiNamedElement -> referencedValue.equals(psiNamedElement.getName()))
                .findFirst()
                .orElse(null);
    }

    private String extractReferenceValue() {
        return StringUtils.substringAfterLast(originalRefValue, SLASH);
    }

    @NotNull
    @Override
    public Object[] getVariants() {
        return new Object[0];
    }

    @Override
    public PsiElement handleElementRename(String newElementName) throws IncorrectOperationException {
        return super.handleElementRename(REFERENCE_PREFIX + referenceType + SLASH + newElementName);
    }

}
