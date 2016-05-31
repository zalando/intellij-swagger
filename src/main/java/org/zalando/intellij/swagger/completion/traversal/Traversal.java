package org.zalando.intellij.swagger.completion.traversal;

import com.intellij.codeInsight.completion.CompletionData;
import com.intellij.codeInsight.completion.InsertHandler;
import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.patterns.ElementPattern;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import org.jetbrains.annotations.NotNull;
import org.zalando.intellij.swagger.completion.level.field.Field;
import org.zalando.intellij.swagger.completion.style.CompletionStyle;

import java.util.List;
import java.util.Optional;

import static com.intellij.patterns.StandardPatterns.character;

interface Traversal {

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

    boolean isUniqueKey(String keyName, final PsiElement psiElement);

    InsertHandler<LookupElement> createInsertHandler(Field field);

    /**
     * By default platform considers all the {@link Character#isJavaIdentifierPart(char)} characters at the left
     * of caret as a prefix. Sometimes this has to be overridden (see #25 for the requirement to include also
     * '/' and '#'). This method allows to override standard behavior for specific locations.
     *
     * @param psiElement        element whoch owns the caret
     * @param caretOffsetInFile file based offset of caret
     * @return custom prefix for given position of caret inside given {@link PsiElement}, or {@link Optional#empty()}
     * when standard behavior is good enough.
     */
    @NotNull
    default Optional<String> getCustomCompletionPrefix(PsiElement psiElement, int caretOffsetInFile) {
        if (!isRefValue(psiElement)) {
            // standard platform behavior is good enough
            return Optional.empty();
        }
        String result = CompletionData.findPrefixStatic(psiElement, caretOffsetInFile, NOT_REF_VALUE_CHARACTER);
        return Optional.ofNullable(result);
    }

    ElementPattern<Character> NOT_REF_VALUE_CHARACTER =
            CompletionData.NOT_JAVA_ID.andNot(character().oneOf('/', '#'));


}
