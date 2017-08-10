package org.zalando.intellij.swagger.completion.field;

import com.intellij.codeInsight.completion.CompletionResultSet;
import com.intellij.codeInsight.completion.InsertHandler;
import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import org.zalando.intellij.swagger.completion.field.model.common.Field;
import org.zalando.intellij.swagger.completion.CompletionHelper;

public abstract class FieldCompletion {

    protected final CompletionHelper completionHelper;
    final CompletionResultSet completionResultSet;

    public FieldCompletion(final CompletionHelper completionHelper, final CompletionResultSet completionResultSet) {
        this.completionHelper = completionHelper;
        this.completionResultSet = completionResultSet;
    }

    public abstract void fill();

    public void addUnique(final Field field) {
        if (completionHelper.isUniqueKey(field.getName())) {
            completionResultSet.addElement(create(field, completionHelper.createInsertFieldHandler(field)));
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
