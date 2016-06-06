package org.zalando.intellij.swagger.completion.level;

import com.intellij.codeInsight.completion.CompletionResultSet;
import org.zalando.intellij.swagger.completion.level.field.ArrayField;
import org.zalando.intellij.swagger.completion.level.field.ObjectField;
import org.zalando.intellij.swagger.completion.level.field.RefField;
import org.zalando.intellij.swagger.completion.traversal.PositionResolver;

import static org.zalando.intellij.swagger.completion.style.CompletionStyleFactory.optional;

class PathLevelCompletion extends LevelCompletion {

    PathLevelCompletion(final PositionResolver positionResolver, final CompletionResultSet completionResultSet) {
        super(positionResolver, completionResultSet);
    }

    public void fill() {
        addUnique(new RefField(), optional(positionResolver));
        addUnique(new ObjectField("get"), optional(positionResolver));
        addUnique(new ObjectField("put"), optional(positionResolver));
        addUnique(new ObjectField("post"), optional(positionResolver));
        addUnique(new ObjectField("delete"), optional(positionResolver));
        addUnique(new ObjectField("options"), optional(positionResolver));
        addUnique(new ObjectField("head"), optional(positionResolver));
        addUnique(new ObjectField("patch"), optional(positionResolver));
        addUnique(new ArrayField("parameters"), optional(positionResolver));
    }

}
