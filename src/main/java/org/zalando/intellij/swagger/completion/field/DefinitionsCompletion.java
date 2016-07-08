package org.zalando.intellij.swagger.completion.field;

import com.intellij.codeInsight.completion.CompletionResultSet;
import org.zalando.intellij.swagger.completion.CompletionHelper;

class DefinitionsCompletion extends FieldCompletion {

    DefinitionsCompletion(final CompletionHelper completionHelper, final CompletionResultSet completionResultSet) {
        super(completionHelper, completionResultSet);
    }

    @Override
    public void fill() {
        new SchemaCompletion(completionHelper, completionResultSet).fill();
    }

}
