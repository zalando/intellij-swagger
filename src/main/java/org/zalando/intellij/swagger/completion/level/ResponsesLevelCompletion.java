package org.zalando.intellij.swagger.completion.level;

import static org.zalando.intellij.swagger.completion.style.CompletionStyleFactory.optional;
import com.intellij.codeInsight.completion.CompletionResultSet;
import org.zalando.intellij.swagger.completion.level.field.ObjectField;
import org.zalando.intellij.swagger.completion.level.field.ResponseField;
import org.zalando.intellij.swagger.completion.traversal.PositionResolver;

class ResponsesLevelCompletion extends LevelCompletion {

    ResponsesLevelCompletion(final PositionResolver positionResolver, final CompletionResultSet completionResultSet) {
        super(positionResolver, completionResultSet);
    }

    public void fill() {
        addUnique(new ObjectField("default"), optional(positionResolver));
        addUnique(new ResponseField("200"), optional(positionResolver));
        addUnique(new ResponseField("201"), optional(positionResolver));
        addUnique(new ResponseField("202"), optional(positionResolver));
        addUnique(new ResponseField("203"), optional(positionResolver));
        addUnique(new ResponseField("204"), optional(positionResolver));
        addUnique(new ResponseField("205"), optional(positionResolver));
        addUnique(new ResponseField("206"), optional(positionResolver));
        addUnique(new ResponseField("207"), optional(positionResolver));
        addUnique(new ResponseField("208"), optional(positionResolver));
        addUnique(new ResponseField("226"), optional(positionResolver));

        addUnique(new ResponseField("300"), optional(positionResolver));
        addUnique(new ResponseField("301"), optional(positionResolver));
        addUnique(new ResponseField("302"), optional(positionResolver));
        addUnique(new ResponseField("303"), optional(positionResolver));
        addUnique(new ResponseField("304"), optional(positionResolver));
        addUnique(new ResponseField("305"), optional(positionResolver));
        addUnique(new ResponseField("306"), optional(positionResolver));
        addUnique(new ResponseField("307"), optional(positionResolver));
        addUnique(new ResponseField("308"), optional(positionResolver));

        addUnique(new ResponseField("400"), optional(positionResolver));
        addUnique(new ResponseField("401"), optional(positionResolver));
        addUnique(new ResponseField("402"), optional(positionResolver));
        addUnique(new ResponseField("403"), optional(positionResolver));
        addUnique(new ResponseField("404"), optional(positionResolver));
        addUnique(new ResponseField("405"), optional(positionResolver));
        addUnique(new ResponseField("406"), optional(positionResolver));
        addUnique(new ResponseField("407"), optional(positionResolver));
        addUnique(new ResponseField("408"), optional(positionResolver));
        addUnique(new ResponseField("409"), optional(positionResolver));
        addUnique(new ResponseField("410"), optional(positionResolver));
        addUnique(new ResponseField("411"), optional(positionResolver));
        addUnique(new ResponseField("412"), optional(positionResolver));
        addUnique(new ResponseField("413"), optional(positionResolver));
        addUnique(new ResponseField("414"), optional(positionResolver));
        addUnique(new ResponseField("415"), optional(positionResolver));
        addUnique(new ResponseField("416"), optional(positionResolver));
        addUnique(new ResponseField("417"), optional(positionResolver));
        addUnique(new ResponseField("418"), optional(positionResolver));
        addUnique(new ResponseField("421"), optional(positionResolver));
        addUnique(new ResponseField("422"), optional(positionResolver));
        addUnique(new ResponseField("423"), optional(positionResolver));
        addUnique(new ResponseField("424"), optional(positionResolver));
        addUnique(new ResponseField("426"), optional(positionResolver));
        addUnique(new ResponseField("428"), optional(positionResolver));
        addUnique(new ResponseField("429"), optional(positionResolver));
        addUnique(new ResponseField("431"), optional(positionResolver));
        addUnique(new ResponseField("451"), optional(positionResolver));

        addUnique(new ResponseField("500"), optional(positionResolver));
        addUnique(new ResponseField("501"), optional(positionResolver));
        addUnique(new ResponseField("502"), optional(positionResolver));
        addUnique(new ResponseField("503"), optional(positionResolver));
        addUnique(new ResponseField("504"), optional(positionResolver));
        addUnique(new ResponseField("505"), optional(positionResolver));
        addUnique(new ResponseField("506"), optional(positionResolver));
        addUnique(new ResponseField("507"), optional(positionResolver));
        addUnique(new ResponseField("508"), optional(positionResolver));
        addUnique(new ResponseField("510"), optional(positionResolver));
        addUnique(new ResponseField("511"), optional(positionResolver));
    }

}
