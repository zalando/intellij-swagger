package org.zalando.intellij.swagger.completion.value;

import com.intellij.codeInsight.completion.CompletionResultSet;
import com.intellij.codeInsight.completion.InsertHandler;
import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import org.zalando.intellij.swagger.completion.value.model.Value;
import org.zalando.intellij.swagger.completion.CompletionHelper;

public abstract class ValueCompletion {

    final CompletionHelper completionHelper;
    private final CompletionResultSet completionResultSet;

    ValueCompletion(final CompletionHelper completionHelper, final CompletionResultSet completionResultSet) {
        this.completionHelper = completionHelper;
        this.completionResultSet = completionResultSet;
    }

    public abstract void fill();

    void addValue(final Value value) {
        if (completionHelper.isUniqueArrayStringValue(value.getValue())) {
            completionResultSet.addElement(create(value, completionHelper.createInsertValueHandler(value)));
        }
    }

    private LookupElementBuilder create(final Value value,
                                        final InsertHandler<LookupElement> insertHandler) {
        return LookupElementBuilder.create(value.getValue()).withInsertHandler(insertHandler);
    }
}
