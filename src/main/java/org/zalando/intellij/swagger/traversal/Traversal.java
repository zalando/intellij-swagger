package org.zalando.intellij.swagger.traversal;

import com.intellij.codeInsight.completion.InsertHandler;
import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import org.zalando.intellij.swagger.completion.field.model.Field;
import org.zalando.intellij.swagger.completion.value.model.Value;
import org.zalando.intellij.swagger.traversal.keydepth.KeyDepth;

import java.util.List;
import java.util.Optional;

public abstract class Traversal {

    private final KeyDepth keyDepth;

    Traversal(final KeyDepth keyDepth) {
        this.keyDepth = keyDepth;
    }

    public abstract boolean isKey(final PsiElement psiElement);

    public abstract Optional<String> getKeyName(final PsiElement psiElement);

    abstract boolean elementIsInsideParametersArray(final PsiElement psiElement);

    abstract Optional<String> getNameOfNthKey(final PsiElement psiElement, int nthKey);

    public abstract boolean isRoot(final PsiElement psiElement);

    abstract boolean elementIsDirectValueOfKey(final PsiElement psiElement, final String... keyNames);

    abstract List<PsiElement> getChildrenOf(final String propertyName, final PsiFile psiFile);

    public abstract List<String> getKeyNamesOf(final String propertyName, final PsiFile containingFile);

    abstract boolean isUniqueKey(String keyName, final PsiElement psiElement);

    abstract boolean elementIsInsideArray(final PsiElement psiElement);

    abstract boolean isChildOfKeyWithName(final PsiElement psiElement, final String keyName);

    public abstract boolean isValue(final PsiElement psiElement);

    public abstract boolean isArrayStringElement(final PsiElement psiElement);

    abstract Optional<String> extractSecurityNameFromSecurityItem(final PsiElement psiElement);

    abstract InsertHandler<LookupElement> createInsertFieldHandler(Field field);

    abstract InsertHandler<LookupElement> createInsertValueHandler(final Value value);

    public final boolean isInfo(final PsiElement psiElement) {
        return nthKeyEquals(psiElement, keyDepth.getInfoNth(), "info");
    }

    public final boolean isContact(final PsiElement psiElement) {
        return nthKeyEquals(psiElement, keyDepth.getContactNth(), "contact");
    }

    public final boolean isLicense(final PsiElement psiElement) {
        return nthKeyEquals(psiElement, keyDepth.getLicenseNth(), "license");
    }

    public final boolean isPath(final PsiElement psiElement) {
        return nthKeyEquals(psiElement, keyDepth.getPathNth(), "paths");
    }

    public final boolean isOperation(final PsiElement psiElement) {
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
        return nthKeyEquals(psiElement, keyDepth.getResponsesNth(), "responses") &&
                isChildOfKeyWithName(psiElement, "paths");
    }

    public final boolean isResponse(final PsiElement psiElement) {
        return nthKeyEquals(psiElement, keyDepth.getResponseNth(), "responses") &&
                isChildOfKeyWithName(psiElement, "paths");
    }

    final boolean isResponseDefinition(final PsiElement psiElement) {
        return nthKeyEquals(psiElement, keyDepth.getResponseNth(), "responses") &&
                !isChildOfKeyWithName(psiElement, "paths");
    }

    public final boolean isHeader(final PsiElement psiElement) {
        return nthKeyEquals(psiElement, keyDepth.getHeaderNth(), "headers");
    }

    final boolean isHeaders(final PsiElement psiElement) {
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

    final boolean isDefinitions(final PsiElement psiElement) {
        return nthKeyEquals(psiElement, keyDepth.getDefinitionsNth(), "definitions");
    }

    final boolean isParameterDefinition(final PsiElement psiElement) {
        return nthKeyEquals(psiElement, keyDepth.getParameterDefinitionNth(), "parameters") &&
                !elementIsInsideParametersArray(psiElement);
    }

    final boolean isMimeValue(final PsiElement psiElement) {
        return (nthKeyEquals(psiElement, keyDepth.getMimeNth(), "consumes") ||
                nthKeyEquals(psiElement, keyDepth.getMimeNth(), "produces")) &&
                elementIsInsideArray(psiElement);
    }

    public final boolean isSchemesValue(final PsiElement psiElement) {
        return nthKeyEquals(psiElement, keyDepth.getSchemesNth(), "schemes") && elementIsInsideArray(psiElement);
    }

    public final boolean isDefinitionRefValue(final PsiElement psiElement) {
        return isRefValue(psiElement) &&
                !elementIsInsideParametersArray(psiElement) &&
                !isResponseRefValue(psiElement);
    }

    public final boolean isParameterRefValue(final PsiElement psiElement) {
        return isRefValue(psiElement) &&
                elementIsInsideParametersArray(psiElement);
    }

    public final boolean isResponseRefValue(final PsiElement psiElement) {
        return isRefValue(psiElement) &&
                nthKeyEquals(psiElement, 3, "responses") &&
                isChildOfKeyWithName(psiElement, "paths");
    }

    private boolean isRefValue(final PsiElement psiElement) {
        return nthKeyEquals(psiElement, 1, "$ref");
    }

    final boolean isSecurityKey(final PsiElement psiElement) {
        return nthKeyEquals(psiElement, keyDepth.getSecurityNth(), "security");
    }

    private boolean nthKeyEquals(final PsiElement psiElement, final int nth, final String targetName) {
        return getNameOfNthKey(psiElement, nth)
                .filter(name -> name.equals(targetName))
                .isPresent();
    }

    final boolean isBooleanValue(final PsiElement psiElement) {
        return elementIsDirectValueOfKey(psiElement, "deprecated", "required", "allowEmptyValue",
                "exclusiveMaximum", "exclusiveMinimum", "uniqueItems", "readOnly", "attribute", "wrapped");
    }


    final boolean isTypeValue(final PsiElement psiElement) {
        return elementIsDirectValueOfKey(psiElement, "type");
    }

    final boolean isInValue(final PsiElement psiElement) {
        return elementIsDirectValueOfKey(psiElement, "in");
    }

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

}
