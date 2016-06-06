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

abstract class Traversal {

    abstract boolean elementIsInsideParametersArray(final PsiElement psiElement);

    abstract Optional<String> getNameOfNthKey(final PsiElement psiElement, int nthKey);

    abstract boolean isRoot(final PsiElement psiElement);

    abstract boolean shouldQuote(final PsiElement psiElement);

    abstract CompletionStyle.QuoteStyle getQuoteStyle();

    abstract boolean elementIsDirectValueOfKey(final PsiElement psiElement, final String... keyNames);

    abstract List<PsiElement> getChildrenOf(final String propertyName, final PsiFile psiFile);

    abstract List<String> getKeyNamesOf(final String propertyName, final PsiFile containingFile);

    abstract boolean isUniqueKey(String keyName, final PsiElement psiElement);

    abstract InsertHandler<LookupElement> createInsertHandler(Field field);

    abstract boolean elementIsInsideArray(final PsiElement psiElement);

    <T extends PsiElement> Optional<T> getNthOfType(final PsiElement psiElement, int nth, Class<T> targetType) {
        if (psiElement == null) {
            return Optional.empty();
        } else if (targetType.isAssignableFrom(psiElement.getClass())) {
            if (nth == 1) {
                return Optional.of(targetType.cast(psiElement));
            } else {
                nth--;
            }
        }
        return getNthOfType(psiElement.getParent(), nth, targetType);
    }


    final boolean isInfo(final PsiElement psiElement) {
        return nthKeyEquals(psiElement, getInfoNth(), "info");
    }

    abstract int getInfoNth();

    abstract int getContactNth();

    abstract int getLicenseNth();

    abstract int getPathNth();

    abstract int getOperationNth();

    abstract int getExternalDocsNth();

    abstract int getParametersNth();

    abstract int getItemsNth();

    abstract int getResponsesNth();

    abstract int getResponseNth();

    abstract int getHeadersNth();

    abstract int getTagsNth();

    abstract int getSecurityDefinitionsNth();

    abstract int getSchemaNth();

    abstract int getXmlNth();

    abstract int getDefinitionsNth();

    abstract int getParameterDefinitionNth();

    abstract int getMimeNth();

    abstract int getSchemesNth();

    final boolean isContact(final PsiElement psiElement) {
        return nthKeyEquals(psiElement, getContactNth(), "contact");
    }

    final boolean isLicense(final PsiElement psiElement) {
        return nthKeyEquals(psiElement, getLicenseNth(), "license");
    }

    boolean isPath(final PsiElement psiElement) {
        return nthKeyEquals(psiElement, getPathNth(), "paths");
    }

    boolean isOperation(final PsiElement psiElement) {
        return nthKeyEquals(psiElement, getOperationNth(), "paths");
    }

    final boolean isExternalDocs(final PsiElement psiElement) {
        return nthKeyEquals(psiElement, getExternalDocsNth(), "externalDocs");
    }

    final boolean isParameters(final PsiElement psiElement) {
        return nthKeyEquals(psiElement, getParametersNth(), "parameters") && elementIsInsideArray(psiElement);
    }

    final boolean isItems(final PsiElement psiElement) {
        return nthKeyEquals(psiElement, getItemsNth(), "items");
    }

    final boolean isResponses(final PsiElement psiElement) {
        return nthKeyEquals(psiElement, getResponsesNth(), "responses");
    }

    final boolean isResponse(final PsiElement psiElement) {
        return nthKeyEquals(psiElement, getResponseNth(), "responses");
    }

    final boolean isHeader(final PsiElement psiElement) {
        return nthKeyEquals(psiElement, getHeadersNth(), "headers");
    }

    final boolean isTag(final PsiElement psiElement) {
        return nthKeyEquals(psiElement, getTagsNth(), "tags");
    }

    final boolean isSecurityDefinition(final PsiElement psiElement) {
        return nthKeyEquals(psiElement, getSecurityDefinitionsNth(), "securityDefinitions");
    }

    final boolean isSchema(final PsiElement psiElement) {
        return nthKeyEquals(psiElement, getSchemaNth(), "schema");
    }

    final boolean isXml(final PsiElement psiElement) {
        return nthKeyEquals(psiElement, getXmlNth(), "xml");
    }

    final boolean isDefinitions(final PsiElement psiElement) {
        return nthKeyEquals(psiElement, getDefinitionsNth(), "definitions");
    }

    final boolean isParameterDefinition(final PsiElement psiElement) {
        return nthKeyEquals(psiElement, getParameterDefinitionNth(), "parameters") && !elementIsInsideParametersArray(psiElement);
    }

    final boolean isMimeValue(final PsiElement psiElement) {
        return (nthKeyEquals(psiElement, getMimeNth(), "consumes") || nthKeyEquals(psiElement, 1, "produces")) &&
                elementIsInsideArray(psiElement);
    }

    final boolean isSchemesValue(final PsiElement psiElement) {
        return nthKeyEquals(psiElement, getSchemesNth(), "schemes") && elementIsInsideArray(psiElement);
    }

    boolean isDefinitionRefValue(final PsiElement psiElement) {
        return isRefValue(psiElement) && !elementIsInsideParametersArray(psiElement);
    }

    boolean isParameterRefValue(final PsiElement psiElement) {
        return isRefValue(psiElement) && elementIsInsideParametersArray(psiElement);
    }

    private boolean isRefValue(final PsiElement psiElement) {
        return nthKeyEquals(psiElement, 1, "$ref");
    }

    boolean nthKeyEquals(final PsiElement psiElement, final int nth, final String targetName) {
        return getNameOfNthKey(psiElement, nth)
                .filter(name -> name.equals(targetName))
                .isPresent();
    }

    /**
     * By default platform considers all the {@link Character#isJavaIdentifierPart(char)} characters at the left
     * of caret as a prefix. Sometimes this has to be overridden (see #25 for the requirement to include also
     * '/' and '#'). This method allows to override standard behavior for specific locations.
     *
     * @param psiElement        element which owns the caret
     * @param caretOffsetInFile file based offset of caret
     * @return custom prefix for given position of caret inside given {@link PsiElement}, or {@link Optional#empty()}
     * when standard behavior is good enough.
     */
    @NotNull
    public Optional<String> getCustomCompletionPrefix(PsiElement psiElement, int caretOffsetInFile) {
        if (!isDefinitionRefValue(psiElement) && !isParameterRefValue(psiElement)) {
            // standard platform behavior is good enough
            return Optional.empty();
        }
        String result = CompletionData.findPrefixStatic(psiElement, caretOffsetInFile, NOT_REF_VALUE_CHARACTER);
        return Optional.ofNullable(result);
    }

    ElementPattern<Character> NOT_REF_VALUE_CHARACTER =
            CompletionData.NOT_JAVA_ID.andNot(character().oneOf('/', '#'));


    boolean isBooleanValue(final PsiElement psiElement) {
        return elementIsDirectValueOfKey(psiElement, "deprecated", "required", "allowEmptyValue",
                "exclusiveMaximum", "exclusiveMinimum", "uniqueItems", "readOnly", "attribute", "wrapped");
    }

}
