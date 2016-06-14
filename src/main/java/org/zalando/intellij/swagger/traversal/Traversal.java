package org.zalando.intellij.swagger.traversal;

import static com.intellij.patterns.StandardPatterns.character;
import com.intellij.codeInsight.completion.CompletionData;
import com.intellij.codeInsight.completion.InsertHandler;
import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.patterns.ElementPattern;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import org.jetbrains.annotations.NotNull;
import org.zalando.intellij.swagger.completion.field.model.Field;
import org.zalando.intellij.swagger.completion.style.CompletionStyle;
import org.zalando.intellij.swagger.completion.value.model.Value;
import org.zalando.intellij.swagger.traversal.keydepth.KeyDepth;

import java.util.List;
import java.util.Optional;

public abstract class Traversal {

    private final KeyDepth keyDepth;

    public Traversal(final KeyDepth keyDepth) {
        this.keyDepth = keyDepth;
    }

    public abstract boolean isKey(final PsiElement psiElement);

    public abstract Optional<String> getKeyName(final PsiElement psiElement);

    abstract boolean elementIsInsideParametersArray(final PsiElement psiElement);

    abstract Optional<String> getNameOfNthKey(final PsiElement psiElement, int nthKey);

    public abstract boolean isRoot(final PsiElement psiElement);

    abstract boolean shouldQuote(final PsiElement psiElement);

    abstract CompletionStyle.QuoteStyle getQuoteStyle();

    abstract boolean elementIsDirectValueOfKey(final PsiElement psiElement, final String... keyNames);

    abstract List<PsiElement> getChildrenOf(final String propertyName, final PsiFile psiFile);

    public abstract List<String> getKeyNamesOf(final String propertyName, final PsiFile containingFile);

    abstract boolean isUniqueKey(String keyName, final PsiElement psiElement);

    abstract InsertHandler<LookupElement> createInsertFieldHandler(Field field);
    abstract InsertHandler<LookupElement> createInsertValueHandler(final Value value);

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


    public final boolean isInfo(final PsiElement psiElement) {
        return nthKeyEquals(psiElement, keyDepth.getInfoNth(), "info");
    }

    public final boolean isContact(final PsiElement psiElement) {
        return nthKeyEquals(psiElement, keyDepth.getContactNth(), "contact");
    }

    public final boolean isLicense(final PsiElement psiElement) {
        return nthKeyEquals(psiElement, keyDepth.getLicenseNth(), "license");
    }

    public boolean isPath(final PsiElement psiElement) {
        return nthKeyEquals(psiElement, keyDepth.getPathNth(), "paths");
    }

    public boolean isOperation(final PsiElement psiElement) {
        return nthKeyEquals(psiElement, keyDepth.getOperationNth(), "paths") && !elementIsInsideArray(psiElement);
    }

    public final boolean isExternalDocs(final PsiElement psiElement) {
        return nthKeyEquals(psiElement, keyDepth.getExternalDocsNth(), "externalDocs");
    }

    public final boolean isParameters(final PsiElement psiElement) {
        return nthKeyEquals(psiElement, keyDepth.getParametersNth(), "parameters") && elementIsInsideArray(psiElement);
    }

    public final boolean isItems(final PsiElement psiElement) {
        return nthKeyEquals(psiElement, keyDepth.getItemsNth(), "items");
    }

    public final boolean isResponses(final PsiElement psiElement) {
        return nthKeyEquals(psiElement, keyDepth.getResponsesNth(), "responses");
    }

    public final boolean isResponse(final PsiElement psiElement) {
        return nthKeyEquals(psiElement, keyDepth.getResponseNth(), "responses");
    }

    public final boolean isHeader(final PsiElement psiElement) {
        return nthKeyEquals(psiElement, keyDepth.getHeaderNth(), "headers");
    }

    public boolean isHeaders(final PsiElement psiElement) {
        return nthKeyEquals(psiElement, keyDepth.getHeadersNth(), "headers");
    }

    public final boolean isTag(final PsiElement psiElement) {
        return nthKeyEquals(psiElement, keyDepth.getTagsNth(), "tags");
    }

    public final boolean isSecurityDefinition(final PsiElement psiElement) {
        return nthKeyEquals(psiElement, keyDepth.getSecurityDefinitionsNth(), "securityDefinitions");
    }

    public final boolean isSchema(final PsiElement psiElement) {
        return nthKeyEquals(psiElement, keyDepth.getSchemaNth(), "schema");
    }

    public final boolean isXml(final PsiElement psiElement) {
        return nthKeyEquals(psiElement, keyDepth.getXmlNth(), "xml");
    }

    public final boolean isDefinitions(final PsiElement psiElement) {
        return nthKeyEquals(psiElement, keyDepth.getDefinitionsNth(), "definitions");
    }

    public final boolean isParameterDefinition(final PsiElement psiElement) {
        return nthKeyEquals(psiElement, keyDepth.getParameterDefinitionNth(), "parameters") && !elementIsInsideParametersArray(psiElement);
    }

    public final boolean isMimeValue(final PsiElement psiElement) {
        return (nthKeyEquals(psiElement, keyDepth.getMimeNth(), "consumes") || nthKeyEquals(psiElement, keyDepth.getMimeNth(), "produces")) &&
                elementIsInsideArray(psiElement);
    }

    public final boolean isSchemesValue(final PsiElement psiElement) {
        return nthKeyEquals(psiElement, keyDepth.getSchemesNth(), "schemes") && elementIsInsideArray(psiElement);
    }

    public boolean isDefinitionRefValue(final PsiElement psiElement) {
        return isRefValue(psiElement) && !elementIsInsideParametersArray(psiElement);
    }

    public boolean isParameterRefValue(final PsiElement psiElement) {
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

    private ElementPattern<Character> NOT_REF_VALUE_CHARACTER =
            CompletionData.NOT_JAVA_ID.andNot(character().oneOf('/', '#'));


    boolean isBooleanValue(final PsiElement psiElement) {
        return elementIsDirectValueOfKey(psiElement, "deprecated", "required", "allowEmptyValue",
                "exclusiveMaximum", "exclusiveMinimum", "uniqueItems", "readOnly", "attribute", "wrapped");
    }


    boolean isTypeValue(final PsiElement psiElement) {
        return elementIsDirectValueOfKey(psiElement, "type");
    }

    boolean isInValue(final PsiElement psiElement) {
        return elementIsDirectValueOfKey(psiElement, "in");
    }

    public abstract boolean isValue(final PsiElement psiElement);

    public abstract boolean isArrayStringElement(final PsiElement psiElement);
}
