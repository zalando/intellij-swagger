package org.zalando.intellij.swagger.completion.level.value;

import static org.zalando.intellij.swagger.completion.level.LookupElementBuilderFactory.create;
import static org.zalando.intellij.swagger.completion.style.CompletionStyleFactory.optional;
import com.intellij.codeInsight.completion.CompletionResultSet;
import com.intellij.codeInsight.completion.InsertHandler;
import com.intellij.codeInsight.lookup.LookupElement;
import org.jetbrains.annotations.NotNull;
import org.zalando.intellij.swagger.completion.traversal.PositionResolver;

import java.util.List;
import java.util.stream.Collectors;

class ParameterRefValueCompletion extends ValueCompletion {

    ParameterRefValueCompletion(final PositionResolver positionResolver) {
        super(positionResolver);
    }

    @Override
    public void fill(@NotNull final CompletionResultSet result,
                     @NotNull final InsertHandler<LookupElement> insertHandler) {
        getParameterKeys().stream()
                .forEach(refValue -> result.addElement(create(refValue, optional(positionResolver), insertHandler)));
    }

    private List<String> getParameterKeys() {
        return positionResolver.getKeyNamesOf("parameters").stream()
                .map(keyName -> "#/parameters/" + keyName)
                .collect(Collectors.toList());
    }

}
