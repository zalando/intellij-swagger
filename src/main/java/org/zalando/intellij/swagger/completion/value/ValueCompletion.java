package org.zalando.intellij.swagger.completion.value;

import com.intellij.codeInsight.completion.CompletionResultSet;
import com.intellij.codeInsight.completion.InsertHandler;
import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import org.zalando.intellij.swagger.completion.value.model.Value;
import org.zalando.intellij.swagger.traversal.CompletionHelper;

public abstract class ValueCompletion {

    final CompletionHelper completionHelper;
    final CompletionResultSet completionResultSet;

    protected ValueCompletion(final CompletionHelper completionHelper, final CompletionResultSet completionResultSet) {
        this.completionHelper = completionHelper;
        this.completionResultSet = completionResultSet;
    }

    public abstract void fill();

    public void addValue(final Value value) {
        completionResultSet.addElement(create(value, completionHelper.createInsertValueHandler(value)));
    }

    private LookupElementBuilder create(final Value value,
                                        final InsertHandler<LookupElement> insertHandler) {
        return LookupElementBuilder.create(value.getValue()).withInsertHandler(insertHandler);
    }
}
