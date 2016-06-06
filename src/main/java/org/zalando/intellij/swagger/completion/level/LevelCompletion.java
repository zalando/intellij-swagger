package org.zalando.intellij.swagger.completion.level;

import com.intellij.codeInsight.completion.CompletionResultSet;
import org.zalando.intellij.swagger.completion.level.field.Field;
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

    public abstract void fill();

    public void addUnique(final Field field,
                          final CompletionStyle completionStyle) {
        final String name = field.getName();

        if (positionResolver.isUniqueKey(name)) {
            completionResultSet.addElement(create(name, completionStyle, positionResolver.createInsertHandler(field)));
        }
    }
}
