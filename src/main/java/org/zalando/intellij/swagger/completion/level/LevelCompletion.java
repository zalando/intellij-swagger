package org.zalando.intellij.swagger.completion.level;

import com.intellij.codeInsight.completion.CompletionResultSet;
import com.intellij.codeInsight.completion.InsertHandler;
import com.intellij.codeInsight.lookup.LookupElement;
import org.jetbrains.annotations.NotNull;
import org.zalando.intellij.swagger.completion.style.CompletionStyle;
import org.zalando.intellij.swagger.completion.traversal.PositionResolver;

import static org.zalando.intellij.swagger.completion.level.LookupElementBuilderFactory.create;

public abstract class LevelCompletion {

    final PositionResolver positionResolver;
    final CompletionResultSet completionResultSet;

    protected LevelCompletion(final PositionResolver positionResolver, final CompletionResultSet completionResultSet) {
        this.positionResolver = positionResolver;
        this.completionResultSet = completionResultSet;
    }

    public abstract void fill(@NotNull final InsertHandler<LookupElement> insertHandler);

    public void addUnique(final String lookup,
                          final CompletionStyle completionStyle,
                          final InsertHandler<LookupElement> insertHandler) {
        if (positionResolver.isUniqueKey(lookup)) {
            completionResultSet.addElement(create(lookup, completionStyle, insertHandler));
        }
    }
}
