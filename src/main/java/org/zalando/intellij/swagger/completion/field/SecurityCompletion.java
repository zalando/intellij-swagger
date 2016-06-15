package org.zalando.intellij.swagger.completion.field;

import com.intellij.codeInsight.completion.CompletionResultSet;
import com.intellij.psi.PsiElement;
import org.zalando.intellij.swagger.completion.field.model.ArrayField;
import org.zalando.intellij.swagger.traversal.PositionResolver;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class SecurityCompletion extends FieldCompletion {

    protected SecurityCompletion(final PositionResolver positionResolver, final CompletionResultSet completionResultSet) {
        super(positionResolver, completionResultSet);
    }

    @Override
    public void fill() {
        getSecurityDefinitions().stream().forEach(field -> {
            final List<PsiElement> security = positionResolver.getChildrenOf("security");
            final List<String> existingNames = extractNames(security);
            if (!existingNames.contains(field.getName())) {
                addUnique(field);
            }
        });
    }

    private List<String> extractNames(final List<PsiElement> securityObjects) {
        return securityObjects.stream()
                .map(positionResolver::extractSecurityNameFromSecurityObject)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }

    private List<ArrayField> getSecurityDefinitions() {
        return positionResolver.getKeyNamesOf("securityDefinitions").stream()
                .map(ArrayField::new)
                .collect(Collectors.toList());
    }
}
