package org.zalando.intellij.swagger.completion.level;

import static org.zalando.intellij.swagger.completion.style.CompletionStyleFactory.optional;
import static org.zalando.intellij.swagger.completion.style.CompletionStyleFactory.required;
import com.intellij.codeInsight.completion.CompletionResultSet;
import org.zalando.intellij.swagger.completion.level.field.ContactField;
import org.zalando.intellij.swagger.completion.level.field.LicenseField;
import org.zalando.intellij.swagger.completion.level.field.StringField;
import org.zalando.intellij.swagger.completion.traversal.PositionResolver;

class InfoLevelCompletion extends LevelCompletion {

    InfoLevelCompletion(final PositionResolver positionResolver, final CompletionResultSet completionResultSet) {
        super(positionResolver, completionResultSet);
    }

    public void fill() {
        addUnique(new StringField("title"), required(positionResolver));
        addUnique(new StringField("description"), optional(positionResolver));
        addUnique(new StringField("termsOfService"), optional(positionResolver));
        addUnique(new ContactField(), optional(positionResolver));
        addUnique(new LicenseField(), optional(positionResolver));
        addUnique(new StringField("version"), required(positionResolver));
    }

}
