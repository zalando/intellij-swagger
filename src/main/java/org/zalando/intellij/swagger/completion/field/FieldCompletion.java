package org.zalando.intellij.swagger.completion.field;

import com.intellij.codeInsight.completion.CompletionResultSet;
import com.intellij.codeInsight.completion.InsertHandler;
import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import org.zalando.intellij.swagger.completion.field.model.Field;
import org.zalando.intellij.swagger.traversal.PositionResolver;

public abstract class FieldCompletion {

    final PositionResolver positionResolver;
    final CompletionResultSet completionResultSet;

    protected FieldCompletion(final PositionResolver positionResolver, final CompletionResultSet completionResultSet) {
        this.positionResolver = positionResolver;
        this.completionResultSet = completionResultSet;
    }

    public abstract void fill();

    public void addUnique(final Field field) {
        if (positionResolver.isUniqueKey(field.getName())) {
            completionResultSet.addElement(create(field, positionResolver.createInsertFieldHandler(field)));
        }
    }

    private LookupElementBuilder create(final Field field) {
        LookupElementBuilder lookupElementBuilder = LookupElementBuilder.create(field, field.getName());

        if (field.isRequired()) {
            lookupElementBuilder = lookupElementBuilder.bold();
        }

        return lookupElementBuilder;
    }

    private LookupElementBuilder create(final Field field,
                                        final InsertHandler<LookupElement> insertHandler) {
        return create(field).withInsertHandler(insertHandler);
    }

}
