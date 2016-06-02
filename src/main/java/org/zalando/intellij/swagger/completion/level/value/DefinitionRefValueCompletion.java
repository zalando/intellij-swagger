package org.zalando.intellij.swagger.completion.level.value;

import com.intellij.codeInsight.completion.CompletionResultSet;
import com.intellij.codeInsight.completion.InsertHandler;
import com.intellij.codeInsight.lookup.LookupElement;
import org.jetbrains.annotations.NotNull;
import org.zalando.intellij.swagger.completion.traversal.PositionResolver;

import java.util.List;
import java.util.stream.Collectors;

import static org.zalando.intellij.swagger.completion.level.LookupElementBuilderFactory.create;
import static org.zalando.intellij.swagger.completion.style.CompletionStyleFactory.optional;

class DefinitionRefValueCompletion extends ValueCompletion {

    DefinitionRefValueCompletion(final PositionResolver positionResolver) {
        super(positionResolver);
    }

    @Override
    public void fill(@NotNull final CompletionResultSet result,
                     @NotNull final InsertHandler<LookupElement> insertHandler) {
        getDefinitionKeys().stream()
                .forEach(refValue -> result.addElement(create(refValue, optional(positionResolver), insertHandler)));
    }

    private List<String> getDefinitionKeys() {
        return positionResolver.getKeyNamesOf("definitions").stream()
                .map(keyName -> "#/definitions/" + keyName)
                .collect(Collectors.toList());
    }
}
