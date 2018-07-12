package org.zalando.intellij.swagger.examples.extensions.zalando.value.completion.swagger;

import com.intellij.codeInsight.completion.CompletionResultSet;
import org.zalando.intellij.swagger.completion.SwaggerCompletionHelper;
import org.zalando.intellij.swagger.completion.field.FieldCompletion;
import org.zalando.intellij.swagger.completion.value.ValueCompletion;
import org.zalando.intellij.swagger.examples.extensions.zalando.field.completion.swagger.InfoCompletion;
import org.zalando.intellij.swagger.extensions.completion.swagger.SwaggerCustomFieldCompletionFactory;
import org.zalando.intellij.swagger.extensions.completion.swagger.SwaggerCustomValueCompletionFactory;

import java.util.Optional;

public class SwaggerValueFactory implements SwaggerCustomValueCompletionFactory {

    @Override
    public Optional<ValueCompletion> from(SwaggerCompletionHelper swaggerCompletionHelper, CompletionResultSet completionResultSet) {
        if(swaggerCompletionHelper.hasPath("$.info.x-audience")) {
            return Optional.of(new AudienceValueCompletion(swaggerCompletionHelper, completionResultSet));
        } else {
            return Optional.empty();
        }
    }
}
