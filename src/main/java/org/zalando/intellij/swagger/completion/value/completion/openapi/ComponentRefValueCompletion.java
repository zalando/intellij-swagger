package org.zalando.intellij.swagger.completion.value.completion.openapi;

import com.intellij.codeInsight.completion.CompletionResultSet;
import com.intellij.psi.PsiFile;
import org.zalando.intellij.swagger.completion.CompletionHelper;
import org.zalando.intellij.swagger.completion.value.ValueCompletion;
import org.zalando.intellij.swagger.completion.value.model.common.StringValue;
import org.zalando.intellij.swagger.completion.value.model.common.Value;
import org.zalando.intellij.swagger.traversal.path.PathFinder;

import java.util.List;
import java.util.stream.Collectors;

class ComponentRefValueCompletion extends ValueCompletion {

    private final String refType;

    ComponentRefValueCompletion(final CompletionHelper completionHelper,
                                final CompletionResultSet completionResultSet,
                                final String refType) {
        super(completionHelper, completionResultSet);
        this.refType = refType;
    }

    @Override
    public void fill() {
        getSchemaKeys().forEach(this::addValue);
    }

    private List<Value> getSchemaKeys() {
        final PsiFile psiFile = completionHelper.getPsiFile();
        final String pathExpression = String.format("$.components.%s", refType);

        return new PathFinder().findChildrenByPathFrom(pathExpression, psiFile).stream()
                .map(e -> String.format("#/components/%s/%s", refType, e.getName()))
                .map(StringValue::new)
                .collect(Collectors.toList());
    }
}
