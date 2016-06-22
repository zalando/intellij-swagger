package org.zalando.intellij.swagger.traversal;

import com.intellij.codeInsight.completion.InsertHandler;
import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.json.psi.JsonArray;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiNamedElement;
import org.jetbrains.yaml.psi.YAMLSequence;
import org.zalando.intellij.swagger.completion.field.model.Field;
import org.zalando.intellij.swagger.completion.value.model.Value;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public abstract class Traversal {

    public abstract boolean isKey(final PsiElement psiElement);

    public abstract Optional<String> getKeyNameIfKey(final PsiElement psiElement);

    public abstract Optional<String> getKeyNameOfObject(final PsiElement psiElement);

    public abstract Optional<String> getParentKeyName(final PsiElement psiElement);

    public abstract boolean childOfRoot(final PsiElement psiElement);

    abstract boolean elementIsDirectValueOfKey(final PsiElement psiElement, final String... keyNames);

    abstract List<PsiElement> getChildrenOf(final String propertyName, final PsiFile psiFile);

    public abstract List<String> getKeyNamesOf(final String propertyName, final PsiFile containingFile);

    abstract boolean isUniqueKey(String keyName, final PsiElement psiElement);

    abstract boolean isUniqueArrayStringValue(String value, final PsiElement psiElement);

    abstract boolean elementIsInsideArray(final PsiElement psiElement);

    abstract boolean childOfKeyWithName(final PsiElement psiElement, final String keyName);

    public abstract boolean isValue(final PsiElement psiElement);

    public abstract boolean isArrayStringElement(final PsiElement psiElement);

    abstract Optional<String> extractSecurityNameFromSecurityItem(final PsiElement psiElement);

    abstract InsertHandler<LookupElement> createInsertFieldHandler(Field field);

    abstract InsertHandler<LookupElement> createInsertValueHandler(final Value value);

    public final boolean childOfInfo(final PsiElement psiElement) {
        return hasPath(psiElement, "$.info");
    }

    public final boolean childOfContact(final PsiElement psiElement) {
        return hasPath(psiElement, "$.info.contact");
    }

    public final boolean childOfLicense(final PsiElement psiElement) {
        return hasPath(psiElement, "$.info.license");
    }

    public final boolean childOfPath(final PsiElement psiElement) {
        return hasPath(psiElement, "$.paths.*");
    }

    public final boolean childOfOperation(final PsiElement psiElement) {
        return hasPath(psiElement, "$.paths.*.*") && !childOfParameters(psiElement);
    }

    public final boolean childOfExternalDocs(final PsiElement psiElement) {
        return hasPath(psiElement, "$.externalDocs") ||
                hasPath(psiElement, "$.paths.*.*.externalDocs") ||
                hasPath(psiElement, "$.**.schema.externalDocs");
    }

    public final boolean childOfParameters(final PsiElement psiElement) {
        return hasPath(psiElement, "$.paths.*.*.parameters") ||
                hasPath(psiElement, "$.paths.*.parameters");
    }

    public final boolean childOfItems(final PsiElement psiElement) {
        return hasPath(psiElement, "$.paths.*.*.parameters.items");
    }

    public final boolean childOfResponses(final PsiElement psiElement) {
        return hasPath(psiElement, "$.paths.*.*.responses");
    }

    public final boolean childOfResponse(final PsiElement psiElement) {
        return hasPath(psiElement, "$.paths.*.*.responses.*");
    }

    final boolean childOfResponseDefinition(final PsiElement psiElement) {
        return hasPath(psiElement, "$.responses.*");
    }

    public final boolean childOfHeader(final PsiElement psiElement) {
        return hasPath(psiElement, "$.paths.*.*.responses.*.headers.*");
    }

    final boolean childOfHeaders(final PsiElement psiElement) {
        return hasPath(psiElement, "$.paths.*.*.responses.*.headers");
    }

    public final boolean childOfTag(final PsiElement psiElement) {
        return hasPath(psiElement, "$.tags");
    }

    public final boolean childOfSecurityDefinition(final PsiElement psiElement) {
        return hasPath(psiElement, "$.securityDefinitions.*");
    }

    public final boolean childOfSchema(final PsiElement psiElement) {
        return hasPath(psiElement, "$.**.schema");
    }

    public final boolean childOfXml(final PsiElement psiElement) {
        return hasPath(psiElement, "$.**.schema.xml");
    }

    final boolean childOfDefinitions(final PsiElement psiElement) {
        return hasPath(psiElement, "$.definitions.*");
    }

    final boolean childOfParameterDefinition(final PsiElement psiElement) {
        return hasPath(psiElement, "$.parameters.*");
    }

    final boolean isMimeValue(final PsiElement psiElement) {
        return hasPath(psiElement, "$.consumes") ||
                hasPath(psiElement, "$.produces") ||
                hasPath(psiElement, "$.paths.*.*.consumes") ||
                hasPath(psiElement, "$.paths.*.*.produces");

    }

    public final boolean isSchemesValue(final PsiElement psiElement) {
        return hasPath(psiElement, "$.schemes");
    }

    public final boolean isDefinitionRefValue(final PsiElement psiElement) {
        return hasPath(psiElement, "$.paths.*.*.responses.*.schema.$ref");
    }

    public final boolean isParameterRefValue(final PsiElement psiElement) {
        return hasPath(psiElement, "$.paths.*.*.parameters.$ref");
    }

    public final boolean isResponseRefValue(final PsiElement psiElement) {
        return hasPath(psiElement, "$.paths.*.*.responses.*.$ref");
    }

    final boolean childOfRootSecurityKey(final PsiElement psiElement) {
        return hasPath(psiElement, "$.security");
    }

    final boolean childOfOperationSecurityKey(final PsiElement psiElement) {
        return hasPath(psiElement, "$.paths.*.*.security");
    }

    final boolean isBooleanValue(final PsiElement psiElement) {
        return elementIsDirectValueOfKey(psiElement, "deprecated", "required", "allowEmptyValue",
                "exclusiveMaximum", "exclusiveMinimum", "uniqueItems", "readOnly", "attribute", "wrapped");
    }

    boolean isSecurityScopeNameValue(final PsiElement psiElement) {
        return hasPath(psiElement, "$.security.*") ||
                hasPath(psiElement, "$.paths.*.*.security.*");
    }

    final boolean isTypeValue(final PsiElement psiElement) {
        return elementIsDirectValueOfKey(psiElement, "type");
    }

    final boolean isFormatValue(final PsiElement psiElement) {
        return elementIsDirectValueOfKey(psiElement, "format");
    }

    final boolean isInValue(final PsiElement psiElement) {
        return elementIsDirectValueOfKey(psiElement, "in");
    }

    boolean childOfItemsCollectionFormat(final PsiElement psiElement) {
        return hasPath(psiElement, "$.paths.*.*.parameters.items.collectionFormat");
    }

    boolean childOfParametersCollectionFormat(final PsiElement psiElement) {
        return hasPath(psiElement, "$.paths.*.*.parameters.collectionFormat");
    }

    boolean childOfHeadersCollectionFormat(final PsiElement psiElement) {
        return hasPath(psiElement, "$.paths.*.*.responses.*.headers.*.collectionFormat");
    }

    private boolean hasPath(final PsiElement psiElement, final String pathExpression) {
        return new Path(psiElement, this, pathExpression).exists();
    }

    abstract List<String> getSecurityScopesIfOAuth2(final PsiElement securityDefinitionItem);

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

    Optional<PsiElement> getParentByName(final PsiElement psiElement, final String parentName) {
        if (psiElement == null) {
            return Optional.empty();
        }

        if (psiElement instanceof PsiNamedElement) {
            final PsiNamedElement psiNamedElement = (PsiNamedElement) psiElement;

            if (parentName.equals(psiNamedElement.getName())) {
                return Optional.of(psiElement);
            }
        }

        return getParentByName(psiElement.getParent(), parentName);
    }

    List<PsiElement> getChildrenOfArrayObject(final PsiElement psiElement) {
        return Arrays.asList(psiElement.getChildren()).stream()
                .filter(child -> child instanceof JsonArray || child instanceof YAMLSequence)
                .map(el -> Arrays.asList(el.getChildren()))
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    public abstract PsiElement extractObjectForValidation(final PsiElement psiElement);
}
