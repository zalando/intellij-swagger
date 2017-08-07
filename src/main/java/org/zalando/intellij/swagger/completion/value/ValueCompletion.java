package org.zalando.intellij.swagger.completion.value;

import com.intellij.codeInsight.completion.CompletionResultSet;
import com.intellij.codeInsight.completion.InsertHandler;
import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import org.zalando.intellij.swagger.completion.CompletionHelper;
import org.zalando.intellij.swagger.completion.value.model.Value;
import org.zalando.intellij.swagger.completion.SwaggerCompletionHelper;

public abstract class ValueCompletion {

    protected final CompletionHelper completionHelper;
    private final CompletionResultSet completionResultSet;

    public ValueCompletion(final CompletionHelper completionHelper, final CompletionResultSet completionResultSet) {
        this.completionHelper = completionHelper;
        this.completionResultSet = completionResultSet;
    }

    public abstract void fill();

    public void addValue(final Value value) {
        if (completionHelper.isUniqueArrayStringValue(value.getValue())) {
            completionResultSet.addElement(create(value, completionHelper.createInsertValueHandler(value)));
        }
    }

    private LookupElementBuilder create(final Value value,
                                        final InsertHandler<LookupElement> insertHandler) {
        return LookupElementBuilder.create(value.getValue()).withInsertHandler(insertHandler);
    }
}
