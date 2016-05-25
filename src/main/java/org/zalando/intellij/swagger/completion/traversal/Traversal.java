package org.zalando.intellij.swagger.completion.traversal;

import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import org.zalando.intellij.swagger.completion.style.CompletionStyle;

import java.util.List;
import java.util.Optional;

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

    boolean isSchemesValue(final PsiElement psiElement);

    boolean isRefValue(final PsiElement psiElement);

    List<PsiElement> getChildrenOf(final String propertyName, final PsiFile psiFile);

    List<String> getKeyNamesOf(final String propertyName, final PsiFile containingFile);

}
