package org.zalando.intellij.swagger.completion.value.completion.swagger;

import com.intellij.codeInsight.completion.CompletionResultSet;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiNamedElement;
import org.zalando.intellij.swagger.completion.CompletionHelper;
import org.zalando.intellij.swagger.completion.value.ValueCompletion;
import org.zalando.intellij.swagger.completion.value.model.common.StringValue;
import org.zalando.intellij.swagger.completion.value.model.common.Value;
import org.zalando.intellij.swagger.traversal.path.PathFinder;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

class SecurityScopeNameValueCompletion extends ValueCompletion {

    SecurityScopeNameValueCompletion(final CompletionHelper completionHelper,
                                     final CompletionResultSet completionResultSet) {
        super(completionHelper, completionResultSet);
    }

    @Override
    public void fill() {
        completionHelper.getParentKeyName().ifPresent(parentKeyName ->
                getSecurityDefinitionByName(parentKeyName).forEach(this::addValue)
        );
    }

    private List<Value> getSecurityDefinitionByName(final String securityDefinitionName) {
        final PsiFile containingFile = completionHelper.getPsiFile().getContainingFile();
        final List<? extends PsiNamedElement> securityDefinitions =
                new PathFinder().findChildrenByPathFrom("$.securityDefinitions", containingFile);

        return securityDefinitions.stream()
                .filter(e -> securityDefinitionName.equals(completionHelper.getKeyNameOfObject(e).orElse(null)))
                .map(completionHelper::getSecurityScopesIfOAuth2)
                .flatMap(Collection::stream)
                .map(StringValue::new)
                .collect(Collectors.toList());
    }

}
