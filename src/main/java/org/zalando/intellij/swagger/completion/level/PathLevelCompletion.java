package org.zalando.intellij.swagger.completion.level;

import static org.zalando.intellij.swagger.completion.style.CompletionStyleFactory.optional;
import com.intellij.codeInsight.completion.CompletionResultSet;
import org.zalando.intellij.swagger.completion.level.field.ArrayField;
import org.zalando.intellij.swagger.completion.level.field.OperationField;
import org.zalando.intellij.swagger.completion.level.field.RefField;
import org.zalando.intellij.swagger.completion.traversal.PositionResolver;

class PathLevelCompletion extends LevelCompletion {

    PathLevelCompletion(final PositionResolver positionResolver, final CompletionResultSet completionResultSet) {
        super(positionResolver, completionResultSet);
    }

    public void fill() {
        addUnique(new RefField(), optional(positionResolver));
        addUnique(new OperationField("get"), optional(positionResolver));
        addUnique(new OperationField("put"), optional(positionResolver));
        addUnique(new OperationField("post"), optional(positionResolver));
        addUnique(new OperationField("delete"), optional(positionResolver));
        addUnique(new OperationField("options"), optional(positionResolver));
        addUnique(new OperationField("head"), optional(positionResolver));
        addUnique(new OperationField("patch"), optional(positionResolver));
        addUnique(new ArrayField("parameters"), optional(positionResolver));
    }

}
