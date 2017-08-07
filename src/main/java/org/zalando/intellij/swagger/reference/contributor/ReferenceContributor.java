package org.zalando.intellij.swagger.reference.contributor;

import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReference;
import com.intellij.psi.PsiReferenceContributor;
import com.intellij.psi.PsiReferenceProvider;
import com.intellij.util.ProcessingContext;
import org.jetbrains.annotations.NotNull;
import org.zalando.intellij.swagger.StringUtils;
import org.zalando.intellij.swagger.reference.*;
import org.zalando.intellij.swagger.traversal.Traversal;

import java.util.Optional;

abstract class ReferenceContributor extends PsiReferenceContributor {

    final Traversal traversal;

    ReferenceContributor(Traversal traversal) {
        this.traversal = traversal;
    }

    PsiReferenceProvider createExternalDefinitionsInRootReferenceProvider() {
        return new PsiReferenceProvider() {
            @NotNull
            @Override
            public PsiReference[] getReferencesByElement(@NotNull PsiElement element,
                                                         @NotNull ProcessingContext context) {
                return Optional.ofNullable(element.getText())
                        .map(text -> new PsiReference[]{
                                new DefinitionsInRootReference(
                                        element,
                                        StringUtils.removeAllQuotes(text),
                                        traversal)
                        }).orElse(DefinitionsInRootReference.EMPTY_ARRAY);
            }
        };
    }

    PsiReferenceProvider createExternalDefinitionsNotInRootReferenceProvider() {
        return new PsiReferenceProvider() {
            @NotNull
            @Override
            public PsiReference[] getReferencesByElement(@NotNull PsiElement element,
                                                         @NotNull ProcessingContext context) {
                return Optional.ofNullable(element.getText())
                        .map(text -> new PsiReference[]{
                                new DefinitionsNotInRootReference(
                                        element,
                                        StringUtils.removeAllQuotes(text),
                                        traversal)
                        }).orElse(DefinitionsNotInRootReference.EMPTY_ARRAY);
            }
        };
    }

    PsiReferenceProvider createLocalReferenceProvider() {
        return new PsiReferenceProvider() {
            @NotNull
            @Override
            public PsiReference[] getReferencesByElement(@NotNull PsiElement element,
                                                         @NotNull ProcessingContext context) {
                return Optional.ofNullable(element.getText())
                        .map(text -> new PsiReference[]{new LocalReference(
                                element,
                                StringUtils.removeAllQuotes(text),
                                traversal)
                        }).orElse(LocalReference.EMPTY_ARRAY);
            }
        };
    }

    PsiReferenceProvider createFileReferenceProvider() {
        return new PsiReferenceProvider() {
            @NotNull
            @Override
            public PsiReference[] getReferencesByElement(@NotNull PsiElement element,
                                                         @NotNull ProcessingContext context) {
                return Optional.ofNullable(element.getText())
                        .map(text -> new PsiReference[]{
                                new FileReference(
                                        element,
                                        StringUtils.removeAllQuotes(text))
                        }).orElse(FileReference.EMPTY_ARRAY);
            }
        };
    }

    PsiReferenceProvider createTagsReferenceProvider() {
        return new PsiReferenceProvider() {
            @NotNull
            @Override
            public PsiReference[] getReferencesByElement(@NotNull PsiElement element,
                                                         @NotNull ProcessingContext context) {
                return Optional.ofNullable(element.getText())
                        .map(text -> new PsiReference[]{
                                new TagReference(
                                        element,
                                        element.getText(),
                                        traversal)})
                        .orElse(TagReference.EMPTY_ARRAY);
            }
        };
    }
}
