package org.zalando.intellij.swagger.completion.traversal;

import com.intellij.psi.PsiElement;
import org.zalando.intellij.swagger.completion.style.CompletionStyle;

interface Traversal {

    boolean isKey(final PsiElement psiElement);

    boolean isRoot(final PsiElement psiElement);

    boolean isInfo(final PsiElement psiElement);

    boolean isContact(final PsiElement psiElement);

    boolean isLicense(final PsiElement psiElement);

    boolean isPath(final PsiElement psiElement);

    boolean isOperation(final PsiElement psiElement);

    boolean isExternalDocs(final PsiElement psiElement);

    boolean isParameters(final PsiElement psiElement);

    boolean isItems(final PsiElement psiElement);

    boolean isResponses(final PsiElement psiElement);

    boolean isResponse(final PsiElement psiElement);

    boolean isHeader(final PsiElement psiElement);

    boolean isTag(final PsiElement psiElement);

    boolean isSecurityDefinition(final PsiElement psiElement);

    boolean isSchema(final PsiElement psiElement);

    boolean isXml(final PsiElement psiElement);

    boolean isDefinitions(final PsiElement psiElement);

    boolean isParameterDefinition(final PsiElement psiElement);

    boolean isMimeValue(final PsiElement psiElement);

    boolean shouldQuote(final PsiElement psiElement);

    CompletionStyle.QuoteStyle getQuoteStyle();

    boolean isSchemesValue(PsiElement psiElement);
}
