package org.zalando.intellij.swagger.reference.swagger;

import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReferenceBase;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.zalando.intellij.swagger.traversal.Traversal;

public class TagReference extends PsiReferenceBase<PsiElement> {

    private final String tagName;
    private final Traversal traversal;

    public TagReference(@NotNull final PsiElement element,
                        @NotNull final String tagName,
                        @NotNull final Traversal traversal) {
        super(element);
        this.tagName = tagName;
        this.traversal = traversal;
    }

    @Nullable
    @Override
    public PsiElement resolve() {
        return traversal.getTags(getElement().getContainingFile()).stream()
                .filter(tag -> tagName.equals(tag.getText()))
                .findFirst()
                .orElse(null);
    }

    @NotNull
    @Override
    public Object[] getVariants() {
        return new Object[0];
    }

}
