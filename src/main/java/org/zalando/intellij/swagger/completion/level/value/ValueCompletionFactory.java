package org.zalando.intellij.swagger.completion.level.value;

import com.intellij.codeInsight.completion.CompletionParameters;
import com.intellij.openapi.editor.Editor;
import com.intellij.psi.PsiElement;
import org.zalando.intellij.swagger.completion.traversal.PositionResolver;

import java.util.Optional;

public class ValueCompletionFactory {

    public static Optional<ValueCompletion> from(final PositionResolver positionResolver,
                                                 final Editor editor) {

        if (positionResolver.completeMimeValue()) {
            return Optional.of(new MimeValueCompletion(positionResolver));
        } else if (positionResolver.completeSchemesValue()) {
            return Optional.of(new SchemesValueCompletion(positionResolver));
        } else if (positionResolver.completeRefValue(editor)) {
            return Optional.of(new RefValueCompletion(positionResolver));
        } else {
            return Optional.empty();
        }
    }
}