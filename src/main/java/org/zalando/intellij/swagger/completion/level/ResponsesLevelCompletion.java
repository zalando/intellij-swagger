package org.zalando.intellij.swagger.completion.level;

import com.intellij.codeInsight.completion.CompletionResultSet;
import org.zalando.intellij.swagger.completion.level.field.ObjectField;
import org.zalando.intellij.swagger.completion.traversal.PositionResolver;

import static org.zalando.intellij.swagger.completion.style.CompletionStyleFactory.optional;

class ResponsesLevelCompletion extends LevelCompletion {

    ResponsesLevelCompletion(final PositionResolver positionResolver, final CompletionResultSet completionResultSet) {
        super(positionResolver, completionResultSet);
    }

    public void fill() {
        addUnique(new ObjectField("default"), optional(positionResolver));
        addUnique(new ObjectField("200"), optional(positionResolver));
        addUnique(new ObjectField("201"), optional(positionResolver));
        addUnique(new ObjectField("202"), optional(positionResolver));
        addUnique(new ObjectField("203"), optional(positionResolver));
        addUnique(new ObjectField("204"), optional(positionResolver));
        addUnique(new ObjectField("205"), optional(positionResolver));
        addUnique(new ObjectField("206"), optional(positionResolver));
        addUnique(new ObjectField("207"), optional(positionResolver));
        addUnique(new ObjectField("208"), optional(positionResolver));
        addUnique(new ObjectField("226"), optional(positionResolver));

        addUnique(new ObjectField("300"), optional(positionResolver));
        addUnique(new ObjectField("301"), optional(positionResolver));
        addUnique(new ObjectField("302"), optional(positionResolver));
        addUnique(new ObjectField("303"), optional(positionResolver));
        addUnique(new ObjectField("304"), optional(positionResolver));
        addUnique(new ObjectField("305"), optional(positionResolver));
        addUnique(new ObjectField("306"), optional(positionResolver));
        addUnique(new ObjectField("307"), optional(positionResolver));
        addUnique(new ObjectField("308"), optional(positionResolver));

        addUnique(new ObjectField("400"), optional(positionResolver));
        addUnique(new ObjectField("401"), optional(positionResolver));
        addUnique(new ObjectField("402"), optional(positionResolver));
        addUnique(new ObjectField("403"), optional(positionResolver));
        addUnique(new ObjectField("404"), optional(positionResolver));
        addUnique(new ObjectField("405"), optional(positionResolver));
        addUnique(new ObjectField("406"), optional(positionResolver));
        addUnique(new ObjectField("407"), optional(positionResolver));
        addUnique(new ObjectField("408"), optional(positionResolver));
        addUnique(new ObjectField("409"), optional(positionResolver));
        addUnique(new ObjectField("410"), optional(positionResolver));
        addUnique(new ObjectField("411"), optional(positionResolver));
        addUnique(new ObjectField("412"), optional(positionResolver));
        addUnique(new ObjectField("413"), optional(positionResolver));
        addUnique(new ObjectField("414"), optional(positionResolver));
        addUnique(new ObjectField("415"), optional(positionResolver));
        addUnique(new ObjectField("416"), optional(positionResolver));
        addUnique(new ObjectField("417"), optional(positionResolver));
        addUnique(new ObjectField("418"), optional(positionResolver));
        addUnique(new ObjectField("421"), optional(positionResolver));
        addUnique(new ObjectField("422"), optional(positionResolver));
        addUnique(new ObjectField("423"), optional(positionResolver));
        addUnique(new ObjectField("424"), optional(positionResolver));
        addUnique(new ObjectField("426"), optional(positionResolver));
        addUnique(new ObjectField("428"), optional(positionResolver));
        addUnique(new ObjectField("429"), optional(positionResolver));
        addUnique(new ObjectField("431"), optional(positionResolver));
        addUnique(new ObjectField("451"), optional(positionResolver));

        addUnique(new ObjectField("500"), optional(positionResolver));
        addUnique(new ObjectField("501"), optional(positionResolver));
        addUnique(new ObjectField("502"), optional(positionResolver));
        addUnique(new ObjectField("503"), optional(positionResolver));
        addUnique(new ObjectField("504"), optional(positionResolver));
        addUnique(new ObjectField("505"), optional(positionResolver));
        addUnique(new ObjectField("506"), optional(positionResolver));
        addUnique(new ObjectField("507"), optional(positionResolver));
        addUnique(new ObjectField("508"), optional(positionResolver));
        addUnique(new ObjectField("510"), optional(positionResolver));
        addUnique(new ObjectField("511"), optional(positionResolver));
    }

}
