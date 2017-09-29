package org.zalando.intellij.swagger.completion;

import com.intellij.codeInsight.completion.InsertHandler;
import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiNamedElement;
import org.zalando.intellij.swagger.completion.field.model.common.Field;
import org.zalando.intellij.swagger.completion.value.model.common.Value;
import org.zalando.intellij.swagger.traversal.path.PathFinder;

import java.util.List;
import java.util.Optional;

public abstract class CompletionHelper {

    protected final PsiElement psiElement;

    protected CompletionHelper(final PsiElement psiElement) {
        this.psiElement = psiElement;
    }

    public boolean isUniqueKey(final String keyName) {
        List<? extends PsiNamedElement> children = new PathFinder().findChildrenByPathFrom("parent", psiElement);

        return children.stream()
                .noneMatch((c) -> keyName.equals(c.getName()));
    }

    public abstract InsertHandler<LookupElement> createInsertFieldHandler(final Field field);

    public abstract Optional<String> extractSecurityNameFromSecurityObject(final PsiElement psiElement);

    public abstract List<String> getSecurityScopesIfOAuth2(final PsiElement securityDefinitionItem);

    public abstract Optional<PsiElement> getParentByName(final String parentName);

    public abstract boolean isUniqueArrayStringValue(final String keyName);

    public abstract InsertHandler<LookupElement> createInsertValueHandler(final Value value);

    public abstract List<PsiElement> getChildrenOfArrayObject(final PsiElement psiElement);

    public abstract List<String> getTagNames();

    public abstract Optional<String> getParentKeyName();

    public abstract Optional<String> getKeyNameOfObject(final PsiElement psiElement);

    public abstract PsiFile getPsiFile();
}
