package org.zalando.intellij.swagger.completion.level;

import static org.zalando.intellij.swagger.completion.style.CompletionStyleFactory.optional;
import static org.zalando.intellij.swagger.completion.style.CompletionStyleFactory.required;
import com.intellij.codeInsight.completion.CompletionResultSet;
import org.zalando.intellij.swagger.completion.level.field.ObjectField;
import org.zalando.intellij.swagger.completion.level.field.StringField;
import org.zalando.intellij.swagger.completion.traversal.PositionResolver;

class ResponseLevelCompletion extends LevelCompletion {

    ResponseLevelCompletion(final PositionResolver positionResolver, final CompletionResultSet completionResultSet) {
        super(positionResolver, completionResultSet);
    }

    public void fill() {
        addUnique(new StringField("description"), required(positionResolver));
        addUnique(new ObjectField("schema"), optional(positionResolver));
        addUnique(new ObjectField("headers"), optional(positionResolver));
        addUnique(new ObjectField("examples"), optional(positionResolver));
    }

}
