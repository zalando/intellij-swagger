package org.zalando.intellij.swagger.reference.contributor.openapi;

import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReference;
import com.intellij.psi.PsiReferenceContributor;
import com.intellij.psi.PsiReferenceProvider;
import com.intellij.util.ProcessingContext;
import org.jetbrains.annotations.NotNull;
import org.zalando.intellij.swagger.StringUtils;
import org.zalando.intellij.swagger.reference.openapi.LocalReference;

import java.util.Optional;

abstract class ReferenceContributor extends PsiReferenceContributor {

    PsiReferenceProvider createLocalReferenceProvider() {
        return new PsiReferenceProvider() {
            @NotNull
            @Override
            public PsiReference[] getReferencesByElement(@NotNull PsiElement element,
                                                         @NotNull ProcessingContext context) {
                return Optional.ofNullable(element.getText())
                        .map(text -> new PsiReference[]{new LocalReference(element, StringUtils.removeAllQuotes(text))
                        }).orElse(LocalReference.EMPTY_ARRAY);
            }
        };
    }

}
