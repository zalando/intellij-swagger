package org.zalando.intellij.swagger.completion;

import com.intellij.codeInsight.completion.InsertHandler;
import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import org.zalando.intellij.swagger.completion.field.model.Field;
import org.zalando.intellij.swagger.completion.value.model.Value;

import java.util.List;
import java.util.Optional;

public interface CompletionHelper {

    boolean isUniqueKey(final String keyName);

    InsertHandler<LookupElement> createInsertFieldHandler(final Field field);

    Optional<String> extractSecurityNameFromSecurityObject(final PsiElement psiElement);

    List<String> getSecurityScopesIfOAuth2(final PsiElement securityDefinitionItem);

    Optional<PsiElement> getParentByName(final String parentName);

    boolean isUniqueArrayStringValue(final String keyName);

    InsertHandler<LookupElement> createInsertValueHandler(final Value value);

    List<String> getKeyNamesOfDefinition(final String propertyName);

    List<PsiElement> getChildrenOfRoot(final String propertyName);

    List<PsiElement> getChildrenOfArrayObject(final PsiElement psiElement);

    List<String> getTagNames();

    Optional<String> getParentKeyName();

    Optional<String> getKeyNameOfObject(final PsiElement psiElement);
}
